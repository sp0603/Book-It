package com.example.scheduling;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private DatabaseReference databaseReference;
    private ArrayList<ListViewUser> eventList;
    private ListItemAdapter listItemAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }

//    @Override void onResume(){
//        super.onResume();
//        getEvents();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("events").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        eventList = new ArrayList<>();
        listItemAdapter = new ListItemAdapter(eventList, getContext());

        ListView listViewUsers = view.findViewById(R.id.eventListview);
        listViewUsers.setAdapter(listItemAdapter);

        databaseReference = databaseRef;

        getEvents();

        return view;
    }
    private void getEvents(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot eventSnapshot : snapshot.getChildren()){
                    String friendUid = eventSnapshot.getKey();
                    String eventUid = eventSnapshot.getKey();
                    String eventName = eventSnapshot.child("Name").getValue(String.class);
                    String eventDate = eventSnapshot.child("Date").getValue(String.class);
                    String eventStartTime = eventSnapshot.child("Start Time").getValue(String.class);
                    String eventEndTime = eventSnapshot.child("End Time").getValue(String.class);
                    String eventNotes = eventSnapshot.child("Notes").getValue(String.class);

                    if(friendUid != null && eventUid != null && eventName != null && eventDate != null && eventStartTime != null && eventEndTime != null && eventNotes != null) {
//                        eventList.add(new ListViewUser(eventName, eventDate,eventStartTime, eventEndTime, eventNotes));

                        // problem on this line

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

