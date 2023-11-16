package com.example.scheduling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    Button returnbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        returnbutton = findViewById(R.id.backProfileButton);

        returnbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {finish();}
        });
    }
}