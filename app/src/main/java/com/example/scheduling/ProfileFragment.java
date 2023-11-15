package com.example.scheduling;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    ImageButton profilebutton;
    ImageButton settingbutton;
    TextView usernameText;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilebutton = view.findViewById(R.id.profileButton);
        settingbutton = view.findViewById(R.id.settingButton);
        usernameText = view.findViewById(R.id.usernameDisplay);

//        return inflater.inflate(R.layout.fragment_profile, container, false);

        settingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(requireContext(), Settings.class);
                startActivity(intent);
            }
        });

        return view;

    }
}