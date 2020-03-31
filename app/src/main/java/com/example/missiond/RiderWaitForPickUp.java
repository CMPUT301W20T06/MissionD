package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Displays a map, driver's information, and pick up location and destination
 * Rider can call driver
 * @author
 *  Weiyi Wu, Xinrong Zhou
 * @version
 *  Mar.12 2020
 */
public class RiderWaitForPickUp extends AppCompatActivity implements RiderConfirmCancelDialog.RiderConfirmCancelDialogListener, OnMapReadyCallback
,TaskLoadedCallback{
    private ImageButton back;
    private Button confirm;
    private TextView driverName,pickUpText,destText;
    private SupportMapFragment mapFragment;
    private GoogleMap newMap;
    LatLng LatLng1,LatLng2;

    Float address1Lat,address1Lng,address2Lat,address2Lng;

    Marker Marker1;
    Marker Marker2;

    Polyline currentPolyline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_wait_for_pick_up);

        DataBaseHelper DB = DataBaseHelper.getInstance();

        driverName = findViewById(R.id.driverName);
        DB.getDriver("Yifei", new Consumer<Driver>() {
            @Override
            public void accept(Driver driver) {
                String driver_name = driver.getUserName();
                driverName.setText(driver_name);
            }
        });


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String pickUp = extras.getString("pickUp");
        final String dest = extras.getString("dest");
        address1Lat = extras.getFloat("startAddressLatitude");
        address1Lng = extras.getFloat("startAddressLongitude");
        address2Lat = extras.getFloat("destinationAddressLatitude");
        address2Lng = extras.getFloat("destinationAddressLongitude");

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        pickUpText = findViewById(R.id.Location1);
        pickUpText.setText(pickUp);
        destText = findViewById(R.id.Location2);
        destText.setText(dest);

        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderConfirmCancelDialog riderConfirmCancelDialog = new RiderConfirmCancelDialog();
                riderConfirmCancelDialog.show(getSupportFragmentManager(),"cancelConfirmDialog");
            }
        });


        driverName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverInfoDialog driverInfoDialog = new DriverInfoDialog();
                driverInfoDialog.show(getSupportFragmentManager(),"addMoneyFragment");
            }
        });

        confirm = findViewById(R.id.confirmPickUp_rider);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = new Bundle();
                extras.putString("pickUp",pickUp);
                extras.putString("dest",dest);

                extras.putFloat("startAddressLatitude", (float) address1Lat);
                extras.putFloat("startAddressLongitude", (float) address1Lng);
                extras.putFloat("destinationAddressLatitude", (float) address2Lat);
                extras.putFloat("destinationAddressLongitude", (float) address2Lng);
                Intent i = new Intent(RiderWaitForPickUp.this, RiderOnTripActivity.class);

                i.putExtras(extras);

                startActivity(i);

                finish();
            }
        });

    }

    /**
     * Cancel request and go back to the rider activity
     */
    @Override
    public void onCancelConfirmClick() {
        finish();
    }

    /**
     * Show pick up location and destination on the map
     * @param googleMap
     *  This is the map to be shown
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        newMap = googleMap;
//
        LatLng1 = new LatLng(address1Lat,address1Lng);
        LatLng2 = new LatLng(address2Lat,address2Lng);


        Marker1  = newMap.addMarker(new MarkerOptions().position(LatLng1).title("start address"));
        Marker2 = newMap.addMarker(new MarkerOptions().position(LatLng2).title("destination address"));

        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng1,11));
        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng2,11));

        String url = getUrl(Marker1.getPosition(),Marker2.getPosition(),"driving");
        new FetchURL(RiderWaitForPickUp.this).execute(url,"driving");

    }

    private String getUrl (LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" +
                getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = newMap.addPolyline((PolylineOptions)values[0]);


    }
}


