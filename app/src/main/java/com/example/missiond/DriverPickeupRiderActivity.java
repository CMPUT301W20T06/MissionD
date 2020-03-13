package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DriverPickeupRiderActivity extends AppCompatActivity {

    private Button pick_up_button;
    private TextView start_location;
    private TextView destination;
    private TextView rider_name;
    private TextView rider_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_pickup_rider);

        pick_up_button = findViewById(R.id.pickup_button);
        //start_location = findViewById(R.id.start_location);
        //destination = findViewById(R.id.destination_text);
        //rider_name = findViewById(R.id.rider_name);
        //rider_phone = findViewById(R.id.rider_phone);

        pick_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverPickeupRiderActivity.this, DriverCompleteTripActivity.class);
                startActivity(intent);
            }
        });


    }
}
