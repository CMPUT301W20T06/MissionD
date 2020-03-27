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
public class DriverPickeupRiderActivity extends AppCompatActivity {

    private Button pick_up_button;
    private TextView start_location;
    private TextView destination;
    private TextView rider_name;
    private TextView rider_phone;

    String Location;
    String Destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_pickup_rider);

        Location = getIntent().getExtras().getString("location");
        Destination = getIntent().getExtras().getString("destination");

        pick_up_button = findViewById(R.id.pickup_button);
        start_location = findViewById(R.id.start_location);
        destination = findViewById(R.id.Destination_text);
        //rider_name = findViewById(R.id.rider_name);
        //rider_phone = findViewById(R.id.rider_phone);

        start_location.setText(Location);
        destination.setText(Destination);

        pick_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverPickeupRiderActivity.this, DriverCompleteTripActivity.class);
                intent.putExtra("location",Location);
                intent.putExtra("destination",Destination);
                startActivity(intent);
            }
        });


    }
}
