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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ContactsPage extends Fragment {

    DatabaseReference databaseReference;
    ArrayList<ListViewUser> friendsList;
    ListItemAdapter listItemAdapter;

    public ContactsPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts_page, container, false);

        ImageButton addFriendButton = view.findViewById(R.id.imageButton2);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFriend.class);
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
                // toast message will pop up upon clicking on a list item
                Toast.makeText(requireContext(), "TEST", Toast.LENGTH_SHORT).show();
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
                    String friendName = friendSnapshot.getValue(String.class);

                    if (friendName != null) {
                        friendsList.add(new ListViewUser(friendName));
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