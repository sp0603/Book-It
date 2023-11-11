package com.example.scheduling;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddFriend extends AppCompatActivity {
    TextInputLayout textInputLayout;
    TextInputEditText emailEditText;
    Button searchButton;
    Button backButton;

    public AddFriend() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        textInputLayout = findViewById(R.id.addFriendEditText);
        emailEditText = textInputLayout.findViewById(R.id.addFriendEmail);
        searchButton = findViewById(R.id.addFriendButton);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                // search for email in db, and if found add to user's friends
            }
        });
    }
}
