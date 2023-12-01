package com.example.scheduling;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    ImageView profilebutton;
    ImageButton settingbutton;
    TextView usernameDisplay;
    private ActivityResultLauncher<Intent> galleryLauncher;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilebutton = view.findViewById(R.id.profileButton);
        settingbutton = view.findViewById(R.id.settingButton);
        usernameDisplay = view.findViewById(R.id.usernameDisplay);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            String uid = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild("username")){
                        String username = dataSnapshot.child("username").getValue(String.class);

                        usernameDisplay.setText(username);
                    } else {
                        usernameDisplay.setText("Default Display Name");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handling errors soon.
                }
            });
        }
//        return inflater.inflate(R.layout.fragment_profile, container, false);

        settingbutton.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(), Settings.class);
            startActivity(intent);
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null && data.getData() != null) {
                    Uri imageUri = data.getData();
                    //upload image
                    uploadImage(imageUri);
                }
            }
        });

        profilebutton.setOnClickListener(v -> openGallery());

        if(currentUser != null){
            String currentUserId = currentUser.getUid();
            DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId);

            ValueEventListener profilePictureListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                    String profilePictureUrl = dataSnapshot.getValue(String.class);
                    if (profilePictureUrl != null && !profilePictureUrl.isEmpty()){
                        Picasso.get().load(profilePictureUrl)
                                .fit().centerCrop()
                                .transform(new CircleTransformation())
                                .into(profilebutton);
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError){
                    Log.e("ProfileFragment", "Error loading profile picture: " + databaseError.getMessage());
                    Toast.makeText(getActivity(), "Error loading profile picture", Toast.LENGTH_SHORT).show();
                }
            };

            // Attach ValueEventListener to listen for changes
            currentUserRef.child("profilePictureUrl").addValueEventListener(profilePictureListener);

            // Store the listener in the fragment's tag to be able to remove it later
            view.setTag(profilePictureListener);
        }

        return view;
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void uploadImage(Uri imageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        String currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference currentUserRef = usersRef.child(currentUserId);

        StorageReference userProfileReference = storageReference.child("user_profile_pictures/" + currentUserId + ".jpg");

        // upload picture
        UploadTask uploadTask = userProfileReference.putFile(imageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return userProfileReference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                String fullImageUrl = downloadUri.toString();

                currentUserRef.child("profilePictureUrl").setValue(fullImageUrl)
                        .addOnSuccessListener(unused -> Toast.makeText(getActivity(), "Image Uploaded Successfully", LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(getActivity(), "Unsuccessfully added to Realtime DB", LENGTH_SHORT).show());
            } else {
                makeText(getActivity(),
                        "Unsuccessful Image Upload",
                        LENGTH_SHORT).show();
            }
        });
    }

    private static class CircleTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    @Override
    public void onDestroyView() {
        // Remove ValueEventListener when the fragment is destroyed to prevent memory leaks
        Object tag = getView().getTag();
        if (tag instanceof ValueEventListener) {
            DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            currentUserRef.child("profilePictureUrl").removeEventListener((ValueEventListener) tag);
        }

        super.onDestroyView();
    }

}