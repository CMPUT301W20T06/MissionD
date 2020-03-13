package com.example.missiond;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Display a map
 * Rider can start a request by clicking "START" button
 * @author
 *  Xinrong Zhou, Weiyi Wu
 * @version
 *  Mar.12 2020
 */

public class RiderActivity extends AppCompatActivity implements initFragment.initFragmentListener, OnMapReadyCallback {
    private initFragment initF;
    private SupportMapFragment riderMapFragment;
    private String pickupLoc, DestLoc;
    private Button profile;

    private GoogleMap riderMap;
    final int LOCATION_REQUEST_CODE = 1;

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private static final String TAG = RiderActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        initF = new initFragment();
        //mapF = new mapFragment();

        Rider user1 = new Rider("Isaac","7807098105","user@email.com");

        DataBaseHelper DB = DataBaseHelper.getInstance();
        DB.AddRider(user1);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.initFrag, initF)
                .commit();

        riderMapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFrag);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RiderActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

        } else {

            riderMapFragment.getMapAsync((OnMapReadyCallback) this);
        }

        profile = findViewById(R.id.profile_rider);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RiderActivity.this,UserProfileActivity.class);
                startActivity(i);
            }
        });

    }

    /**
     * Rider starts a trip request
     */
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


    /**
     * This method will set the driver current location on the map
     *  @param googleMap
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        riderMap = googleMap;
        getDeviceLocation();

        riderMap.setMyLocationEnabled(true);


    }

    /**
     * This function wil get the driver current location
     */
    public void getDeviceLocation() {
        //Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            riderMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 8));

                        } else {
                            //Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(RiderActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            //Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }
}
