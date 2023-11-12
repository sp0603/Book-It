package com.example.scheduling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    ViewPager2 viewPager;
    ViewPagerAdapter myAdapter;
    TabLayout tabLayout;
    String[] tabNames = {"Home", "New Event", "Contacts", "Profile"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else {
            setContentView(R.layout.tablayout);
            tabLayout = findViewById(R.id.tablayout);
            viewPager = findViewById(R.id.viewPager2);

            myAdapter = new ViewPagerAdapter(
                    getSupportFragmentManager(),
                    getLifecycle()
            );
            myAdapter.setUpLayout(viewPager, tabLayout, tabNames);
        }
    }
}