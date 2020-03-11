package com.example.mar05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RiderWaitForAcceptActivity extends AppCompatActivity {
    private TextView pickUpLocation, Destination;
    private ImageButton close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_wait_for_accept);

        Bundle extras = getIntent().getExtras();
        String pickUp = extras.getString("pickupLoc");
        String dest = extras.getString("dest");

        pickUpLocation = findViewById(R.id.Location1Wait);
        Destination = findViewById(R.id.Location2Wait);
        close = findViewById(R.id.riderBackWait);

        pickUpLocation.setText(pickUp);
        Destination.setText(dest);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
