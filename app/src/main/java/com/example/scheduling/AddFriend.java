package com.example.scheduling;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        FirebaseApp.initializeApp(this);
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
                String email = emailEditText.getText().toString().trim();

                // search for email in db
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference("friends");
                usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            //email is registered in database
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String userId = userSnapshot.getKey();
                                String userName = userSnapshot.child("name").getValue(String.class);
                                //add to friends of current user
                                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                friendsRef.child(currentUserId).child(userId).setValue(userName);

                                Toast.makeText(AddFriend.this, "Friend added", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        else {
                            //email not registered in database
                            Toast.makeText(AddFriend.this, "Email Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFriend.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
