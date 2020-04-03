package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

/**
 * Rider can rate driver
 * @author
 *  Weiyi Wu
 * @version
 *  Mar.28 2020
 */

public class RiderRateActivity extends AppCompatActivity {
    private String pickUp,dest,driver_name;
    private TextView location1,location2,driverName;
    private Button like,dislike;
    private Driver driver1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_rate);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        pickUp = extras.getString("pickUp");
        dest = extras.getString("dest");
        driver_name = extras.getString("driver");

        final DataBaseHelper DB = DataBaseHelper.getInstance();
        DB.getDriver(driver_name, new Consumer<Driver>() {
            @Override
            public void accept(Driver driver) {
                driver1 = driver;
            }
        });

        driverName = findViewById(R.id.driverName);
        location1 = findViewById(R.id.Location1);
        location2 = findViewById(R.id.Location2);
        driverName.setText(driver_name);
        location1.setText(pickUp);
        location2.setText(dest);

        like = findViewById(R.id.like);
        dislike = findViewById(R.id.dislike);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driver1.increaseThumbUp();
                DB.UpdateDriverData(driver1);
                finish();
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driver1.increaseThumbDown();
                DB.UpdateDriverData(driver1);
                finish();
            }
        });
    }
}
