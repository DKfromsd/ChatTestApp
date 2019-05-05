package com.okldk.familychat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;


public class ChatActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
// test    String[] myDataset={"Hello","Hi","hola","amigo","hahaha","메롱"};

    String email;
    private List<Chat> mChat;

    EditText etText;
    Button btnSend;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
           // String name = user.getDisplayName();
            email = user.getEmail();
           // Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
           // String uid = user.getUid();
        }
        Intent in = getIntent();
        final String stChatId = in.getStringExtra( "familyUid"); // TODO

        etText=(EditText) findViewById(R.id.etText);
        btnSend=(Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                String stText=etText.getText().toString();
                if(stText.equals("")||stText.isEmpty()){
                    Toast.makeText(ChatActivity.this, "No string", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ChatActivity.this, email+","+stText, Toast.LENGTH_SHORT).show();

                    Calendar c= Calendar.getInstance();
                    SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate =df.format(c.getTime());


                    // Write a message to the database
                    //FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users").child(stChatId).child("chats").child(formattedDate); // ("chats").push().child(formattedDate); // push key


                    Hashtable<String,String> chat = new Hashtable<String, String>();
                    chat.put("email", email);
                    chat.put("text",stText);

                    myRef.setValue(chat);
                }

                stText = etText.getText().toString();
                Toast.makeText(ChatActivity.this, stText, Toast.LENGTH_SHORT).show();
                etText.setText(""); // TODO 2019 04 21
        }
        });
        Button btnFinish=(Button) findViewById(R.id.btnFinish);
       // btnFinish.setOnClickListener((view) {
                btnFinish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
              finish();
            }

        });
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mChat = new ArrayList<>();

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(mChat,email,ChatActivity.this);
        mRecyclerView.setAdapter(mAdapter);


        DatabaseReference myRef = database.getReference("users").child(stChatId).child("chats");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // A new comment has been added, add it to the displayed list
                Chat chat = dataSnapshot.getValue(Chat.class);

                // [START_EXCLUDE]
                // Update RecyclerView
                mChat.add(chat);
                mRecyclerView.scrollToPosition(mChat.size()-1);
                mAdapter.notifyItemInserted(mChat.size() - 1);
                // [END_EXCLUDE]

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

