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

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class ListItemAdapter extends ArrayAdapter<ListViewUser> {
    //using layouts = MyCustomAdapter
    //using custom objects = extends ArrayAdapter<ListViewUser>
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
        ImageView profilePicture;
        TextView name;
    }

    //getView(): used to create and return a view for a specific item in the list

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the user object for the current position
        ListViewUser listViewUser = getItem(position);

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
            myViewHolder.name = convertView.findViewById(R.id.user_name);
            myViewHolder.profilePicture = convertView.findViewById(R.id.profilePic);

            result = convertView;

            convertView.setTag(myViewHolder);
        }else{
            //the view is recycled
            myViewHolder = (MyViewHolder) convertView.getTag();
            result = convertView;
        }
        if (listViewUser.getProfilePictureUrl() != null && !listViewUser.getProfilePictureUrl().isEmpty()) {
            String fullUrl = listViewUser.getProfilePictureUrl();
            Picasso.get().load(fullUrl)
                    .fit().centerCrop().transform(new ProfileFragment.CircleTransformation())
                    .into(myViewHolder.profilePicture);
        }
        // getting data from the model class
        if (listViewUser != null) {
            myViewHolder.name.setText(listViewUser.getName());
        }
        return result;
    }
}
