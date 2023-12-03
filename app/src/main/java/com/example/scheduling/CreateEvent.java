package com.example.scheduling;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateEvent extends Fragment {

    Button starttimeButton;
    Button endtimeButton;
    public int startHour, startMinute, endHour, endMinute;

    TextView starttimeTextView;
    TextView endtimeTextView;

    TextView selectedDateTextView;
    Button selectDateButton;

    Button addFriendButton;

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

        starttimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showTimePickerDialog(true);
            }
        });

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

        addFriendButton = view.findViewById(R.id.addFriendEvent);

        addFriendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // fill in the gaps
            }
        });

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

