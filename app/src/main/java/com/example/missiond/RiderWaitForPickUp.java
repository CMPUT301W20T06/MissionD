package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.List;

/**
 * Displays a map, driver's information, and pick up location and destination
 * Rider can call or email driver
 * Can automatically check order data
 * @author
 *  Weiyi Wu, Xinrong Zhou
 * @version
 *  April.01 2020
 */
public class RiderWaitForPickUp extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback{
    private boolean isPickUp = false;
    private Handler handler = new Handler();
    private Order order1;
    private String id,driver_name,pickUp,dest;
    private ImageButton back;
    private Button confirm;
    private TextView driverName,pickUpText,destText;
    private SupportMapFragment mapFragment;
    private GoogleMap newMap;
    final DataBaseHelper DB = DataBaseHelper.getInstance();
    LatLng LatLng1,LatLng2;

    Float address1Lat,address1Lng,address2Lat,address2Lng;

    Marker Marker1;
    Marker Marker2;

    Polyline currentPolyline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_wait_for_pick_up);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        pickUp = extras.getString("pickUp");
        dest = extras.getString("dest");
        id = extras.getString("orderID");
        driver_name = extras.getString("driver");

        address1Lat = extras.getFloat("startAddressLatitude");
        address1Lng = extras.getFloat("startAddressLongitude");
        address2Lat = extras.getFloat("destinationAddressLatitude");
        address2Lng = extras.getFloat("destinationAddressLongitude");

        driverName = findViewById(R.id.driverName);
        DB.getDriver(driver_name, new Consumer<Driver>() {
            @Override
            public void accept(Driver driver) {
                String driver_name = driver.getUserName();
                driverName.setText(driver_name);
            }
        });

        startRepeating();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        pickUpText = findViewById(R.id.Location1);
        pickUpText.setText(pickUp);
        destText = findViewById(R.id.Location2);
        destText.setText(dest);


        driverName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("driver",driver_name);
                DriverInfoDialog driverInfoDialog = new DriverInfoDialog();
                driverInfoDialog.setArguments(bundle);
                driverInfoDialog.show(getSupportFragmentManager(),"addMoneyFragment");
            }
        });

        confirm = findViewById(R.id.confirmPickUp_rider);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * order.setOrderStatus(4)  //pick up but not finish
                 * pass orderID to next activity
                 */
                order1.setOrderStatus(4);
                DB.updateOrder(order1);
            }
        });

    }

    public void startRepeating(){
        runnable.run();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isPickUp){

                Bundle extras = new Bundle();
                extras.putString("pickUp",pickUp);
                extras.putString("dest",dest);
                extras.putString("orderID",id);
                extras.putString("driver",driver_name);

                extras.putFloat("startAddressLatitude", (float) address1Lat);
                extras.putFloat("startAddressLongitude", (float) address1Lng);
                extras.putFloat("destinationAddressLatitude", (float) address2Lat);
                extras.putFloat("destinationAddressLongitude", (float) address2Lng);
                Intent i = new Intent(RiderWaitForPickUp.this, RiderOnTripActivity.class);

                i.putExtras(extras);

                startActivity(i);

                finish();
            }
            else{
                getOrder();
                handler.postDelayed(this, 2000);
            }
        }
    };

    private void getOrder(){
        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                for (int i=0;i <orders.size();i++) {
                    Order order = orders.get(i);
                    if (order.getId().equals(id)) {
                        order1 = order;
                    }
                }
                onLoaded();
            }
        });
    }

    public void onLoaded(){
        if (order1.getOrderStatus()==4) {
            isPickUp=true;
        }
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


