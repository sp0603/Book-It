package com.example.scheduling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListItemAdapter extends ArrayAdapter<ListViewUser> {
    //using layouts = MyCustomAdapter
    //using custom objects = extends ArrayAdapter<User>
    private ArrayList<ListViewUser> userArrayList;
    Context context;

    public ListItemAdapter(ArrayList<ListViewUser> userArrayList, Context context) {
        super(context, R.layout.list_item_layout, userArrayList);
        this.userArrayList = userArrayList;
        this.context = context;
    }

    //view holder class: used to cache references to the views within
    //               an item layout, so that they dont need to be repeatedly
    //               looked up during scrolling
    private static class MyViewHolder{
        TextView name;
        TextView sampleText;
        ImageView userImage;
    }

    //getView(): used to create and return a view for a specific item in the list

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the user object for the current position
        ListViewUser user = getItem(position);

        //inflate layout
        MyViewHolder myViewHolder;
        final View result;

        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(
                    R.layout.list_item_layout,
                    parent,
                    false
            );
            // finding Views
            myViewHolder.name = (TextView) convertView.findViewById(R.id.user_name);
            myViewHolder.sampleText = (TextView) convertView.findViewById(R.id.sampleText);
            myViewHolder.userImage = (ImageView) convertView.findViewById(R.id.imageView);

            result = convertView;

            convertView.setTag(myViewHolder);
        }else{
            //the view is recycled
            myViewHolder = (MyViewHolder) convertView.getTag();
            result = convertView;
        }

        // getting data from the model class
        myViewHolder.name.setText(user.getName());
        myViewHolder.sampleText.setText(user.getSampleText());
        myViewHolder.userImage.setImageResource(user.getUserImage());

        return result;
    }
}
