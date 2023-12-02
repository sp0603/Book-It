package com.example.scheduling;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {
    Button userButton;
    Button passwordButton;
    Button emailButton;

    Button returnbutton;
    Button updateButton;
    Button logOutButton;

    EditText usernameEditText;
    EditText passwordEditText;
    EditText emailEditText;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        returnbutton = findViewById(R.id.backProfileButton);

        returnbutton.setOnClickListener(view -> finish());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        userButton = findViewById(R.id.usernameTextview);
        passwordButton = findViewById(R.id.passwordTextView);
        emailButton = findViewById(R.id.emailTextView);

        userButton.setOnClickListener(v -> {

            LinearLayout layout = new LinearLayout(Settings.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            usernameEditText = new EditText(Settings.this);
            usernameEditText.setText(userButton.getText().toString());
            layout.addView(usernameEditText);

            returnbutton = new Button(Settings.this);
            returnbutton.setText("Return");
            returnbutton.setOnClickListener(view -> finish());

            updateButton = new Button(Settings.this);
            updateButton.setText("Update");
            updateButton.setOnClickListener(v1 -> {
                updateUsername(usernameEditText.getText().toString());
                {finish();}
            });
            layout.addView(updateButton);
            layout.addView(returnbutton);

            setContentView(layout);
        });

        passwordButton.setOnClickListener(v -> {
            LinearLayout layout = new LinearLayout(Settings.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            passwordEditText = new EditText(Settings.this);
            passwordEditText.setText(passwordButton.getText().toString());
            layout.addView(passwordEditText);

            returnbutton = new Button(Settings.this);
            returnbutton.setText("Return");
            returnbutton.setOnClickListener(view -> finish());

            updateButton = new Button(Settings.this);
            updateButton.setText("Update");
            updateButton.setOnClickListener(view -> {
                updatePassword(passwordEditText.getText().toString());
                {finish();}
            });
            layout.addView(updateButton);
            layout.addView(returnbutton);

            setContentView(layout);

        });

        emailButton.setOnClickListener(v -> {
            LinearLayout layout = new LinearLayout(Settings.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            emailEditText = new EditText(Settings.this);
            emailEditText.setText(emailButton.getText().toString());
            layout.addView(emailEditText);

            returnbutton = new Button(Settings.this);
            returnbutton.setText("Return");
            returnbutton.setOnClickListener(view -> finish());

            updateButton = new Button(Settings.this);
            updateButton.setText("Update");
            updateButton.setOnClickListener(view -> {
                updateEmail(emailEditText.getText().toString());
                {finish();}
            });
            layout.addView(updateButton);
            layout.addView(returnbutton);

            setContentView(layout);
        });

        logOutButton = findViewById(R.id.logOutButton);

        logOutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
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