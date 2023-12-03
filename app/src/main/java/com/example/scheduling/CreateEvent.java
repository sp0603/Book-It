package com.example.scheduling;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.nio.channels.Selector;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreateEvent extends Fragment {

    Button starttimeButton;
    Button endtimeButton;
    public int startHour, startMinute, endHour, endMinute;

    TextView starttimeTextView;
    TextView endtimeTextView;

    TextView selectedDateTextView;
    Button selectDateButton;
    Button bookEventButton;
    MultiAutoCompleteTextView addFriendACTextView;
    TextView eventNameTextView;
    EditText notesText;

    public CreateEvent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        // start time and date
        starttimeButton = view.findViewById(R.id.startTimeButton);
        endtimeButton = view.findViewById(R.id.endTimeButton);
        addFriendACTextView = view.findViewById(R.id.addFriendACTextView);
        bookEventButton = view.findViewById(R.id.bookeventButton);
        eventNameTextView = view.findViewById(R.id.nameofEvent);
        notesText = view.findViewById(R.id.notesField);
        starttimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showTimePickerDialog(true);
            }
        });
        ///////////////////////////////////////////////////////////////////

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currUser = auth.getCurrentUser();

        String currUserId = currUser.getUid();

        DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference()
                .child("friends").child(currUserId);

        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isAdded()) {
                    List<String> friendNames = new ArrayList<>();

                    for (DataSnapshot friendsnap : snapshot.getChildren()) {
                        String name = friendsnap.child("name").getValue(String.class);
                        friendNames.add(name);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, friendNames);
                    addFriendACTextView.setAdapter(adapter);
                    addFriendACTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                //errors
            }
        });

        bookEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(eventNameTextView.getText().toString())) {
                    Toast.makeText(getContext(),
                            "Enter Event Name",
                            Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(selectedDateTextView.getText().toString())) {
                    Toast.makeText(getContext(),
                            "Enter Date",
                            Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(starttimeTextView.getText().toString())) {
                    Toast.makeText(getContext(),
                            "Enter Start Time",
                            Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(endtimeTextView.getText().toString())) {
                    Toast.makeText(getContext(),
                            "",
                            Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(notesText.getText().toString())) {
                    Toast.makeText(getContext(),
                            "Enter Notes",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //save event
                    String eventName = eventNameTextView.getText().toString();
                    String eventDate = selectedDateTextView.getText().toString();
                    String startTime = starttimeTextView.getText().toString();
                    String endTime = endtimeTextView.getText().toString();
                    String notes = notesText.getText().toString();
                    String attendingFriends = addFriendACTextView.getText().toString();

                    String[] attendingFriendsArr = attendingFriends.split(", ");

                    //save event to db
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser currUser = auth.getCurrentUser();
                    String currUserId = currUser.getUid();

                    DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference()
                            .child("events").child(currUserId);
                    //create unique key for event
                    String eventId = eventsRef.push().getKey();

                    eventsRef.child(eventId).child("Name").setValue(eventName);
                    eventsRef.child(eventId).child("Date").setValue(eventDate);
                    eventsRef.child(eventId).child("Start Time").setValue(startTime);
                    eventsRef.child(eventId).child("End Time").setValue(endTime);
                    eventsRef.child(eventId).child("Notes").setValue(notes);

                    for (String friendName : attendingFriendsArr) {
                        DatabaseReference friendsRef = FirebaseDatabase.getInstance()
                                .getReference().child("friends").child(currUserId);
                        friendsRef.orderByChild("name").equalTo(friendName)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
                                            String friendId = friendSnapshot.getKey();

                                            DatabaseReference friendEventsRef = FirebaseDatabase.getInstance()
                                                    .getReference().child("users").child(friendId).child("events");
                                            friendEventsRef.child(eventId).setValue("attending");
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        //
                                    }
                                });
                    }
                    Toast.makeText(getContext(), "Event Created", Toast.LENGTH_SHORT).show();
                    starttimeTextView.setText("0:00");
                    endtimeTextView.setText("0:00");
                    selectedDateTextView.setText("MM/DD/YYYY");
                    eventNameTextView.setText("");
                    notesText.setText("");
                    addFriendACTextView.setText("");
                }
            }
        });

        ///////////////////////////////////////////////////////////////////
        endtimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showTimePickerDialog(false);
            }
        });

        // selected day

        selectedDateTextView = view.findViewById(R.id.dateDisplay);
        selectDateButton = view.findViewById(R.id.eventDate);

        selectDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onPickDateClick(view);
            }
        });

        //add friend to event
        //not yet completed

        return view;
    }
    // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_create_event, container, false);

    //start time and end time

    private void showTimePickerDialog(final boolean isStartTime){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(isStartTime){
                            startHour = hourOfDay;
                            startMinute = minute;
                            updateStartTime();
                        } else {
                            endHour = hourOfDay;
                            endMinute = minute;
                            updateEndTime();
                        }
                    }
                },
                hour,
                minute,
                DateFormat.is24HourFormat(getContext())
        );

        timePickerDialog.updateTime(hour, (minute/30)*30);

        timePickerDialog.show();
    }
    private void updateStartTime(){
        starttimeTextView = getView().findViewById(R.id.startTime);
        String amPm = startHour < 12 ? "AM" : "PM";
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String formattedTime = sdf.format(getTimeInMillis(startHour, startMinute));
        starttimeTextView.setText(formattedTime + " ");
    }

    private void updateEndTime(){
        endtimeTextView = getView().findViewById(R.id.endTime);
        String amPm = endHour < 12 ? "AM" : "PM";
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String formattedTime = sdf.format(getTimeInMillis(endHour, endMinute));
        endtimeTextView.setText(formattedTime + " ");

    }

    private long getTimeInMillis(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTimeInMillis();
    }

    // selected day, month, and year

    public void onPickDateClick(View view){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectDate = (monthOfYear + 1) + "/" + dayOfMonth  + "/" + year;
                        selectedDateTextView.setText(selectDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }
}