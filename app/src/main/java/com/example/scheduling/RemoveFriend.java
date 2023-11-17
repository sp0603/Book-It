package com.example.scheduling;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RemoveFriend extends AppCompatActivity {

    TextInputLayout textInputLayout;
    TextInputEditText emailEditText;
    Button backButton;
    Button removeFriendButton;

    public RemoveFriend() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.remove_friend);

        textInputLayout = findViewById(R.id.removeFriendEditText);
        emailEditText = textInputLayout.findViewById(R.id.removeFriendEmail);
        removeFriendButton = findViewById(R.id.removeFriendButton);
        backButton = findViewById(R.id.removeFriendBackButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        removeFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference("friends");

                usersRef.orderByChild("email").equalTo(email)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //email is in database
                        if (snapshot.exists()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String userId = userSnapshot.getKey();

                                //remove friend
                                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                friendsRef.child(currentUserId).child(userId).removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RemoveFriend.this,
                                                "Friend Removed",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RemoveFriend.this,
                                                        "Failed to Remove Friend",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                        else {
                            Toast.makeText(RemoveFriend.this,
                                    "Email Not Found",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(RemoveFriend.this,
                                "ERROR",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
