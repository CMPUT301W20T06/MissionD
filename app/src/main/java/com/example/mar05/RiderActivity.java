package com.example.mar05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class RiderActivity extends AppCompatActivity implements initFragment.initFragmentListener{
    private initFragment initF;
    private mapFragment mapF;
    private String pickupLoc, DestLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        initF = new initFragment();
        mapF = new mapFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.initFrag, initF)
                .commit();

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
