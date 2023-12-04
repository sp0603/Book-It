package com.example.scheduling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListItemEventAdapter extends ArrayAdapter<ListViewEvent> {
    private ArrayList<ListViewEvent> eventArrayList;
    Context context;

    public ListItemEventAdapter(ArrayList<ListViewEvent> eventArrayList, Context context) {
        super(context, R.layout.event_list_item_layout, eventArrayList);
        this.eventArrayList = eventArrayList;
        this.context = context;
    }

    private static class MyViewHolder{
        TextView eventName;
        TextView eventDate;
        TextView eventStartTime;
        TextView eventEndTime;
        TextView eventNotes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListViewEvent listViewEvent = getItem(position);

        MyViewHolder myViewHolder;
        final View result;

        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(
                    R.layout.event_list_item_layout,
                    parent,
                    false
            );
            myViewHolder.eventName = convertView.findViewById(R.id.eventNameTV);
            myViewHolder.eventStartTime = convertView.findViewById(R.id.startTimeTV);
            myViewHolder.eventEndTime = convertView.findViewById(R.id.endTimeTV);
            myViewHolder.eventDate = convertView.findViewById(R.id.dateTV);
            myViewHolder.eventNotes = convertView.findViewById(R.id.notesTV);

            result = convertView;

            convertView.setTag(myViewHolder);
        }else {
            myViewHolder = (MyViewHolder) convertView.getTag();
            result  = convertView;
        }
        myViewHolder.eventName.setText(listViewEvent.getEventName());
        myViewHolder.eventStartTime.setText(listViewEvent.getStartTime());
        myViewHolder.eventEndTime.setText(listViewEvent.getEndTime());
        myViewHolder.eventDate.setText(listViewEvent.getEventDate());
        myViewHolder.eventNotes.setText(listViewEvent.getNotes());

        return result;
    }
}
