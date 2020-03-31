package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class RiderRateActivity extends AppCompatActivity {
    private String pickUp,dest,driver_name;
    private TextView location1,location2,driverName;
    private Button like,dislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_rate);

        /**
         * orderID = getExtras("orderID");
         * find order by order id
         * get driver name from order
         * get pick up, dest from order
         * find driver by driver name
         */

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        pickUp = "pick up test";
        dest = "dest test";
        driver_name = "driver name test";

        //pickUp = extras.getString("pickUp");
        //dest = extras.getString("dest");
        //driver_name = extras.getString("name");
//        DataBaseHelper DB = DataBaseHelper.getInstance();
//        final Driver driver = DB.getDriver(driver_name);

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
//                driver.increaseThumbUp();
                finish();
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                driver.increaseThumbDown();
                finish();
            }
        });
    }
}
