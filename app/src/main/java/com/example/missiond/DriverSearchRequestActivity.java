package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


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

        String []location ={"University of Alberta", "University of Alberta", "NAIT", "University of Alberta","Edmonton International Airport","Mayfair South"};
        String []destination={"Southgate Centre","Edmonton International Airport","Southgate Centre","Mayfair South","Corona Station","Churchill Station"};

        tripDataList = new ArrayList<>();

        for (int i=0;i<location.length;i++){
            tripDataList.add((new Trip(location[i], destination[i])));
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

