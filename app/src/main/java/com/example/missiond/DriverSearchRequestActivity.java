package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Display a list view of rider's requests to Driver
 * Driver click the list that he/she wants to start.
 * @author
 *  Weiting Chi
 * @version
 *  Mar.12 2020
 */

public class DriverSearchRequestActivity extends AppCompatActivity {

    private ImageButton button_back;
    private ImageButton button_refresh;
    final DataBaseHelper DB = DataBaseHelper.getInstance();

    ListView tripList;

    ArrayAdapter<Trip> tripAdapter;
    ArrayList<Trip> tripDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_search_request_activity);

        button_back = findViewById(R.id.DriverDestBack);
        button_refresh = findViewById(R.id.refresh);
        tripList = findViewById(R.id.trip_list);


        List<Order> orders = DB.getAllOrders();
        List<Order> current_orders = new ArrayList<>();

        Toast.makeText(this,String.valueOf(orders.size()),Toast.LENGTH_SHORT).show();

        for (int i = 0; i< orders.size(); i++) {
            Order order = orders.get(i);
            if (order != null) {
                if (order.getOrderStatus() != null) {
                    int status = order.getOrderStatus();
                    if (status == 1) {
                        current_orders.add(order);
                    }
                }
            }
        }


        tripDataList = new ArrayList<>();

        for (int i=0;i<current_orders.size();i++){
            Order order = current_orders.get(i);
            tripDataList.add((new Trip(order.getStartLocation(), order.getEndLocation())));
        }

        tripAdapter = new TripList(this, tripDataList);
        tripList.setAdapter(tripAdapter);


        /**
         * When click on the list
         * it will get the trip location and trip destination
         * it will go to the DriverMakeOfferActivity
         */
        tripList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trip tripedit = tripDataList.get(position);

                // 这里要order object 的话直接 Order order = current_orders.get(position);

                Intent intent = new Intent(DriverSearchRequestActivity.this, DriverMakeOfferActivity.class);
                intent.putExtra("trip_location",tripedit.getLocationName());
                intent.putExtra("trip_destination",tripedit.getDestination());
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

