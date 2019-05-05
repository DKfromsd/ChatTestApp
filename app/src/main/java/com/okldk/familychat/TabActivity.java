package com.okldk.familychat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TabActivity extends AppCompatActivity {
    //private TextView mTextMessage;
    Fragment fragment;

    long lastPressed;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
    //                mTextMessage.setText("Home");//R.string.title_home);
                    fragment = new HomeFragment();
                    switchFragment(fragment);

                    return true;
                case R.id.navigation_dashboard:
    //                mTextMessage.setText("Family");//R.string.title_dashboard);
                    fragment = new FamilyFragment();
                    switchFragment(fragment);

                    return true;
                case R.id.navigation_notifications:
    //                mTextMessage.setText("Profile");//R.string.title_notifications);
                    fragment = new ProfileFragment();
                    switchFragment(fragment);

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment fragment = new HomeFragment();
        fragmentTransaction.add(R.id.content, fragment);
        fragmentTransaction.commit();

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    public void switchFragment(Fragment fragment){
        // Create new fragment and transaction
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.content, fragment);
        // Commit the transaction
        transaction.commit();

    }
    /*
    @Override
    public void onBackPressed(){

        if(System.currentTimeMillis()-lastPressed <1500){
            finish();
        }
        Toast.makeText(this,"one more time pressed, then finsih", Toast.LENGTH_SHORT).show();
        lastPressed = System.currentTimeMillis();
     } */
    @Override
    public void onBackPressed () {
        String message = "";
        message = "Back button is blocked to test. Please use menu item or Log out";
        // show message via toast
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_total, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // display a message when a button was pressed
        String message = "";
        if (item.getItemId() == R.id.Exit) {
            message = "Exit this app!";
        }
        // show message via toast
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
        finish();        //super.onDestroy();
        return true;

    }
}
