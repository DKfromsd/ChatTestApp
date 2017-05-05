package com.okldk.familychat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Hashtable;
public class MainActivity extends AppCompatActivity {
    String TAG ="MainActivity";
    EditText etEmail;
    EditText etPassword;
    String stEmail;
    String stPassword;
    ProgressBar pbLogin;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        pbLogin=(ProgressBar)findViewById(R.id.pbLogin);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    SharedPreferences sharedPreferences = getSharedPreferences("email", MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("uid",user.getUid());
                    editor.putString("email",user.getEmail());

                    editor.apply();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        Button btnRegister = (Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               stEmail = etEmail.getText().toString();
               stPassword = etPassword.getText().toString();

               if(stEmail.isEmpty()||stEmail.equals("")||stPassword.isEmpty()||stPassword.equals("")){
                   Toast.makeText(MainActivity.this, "Please input your information", Toast.LENGTH_SHORT).show();
               }else {
                   // Toast.makeText(MainActivity.this, stEmail+","+stPassword, Toast.LENGTH_SHORT).show();
                   registerUser(stEmail, stPassword);
               }
           }
        });

        Button btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                stEmail = etEmail.getText().toString();
                stPassword = etPassword.getText().toString();
                if (stEmail.isEmpty() || stEmail.equals("") || stPassword.isEmpty() || stPassword.equals("")) {
                    Toast.makeText(MainActivity.this, "Please input your information", Toast.LENGTH_SHORT).show();
                } else {

                userLogin(stEmail, stPassword);
                Toast.makeText(MainActivity.this, "LOGIN", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.



                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            
                            if (user != null) {
                                Hashtable<String, String> profile = new Hashtable<String, String>();
                                profile.put("email", user.getEmail());
                                profile.put("photo", "");
                                profile.put("key", user.getUid());
                                myRef.child(user.getUid()).setValue(profile);
                            }
                        }

                        // ...
                    }
                });

    }
    private void userLogin(String email, String password){
        pbLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        pbLogin.setVisibility(View.GONE);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Auth Failed",
                                    Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(MainActivity.this, "Auth Success",
                                    Toast.LENGTH_SHORT).show();

                            Intent in = new Intent(MainActivity.this, TabActivity.class);
                            startActivity(in);

                        }

                        // ...
                    }
                });

    }



}
