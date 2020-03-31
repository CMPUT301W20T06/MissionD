package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * Displays a map, driver's information, and pick up location and destination
 * Rider can click "ARRIVE" to end the trip and pay
 * @author
 *  Weiyi Wu
 * @version
 *  Mar.12 2020
 */

public class RiderOnTripActivity extends AppCompatActivity implements RiderConfirmCancelDialog.RiderConfirmCancelDialogListener, RiderConfirmPayDialog.RiderConfirmPayDialogListener,
        OnMapReadyCallback {
    private ImageButton back;
    private Button confirm;
    private TextView driverName,pickUpText,destText;
    private SupportMapFragment mapFragment;
    private GoogleMap newMap;
    LatLng LatLng1,LatLng2;

    Float address1Lat,address1Lng,address2Lat,address2Lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_on_trip);

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

        confirm = findViewById(R.id.confirmArrival_rider);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderConfirmPayDialog riderConfirmPayDialog = new RiderConfirmPayDialog();
                riderConfirmPayDialog.show(getSupportFragmentManager(),"confirmAndPay");
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
     * Finish request and go to the pay activity
     */
    @Override
    public void onArriveConfirmClick() {
        Intent i = new Intent(RiderOnTripActivity.this, RiderEndPayActivity.class);
        startActivity(i);
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


        newMap.addMarker(new MarkerOptions().position(LatLng1).title("start address"));
        newMap.addMarker(new MarkerOptions().position(LatLng2).title("destination address"));

        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng1,8));
        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng2,8));


    }
}
