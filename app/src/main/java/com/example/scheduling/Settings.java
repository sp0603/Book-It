package com.example.scheduling;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {

    Button returnbutton;
    Button userButton;
    Button updateButton;
    EditText usernameEditText;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        returnbutton = findViewById(R.id.backProfileButton);

        returnbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {finish();}
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        userButton = findViewById(R.id.usernameTextview);

        userButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                LinearLayout layout = new LinearLayout(Settings.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                usernameEditText = new EditText(Settings.this);
                usernameEditText.setText(userButton.getText().toString());
                layout.addView(usernameEditText);

                updateButton = new Button(Settings.this);
                updateButton.setText("Update");
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        updateUsername(usernameEditText.getText().toString());
                        {finish();}
                    }
                });
                layout.addView(updateButton);

                setContentView(layout);
            }
        });
    }

    private void updateUsername(String newUsername){
        databaseReference.child("username").setValue(newUsername);

        LayoutInflater inflater = getLayoutInflater();
        View originalLayout = inflater.inflate(R.layout.activity_settings, null);
        setContentView(originalLayout);
    }

    private void updatePassword(String newPassword){
        databaseReference.child("password").setValue(newPassword);

        LayoutInflater inflater = getLayoutInflater();
        View originalLayout = inflater.inflate(R.layout.activity_settings, null);
        setContentView(originalLayout);
    }

    private void updateEmail(String newEmail){
        databaseReference.child("email").setValue(newEmail);

        LayoutInflater inflater = getLayoutInflater();
        View originalLayout = inflater.inflate(R.layout.activity_settings, null);
        setContentView(originalLayout);
    }
}