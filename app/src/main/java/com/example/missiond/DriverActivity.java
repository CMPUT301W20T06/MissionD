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

public class DriverActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button seeTrip_button;
    private Button profile_button;

    private SupportMapFragment driverMapFragment;

    private GoogleMap driverMap;

    final int LOCATION_REQUEST_CODE = 1;

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private static final String TAG = DriverActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        seeTrip_button = findViewById(R.id.driverSeeTrip);
        profile_button = findViewById(R.id.profile_driver);

        Driver user2 = new Driver("Yifei","795509105","user2@email.com");
        DataBaseHelper DB = DataBaseHelper.getInstance();
        DB.AddDriver(user2);

        driverMapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFrag);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DriverActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

        } else {

            driverMapFragment.getMapAsync((OnMapReadyCallback) this);
        }

        seeTrip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverActivity.this, DriverSearchRequestActivity.class);
                startActivity(intent);

            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        driverMap = googleMap;
        getDeviceLocation();

        driverMap.setMyLocationEnabled(true);

    }
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

                            driverMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 8));

                        } else {
                            //Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(DriverActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            //Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }

    }
}
