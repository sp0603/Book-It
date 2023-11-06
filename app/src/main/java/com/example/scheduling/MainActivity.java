package com.example.scheduling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;
    ViewPager2 viewPager;
    ViewPagerAdapter myAdapter;
    TabLayout tabLayout;
    String[] tabNames = {"Home", "New Event", "Contacts", "Profile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        //button = findViewById(R.id.logout);
        //textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        else {
            setContentView(R.layout.tablayout);
            tabLayout = findViewById(R.id.tablayout);

            myAdapter = new ViewPagerAdapter(
                    getSupportFragmentManager(),
                    getLifecycle()
            );

            // adding fragments to the list in the Adapter class
            myAdapter.addFragment(new HomeFragment());
            myAdapter.addFragment(new CreateEvent());
            myAdapter.addFragment(new ContactsPage());
            myAdapter.addFragment(new ProfileFragment());

            // set the orientation in ViewPager2
            viewPager = findViewById(R.id.viewPager2);
            viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

            // connecting the adapter with the ViewPager2
            viewPager.setAdapter(myAdapter);

            // connecting TabLayout with ViewPager
            new TabLayoutMediator(
                    tabLayout,
                    viewPager,
                    new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            tab.setText(tabNames[position]);
                        }
                    }
            ).attach();
        }

        // commented this out b/c I don't think it's being used currently
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(), Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}