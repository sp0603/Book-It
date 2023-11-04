package com.example.scheduling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ContactsPage extends Fragment {

    ListView listView;
    ArrayList<User> userArrayList;
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

        User user1 = new User("Joe Smith", "sample text", R.drawable.profileimg);
        User user2 = new User("Kara Adams", "sample text", R.drawable.profileimg);
        User user3 = new User("Jessica Lee", "sample text", R.drawable.profileimg);
        User user4 = new User("Hailey Cardenas", "sample text", R.drawable.profileimg);
        User user5 = new User("Andrew Ortega", "sample text", R.drawable.profileimg);
        User user6 = new User("Victor Jarvis", "sample text", R.drawable.profileimg);
        User user7 = new User("Matthew Beck", "sample text", R.drawable.profileimg);
        User user8 = new User("Mason Green", "sample text", R.drawable.profileimg);
        User user9 = new User("Breanna Fox", "sample text", R.drawable.profileimg);


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