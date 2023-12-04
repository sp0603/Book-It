package com.example.scheduling;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class HomeFragment extends Fragment {

    private DatabaseReference databaseRef;
    private ArrayList<ListViewEvent> eventList;
    private ListItemEventAdapter eventlistItemAdapter;
    Button yesButton;
    Button noButton;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume(){
        super.onResume();
        eventList.clear();
        getEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);

        databaseRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("events");

        if (eventList == null) {
            eventList = new ArrayList<>();
        }
        eventlistItemAdapter = new ListItemEventAdapter(eventList, getContext());

        ListView listViewEvents = view.findViewById(R.id.eventListview);
        listViewEvents.setAdapter(eventlistItemAdapter);

        eventList.clear();

        getEvents();

        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                yesButton = new Button(getContext());
                yesButton.setText("Yes");
                layout.addView(yesButton);
                yesButton.setOnClickListener(view1 -> {
                    {getActivity().finish();}
                });

                noButton = new Button(getContext());
                noButton.setText("No");
                layout.addView(noButton);
                noButton.setOnClickListener(view2 -> {
                    deleteEvent(position);
                });

                getActivity().setContentView(layout);
            }
        });

        return view;
    }

    private void getEvents(){
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totalEvents = (int) snapshot.getChildrenCount();
                AtomicInteger eventsProcessed = new AtomicInteger(0);
                for (DataSnapshot eventSnapshot : snapshot.getChildren()){

                    String eventID = eventSnapshot.getKey();
                    String userCreateEventId = eventSnapshot.getValue(String.class);

                    DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference()
                            .child("events").child(userCreateEventId).child(eventID);

                    eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                String eventName = snapshot.child("Name").getValue(String.class);
                                String eventStartTime = snapshot.child("Start Time").getValue(String.class);
                                String eventEndTime = snapshot.child("End Time").getValue(String.class);
                                String eventDate = snapshot.child("Date").getValue(String.class);
                                String eventNotes = snapshot.child("Notes").getValue(String.class);


                                eventList.add(new ListViewEvent(eventName, eventStartTime, eventEndTime, eventNotes, eventDate, eventID));

                                if (eventsProcessed.incrementAndGet() == totalEvents) {
                                    eventlistItemAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle errors here
            }
        });
    }
    private void deleteEvent(int position) {
        if (position >= 0 && position < eventList.size()) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            String eventId = eventList.get(position).getEventID();

            DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(userId).child("events").child(eventId);

            eventRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    eventList.remove(position);
                    eventlistItemAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}