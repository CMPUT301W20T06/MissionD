package com.example.missiond;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Display a list view of rider's requests to Driver
 * Driver click the list that he/she wants to start.
 * @author
 *  Weiting Chi, Yifei Ma
 * @version
 *  Mar.29 2020
 */

public class DriverSearchRequestActivity extends AppCompatActivity {

    private ImageButton button_back;

    private TextView pickup_location;
    final DataBaseHelper DB = DataBaseHelper.getInstance();

    ListView tripList;

    ArrayAdapter<Trip> tripAdapter;
    ArrayList<Trip> tripDataList;

    Float pickupLat;
    Float pickupLng;


    String pickup_Name;
    private String driver_name;

    Location loc1 = new Location("");
    Location loc2 = new Location("");

    List<Order> current_orders = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_search_request_activity);


        /**
         * use the database helper to get the current order
         */
        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                current_orders.clear();
                for (int i = 0; i< orders.size(); i++) {
                    Order order = orders.get(i);
                    int status = order.getOrderStatus();
                    if (status == 1) {
                        current_orders.add(order);
                    }
                }
                onLoaded();
            }
        });
    }

    private void onLoaded(){
        button_back = findViewById(R.id.DriverDestBack);
        tripList = findViewById(R.id.trip_list);
        pickup_location = findViewById(R.id.pickupLocation);

        pickup_Name = getIntent().getExtras().getString("pickup_location");
        pickup_location.setText(pickup_Name);
        pickupLat = getIntent().getExtras().getFloat("pickupLat");
        pickupLng = getIntent().getExtras().getFloat("pickupLng");
        driver_name= getIntent().getExtras().getString("user_name");
        loc1.setLatitude(pickupLat);
        loc1.setLongitude(pickupLng);


        tripDataList = new ArrayList<>();

        for (int i=0;i<current_orders.size();i++){
            Order order = current_orders.get(i);
            loc2.setLatitude((float)order.getStartLoc().getLatitude());
            loc2.setLongitude((float)order.getStartLoc().getLongitude());
            Integer distance = (int)(loc1.distanceTo(loc2)/1000);
            //Toast.makeText(this,String.valueOf((float)order.getStartLoc().getLatitude()),Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,String.valueOf((float)order.getStartLoc().getLongitude()),Toast.LENGTH_SHORT).show();
            tripDataList.add((new Trip(order.getStartLocation(), order.getEndLocation(),distance)));
        }

        tripAdapter = new TripList(this, tripDataList);
        tripList.setAdapter(tripAdapter);


        /**
         * When click on the list
         * it will get the trip location and trip destination
         * it will go to the DriverMakeOfferActivity
         */
        final List<Order> newList = current_orders;

        tripList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trip tripedit = tripDataList.get(position);

                Order order = newList.get(position);


                Intent intent = new Intent(DriverSearchRequestActivity.this, DriverMakeOfferActivity.class);
                intent.putExtra("trip_location",tripedit.getLocationName());
                intent.putExtra("trip_destination",tripedit.getDestination());
                intent.putExtra("startLocationLat",(float)order.getStartLoc().getLatitude());
                intent.putExtra("startLocationLng",(float)order.getStartLoc().getLongitude());
                intent.putExtra("endLocationLat",(float)order.getEndLoc().getLatitude());
                intent.putExtra("endLocationLng",(float)order.getEndLoc().getLongitude());
                intent.putExtra("rider",order.getRider());
                intent.putExtra("cost",order.getCost());
                intent.putExtra("order_id", order.getId());
                intent.putExtra("user_name",driver_name);

                startActivity(intent);
                //DriverSearchRequestActivity.this.finish();

            }
        });

        /**
         * when click on the back button
         * it will get to the last activity
         */

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}

