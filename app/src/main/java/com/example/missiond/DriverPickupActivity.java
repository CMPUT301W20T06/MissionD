package com.example.missiond;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Let Driver to choose his/her pick up location.
 * @author
 *  Weiting Chi and Xinrong Zhou
 * @version
 *  Mar.29 2020
 */
public class DriverPickupActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerDragListener {
    private ImageButton back_button;
    private ImageButton confrim_buton;
    private Button search_button;

    private SupportMapFragment mapFragment;

    private String driver_name;

    private EditText pickUpLocation;

    MarkerOptions userMarkerOptions = new MarkerOptions();

    Marker Marker1;

    GoogleMap mMap;

    String pickupName;
    Address pickupAddress;

    LatLng LatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_pickup);

        back_button = findViewById(R.id.driverLocBack);
        confrim_buton = findViewById(R.id.search_address1);
        confrim_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               searchPickupLocation();

            }
        });
        search_button = findViewById(R.id.searchOrder);

        pickUpLocation = findViewById(R.id.pickup_Location);

        driver_name= getIntent().getExtras().getString("user_name");

        /**
         * Go back to the previous page
         */
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        /**
         * Go to the new request list activity
         */
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Marker1 != null){
                Intent intent = new Intent(DriverPickupActivity.this, DriverSearchRequestActivity.class);
                intent.putExtra("pickup_location",pickupName);
                intent.putExtra("pickupLat",(float)Marker1.getPosition().latitude);
                intent.putExtra("pickupLng",(float)Marker1.getPosition().longitude);
                intent.putExtra("user_name",driver_name);
                startActivity(intent);

            }
                else {
                }
            }
        });


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);



    }
    public void searchPickupLocation(){
        EditText addressField1 =  findViewById(R.id.pickup_Location);
        pickupName = addressField1.getText().toString();

        List<Address> addressList1 = null;

        if(!TextUtils.isEmpty(pickupName)){
            Geocoder geocoder = new Geocoder(this);
            try
            {
                addressList1 = geocoder.getFromLocationName(pickupName,1);
//                        if(addressList1 != null)
                try
                {
                    pickupAddress = addressList1.get(0);
                    LatLng = new LatLng(pickupAddress.getLatitude(),pickupAddress.getLongitude());


                    userMarkerOptions.position(LatLng);
                    userMarkerOptions.title(pickupName);
                    userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                    if (Marker1 != null){
                        Marker1.remove();
                    }

                    Marker1 = mMap.addMarker(userMarkerOptions);
                    Marker1.setDraggable(true);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng));

                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }
                catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }else {
            Toast.makeText(this,"please write any location name",Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerDragListener(this);

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {


    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(this, String.valueOf(marker.getPosition()), Toast.LENGTH_LONG).show();

    }
}
