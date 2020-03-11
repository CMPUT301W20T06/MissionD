package com.example.mar05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RiderMakeRequestActivity extends AppCompatActivity implements OnMapReadyCallback {

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

        Bundle extras = getIntent().getExtras();
         pickUp = extras.getString("RiderPickUp");
         dest = extras.getString("RiderDest");
         fare = extras.getFloat("EstimateFare");
         address1Lat = extras.getFloat("startAddressLatitude");
         address1Lng = extras.getFloat("startAddressLongitude");
         address2Lat = extras.getFloat("destinationAddressLatitude");
         address2Lng = extras.getFloat("destinationAddressLongitude");

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }
    public void onMapReady(GoogleMap googleMap) {
        newMap = googleMap;

        LatLng1 = new LatLng(address1Lat,address1Lng);
        LatLng2 = new LatLng(address2Lat,address2Lng);

        MarkerOptions1.position(LatLng1);
        MarkerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

        MarkerOptions2.position(LatLng2);
        MarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

        newMap.addMarker(MarkerOptions1);
        newMap.addMarker(MarkerOptions2);

        newMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng1));
        newMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng2));



    }
}
