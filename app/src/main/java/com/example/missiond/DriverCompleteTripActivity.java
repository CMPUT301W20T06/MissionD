package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Display a map with driver current location
 * Driver can start to see trip requests by pressing see trip button
 * @author
 *  Weiting Chi
 * @version
 *  Mar.12 2020
 */
public class DriverCompleteTripActivity extends AppCompatActivity {
    private Button complete_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_complete_trip);

        complete_button = findViewById(R.id.complete_button);

        /**
         * press the complete button
         * will go to the ScanQRcode activity
         */
        complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverCompleteTripActivity.this, ScanQRcode.class);
                startActivity(intent);

            }
        });



    }

}
