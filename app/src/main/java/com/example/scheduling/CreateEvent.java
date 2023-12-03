package com.example.scheduling;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public CreateEvent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);


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

        return view;
    }
    // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_create_event, container, false);

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
}

