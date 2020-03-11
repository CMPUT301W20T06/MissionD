package com.example.mar05;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RiderMakeRequestActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private GoogleMap newMap;
    LatLng LatLng1;
    LatLng LatLng2;
    MarkerOptions MarkerOptions1;
    MarkerOptions MarkerOptions2;

    String pickUp;
    String dest;
    Float fare;
    Float address1Lat;
    Float address1Lng;
    Float address2Lat;
    Float address2Lng;

    private SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_make_request);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
         pickUp = extras.getString("RiderPickUp");
         dest = extras.getString("RiderDest");
         fare = extras.getFloat("EstimateFare");
         address1Lat = extras.getFloat("startAddressLatitude");
         address1Lng = extras.getFloat("startAddressLongitude");
         address2Lat = extras.getFloat("destinationAddressLatitude");
         address2Lng = extras.getFloat("destinationAddressLongitude");


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.newMap);

        mapFragment.getMapAsync(this);
        Toast.makeText(this,"hhhhhhh",Toast.LENGTH_SHORT).show();



    }
    @Override
   public void onMapReady(GoogleMap googleMap) {
        newMap = googleMap;
//
        LatLng1 = new LatLng(address1Lat,address1Lng);
        LatLng2 = new LatLng(address2Lat,address2Lng);


        newMap.addMarker(new MarkerOptions().position(LatLng1).title("start address"));
        newMap.addMarker(new MarkerOptions().position(LatLng2).title("destination address"));

        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng1,4));
        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng2,4));

    }
//
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
