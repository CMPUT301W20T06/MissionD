package com.example.mar05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RiderActivity extends AppCompatActivity implements initFragment.initFragmentListener{
    private initFragment initF;
    private mapFragment mapF;
    private String pickupLoc, DestLoc;
    private Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        initF = new initFragment();
        mapF = new mapFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.initFrag, initF)
                .commit();

        profile = findViewById(R.id.profile_rider);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RiderActivity.this,UserProfileActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onRiderStartTripClick() {
        Intent intent = new Intent(RiderActivity.this, MapActivity.class);
        startActivity(intent);

    }

    public String getPickupLoc(){
        return pickupLoc;
    }

    public String getDestLoc(){
        return DestLoc;
    }
}
