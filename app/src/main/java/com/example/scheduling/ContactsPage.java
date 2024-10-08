package com.example.scheduling;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ContactsPage extends Fragment {

    private DatabaseReference databaseReference;
    private ArrayList<ListViewUser> friendsList;
    private ListItemAdapter listItemAdapter;

    public ContactsPage() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getFriends();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts_page, container, false);

        ImageButton addFriendButton = view.findViewById(R.id.imageButtonAdd);
        ImageButton removeFriendButton = view.findViewById(R.id.imageButtonRemove);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFriend.class);
                startActivity(intent);
            }
        });

        removeFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RemoveFriend.class);
                startActivity(intent);
            }
        });

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("friends")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        friendsList = new ArrayList<>();
        listItemAdapter = new ListItemAdapter(friendsList, getContext());

        ListView listViewUsers = view.findViewById(R.id.listview);
        listViewUsers.setAdapter(listItemAdapter);

        databaseReference = databaseRef;

        getFriends();

        //handling click events
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });
        return view;
    }
    private void getFriends() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friendsList.clear();
                for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
                    String friendUid = friendSnapshot.getKey();
                    String friendName = friendSnapshot.child("name").getValue(String.class);
                    String friendProfilePictureUrl = friendSnapshot.child("userProfilePictureUrl").getValue(String.class);

                    if (friendUid != null && friendName != null && friendProfilePictureUrl != null) {
                        friendsList.add(new ListViewUser(friendName, friendProfilePictureUrl));
                    }
                }
                listItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle errors here
            }
        });
    }
}