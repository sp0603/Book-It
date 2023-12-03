package com.example.scheduling;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


public class CreateEvent extends Fragment {

    EditText edit_event_name, edit_event_date, edit_event_address, edit_event_description, edit_event_start_time, edit_event_end_time;
    Button invite_friends_button, create_event_button;
    FirebaseDatabase database;
    DatabaseReference reference;

    public CreateEvent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_event, container, false);

        edit_event_name = view.findViewById(R.id.event_name);
        edit_event_date = view.findViewById(R.id.event_date);
        edit_event_address = view.findViewById(R.id.event_address);
        edit_event_description = view.findViewById(R.id.event_description);
        edit_event_start_time = view.findViewById(R.id.event_start_time);
        edit_event_end_time = view.findViewById(R.id.event_end_time);

        invite_friends_button = view.findViewById(R.id.invite_friends_button);
        create_event_button = view.findViewById(R.id.create_event_button);


        create_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userID = currentUser.getUid();

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("createdEvents");

                    String event_name = edit_event_name.getText().toString();
                    String event_date = edit_event_date.getText().toString();
                    String event_address = edit_event_address.getText().toString();
                    String event_description = edit_event_description.getText().toString();
                    String event_start_time = edit_event_start_time.getText().toString();
                    String event_end_time = edit_event_end_time.getText().toString();

                    if (TextUtils.isEmpty(event_name)) {
                        Toast.makeText(getActivity(), "Enter event name", Toast.LENGTH_SHORT).show();
                    }

                    if (TextUtils.isEmpty(event_date)) {
                        Toast.makeText(getActivity(), "Enter event date", Toast.LENGTH_SHORT).show();
                    }

                    if (TextUtils.isEmpty(event_address)) {
                        Toast.makeText(getActivity(), "Enter event address", Toast.LENGTH_SHORT).show();
                    }

                    if (TextUtils.isEmpty(event_description)) {
                        Toast.makeText(getActivity(), "Enter event description", Toast.LENGTH_SHORT).show();
                    }

                    if (TextUtils.isEmpty(event_start_time)) {
                        Toast.makeText(getActivity(), "Enter event start time", Toast.LENGTH_SHORT).show();
                    }

                    if (TextUtils.isEmpty(event_end_time)) {
                        Toast.makeText(getActivity(), "Enter event end time", Toast.LENGTH_SHORT).show();
                    }

                    Event createdEvents = new Event(event_name, event_date, event_address, event_description, event_start_time, event_end_time);

                    DatabaseReference userEventsRef = reference.child("users").child(userID).child("createdEvents");

                    userEventsRef.push().setValue(new Event(event_name, event_date, event_address, event_description, event_start_time, event_end_time))
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Event Created", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), "Error creating event", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "User not authenticated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        invite_friends_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Invite friends clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public static class Event {
        public String eventName;
        public String eventDate;
        public String eventAddress;
        public String eventDescription;
        public String eventStartTime;
        public String eventEndTime;

        public Event() {
            //empty constructor
        }

        public Event(String eventName, String eventDate, String eventAddress, String eventDescription, String eventStartTime, String eventEndTime) {
            this.eventName = eventName;
            this.eventDate = eventDate;
            this.eventAddress = eventAddress;
            this.eventDescription = eventDescription;
            this.eventStartTime = eventStartTime;
            this.eventEndTime = eventEndTime;
        }
    }
}