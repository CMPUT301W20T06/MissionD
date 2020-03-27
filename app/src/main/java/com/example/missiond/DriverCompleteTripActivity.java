package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Display a map with driver current location
 * Driver can start to see trip requests by pressing see trip button
 * @author
 *  Weiting Chi
 * @version
 *  Mar.26 2020
 */
public class DriverCompleteTripActivity extends AppCompatActivity {
    private Button completet_button;
    private TextView start_location;
    private TextView destination;
    private TextView rider_name;
    private TextView rider_phone;


    private String Location;
    private String Destination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_complete_trip);

        Location = getIntent().getExtras().getString("location");
        Destination = getIntent().getExtras().getString("destination");

        start_location = findViewById(R.id.start_location);
        destination = findViewById(R.id.Destination_text);
        //rider_name = findViewById(R.id.rider_name);
        //rider_phone = findViewById(R.id.rider_phone);

        start_location.setText(Location);
        destination.setText(Destination);

        completet_button = findViewById(R.id.complete_button);

        /**
         * press the complete button
         * will go to the ScanQRcode activity
         */
        completet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverCompleteTripActivity.this, ScanQRcode.class);
                startActivity(intent);

            }
        });



    }

}
