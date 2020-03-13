package com.example.missiond;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Once there is a drive to accept the request, the information about that drive will be displayed
 * Rider can choose to ride with this diver or wait for another driver
 * @author
 *  Xinrong Zhou, Weiyi Wu
 * @version
 *  Mar.12 2020
 */

public class RiderMakeRequestActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, RiderConfirmDriverDialog.RiderConfirmDriverListener, RiderConfirmCancelDialog.RiderConfirmCancelDialogListener {

    private GoogleMap newMap;
    LatLng LatLng1,LatLng2;
    MarkerOptions MarkerOptions1,MarkerOptions2;

    String pickUp,dest;
    Float fare,address1Lat,address1Lng,address2Lat,address2Lng;

    private SupportMapFragment mapFragment;
    private RiderConfirmDriverDialog confirmDriverDialog;
    private ImageButton close;
    private TextView pickupText,destText,next;


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


        pickupText = findViewById(R.id.Location1_makeRequest);
        pickupText.setText(pickUp);

        destText = findViewById(R.id.Location2_makeRequest);
        destText.setText(dest);

        close = findViewById(R.id.riderBack_makeRequest);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderConfirmCancelDialog riderConfirmCancelDialog = new RiderConfirmCancelDialog();
                riderConfirmCancelDialog.show(getSupportFragmentManager(),"cancelConfirmDialog");
            }
        });


        /*
        when there is a driver accepting the request
        while true:
            if request.getDriver != null:
                break and show fragment;
         */
        next = findViewById(R.id.next_makeRequest);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderConfirmDriverDialog confirmDriverDialog = new RiderConfirmDriverDialog();
                confirmDriverDialog.show(getSupportFragmentManager(),"confirmDriverFragment");
            }
        });
    }
    @Override
   public void onMapReady(GoogleMap googleMap) {
        newMap = googleMap;
//
        LatLng1 = new LatLng(address1Lat,address1Lng);
        LatLng2 = new LatLng(address2Lat,address2Lng);


        newMap.addMarker(new MarkerOptions().position(LatLng1).title("start address"));
        newMap.addMarker(new MarkerOptions().position(LatLng2).title("destination address"));

        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng1,8));
        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng2,8));

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

    /**
     * If rider confirm driver, rider waits for driver to pick up
     */
    @Override
    public void onConfirmClick() {
        Bundle extras = new Bundle();
        extras.putString("pickUp",pickUp);
        extras.putString("dest",dest);

        extras.putFloat("startAddressLatitude", (float) address1Lat);
        extras.putFloat("startAddressLongitude", (float) address1Lng);
        extras.putFloat("destinationAddressLatitude", (float) address2Lat);
        extras.putFloat("destinationAddressLongitude", (float) address2Lng);
        Intent i = new Intent(RiderMakeRequestActivity.this, RiderWaitForPickUp.class);

        i.putExtras(extras);

        startActivity(i);

        finish();
        /*
        change request status
         */
//        Toast.makeText(this,"test",Toast.LENGTH_SHORT).show();
    }

    /**
     * If rider confirm cancel, go back to the previous activity
     */
    @Override
    public void onCancelConfirmClick() {
        finish();
    }
}
