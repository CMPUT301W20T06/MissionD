package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Display a map with driver current location
 * Driver can start to see trip requests by pressing see trip button
 * @author
 *  Weiting Chi, Xinrong Zhou
 * @version
 *  Mar.30 2020
 */
public class DriverCompleteTripActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private Button completet_button;
    private TextView start_location;
    private TextView destination;
    private TextView rider_name;

    private SupportMapFragment newMapFragment;

    private String Location;
    private String Destination;

    private LatLng LatLng1;
    private LatLng LatLng2;

    MarkerOptions MarkerOptions1 = new MarkerOptions();
    MarkerOptions MarkerOptions2 = new MarkerOptions();

    private Polyline currentPolyline;
    private GoogleMap completeMap;
    String Rider;
    private String Order_id;

    private float startLat,startLng,endLat,endLng;
    final DataBaseHelper DB = DataBaseHelper.getInstance();
    private Order order1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_complete_trip);



        Location = getIntent().getExtras().getString("location");
        Destination = getIntent().getExtras().getString("destination");
        startLat = getIntent().getExtras().getFloat("startLocationLat");
        startLng = getIntent().getExtras().getFloat("startLocationLng");
        endLat = getIntent().getExtras().getFloat("endLocationLat");
        endLng = getIntent().getExtras().getFloat("endLocationLng");
        Rider = getIntent().getExtras().getString("rider");
        Order_id = getIntent().getExtras().getString("order_id");


        start_location = findViewById(R.id.start_location);
        destination = findViewById(R.id.Destination_text);
        rider_name = findViewById(R.id.rider_name);
        rider_name.setText(Rider);


        start_location.setText(Location);
        destination.setText(Destination);

        completet_button = findViewById(R.id.complete_button);


        /**
         * Use the database helper to get the order
         * and set the order status
         * update status
         */
        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                for (int i=0;i <orders.size();i++) {
                    Order order = orders.get(i);
                    if (order.getId().equals(Order_id)) {
                        order1=order;
                        order1.setOrderStatus(4);
                        DB.updateOrder(order1);
                        //Toast.makeText(getActivity(),order1.getOrderStatus().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /**
         * press the complete button
         * will go to the ScanQRcode activity
         */
        completet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverCompleteTripActivity.this, ScanQRcode.class);
                intent.putExtra("rider",Rider);
                intent.putExtra("order_id",Order_id);
                startActivity(intent);

            }
        });

        newMapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        newMapFragment.getMapAsync(this);


    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = completeMap.addPolyline((PolylineOptions)values[0]);


    }

    /**
     * This method will set the driver current location on the map
     *  @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        completeMap = googleMap;

        LatLng1 = new LatLng(startLat,startLng);
        MarkerOptions1.position(LatLng1);
        MarkerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        MarkerOptions1.title("start address");

        completeMap.addMarker(MarkerOptions1);

        LatLng2 = new LatLng(endLat,endLng);
        MarkerOptions2.position(LatLng2);
        MarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        MarkerOptions2.title("destination address");

        completeMap.addMarker(MarkerOptions2);

        String url = getUrl(MarkerOptions1.getPosition(),MarkerOptions2.getPosition(),"driving");
        new FetchURL(DriverCompleteTripActivity.this).execute(url,"driving");


        completeMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng1,11));
        completeMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng2,11));

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
}
