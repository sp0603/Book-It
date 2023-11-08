package com.example.scheduling;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ContactsPage extends Fragment {

    ListView listView;
    ArrayList<ListViewUser> userArrayList;
    private ListItemAdapter adapter;
    public ContactsPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts_page, container, false);

        //1- AdapterView: a ListView
        listView = view.findViewById(R.id.listview);

        //2- Data Source: ArrayList<User>
        userArrayList = new ArrayList<>();

        ListViewUser user1 = new ListViewUser("Joe Smith", "sample text", R.drawable.profileimg);
        ListViewUser user2 = new ListViewUser("Kara Adams", "sample text", R.drawable.profileimg);
        ListViewUser user3 = new ListViewUser("Jessica Lee", "sample text", R.drawable.profileimg);
        ListViewUser user4 = new ListViewUser("Hailey Cardenas", "sample text", R.drawable.profileimg);
        ListViewUser user5 = new ListViewUser("Andrew Ortega", "sample text", R.drawable.profileimg);
        ListViewUser user6 = new ListViewUser("Victor Jarvis", "sample text", R.drawable.profileimg);
        ListViewUser user7 = new ListViewUser("Matthew Beck", "sample text", R.drawable.profileimg);
        ListViewUser user8 = new ListViewUser("Mason Green", "sample text", R.drawable.profileimg);
        ListViewUser user9 = new ListViewUser("Breanna Fox", "sample text", R.drawable.profileimg);


        userArrayList.add(user1);
        userArrayList.add(user2);
        userArrayList.add(user3);
        userArrayList.add(user4);
        userArrayList.add(user5);
        userArrayList.add(user6);
        userArrayList.add(user7);
        userArrayList.add(user8);
        userArrayList.add(user9);

        //Adapter:
        adapter = new ListItemAdapter(userArrayList, requireContext());
        listView.setAdapter(adapter);

        //handling click events
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // toast message will pop up upon clicking on a list item
                // will change later to open up that user's profile?
                Toast.makeText(requireContext(), "TEST", Toast.LENGTH_SHORT).show();
            }
        });
        
        ///
        return view;
    }
}