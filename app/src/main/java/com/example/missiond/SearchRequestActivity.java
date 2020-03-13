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

public class SearchRequestActivity extends AppCompatActivity {

    private ImageButton button_back;
    private ImageButton button_refresh;

    ListView tripList;

    ArrayAdapter<Trip> tripAdapter;
    ArrayList<Trip> tripDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_request_activity);

        button_back = findViewById(R.id.DriverDestBack);
        button_refresh = findViewById(R.id.refresh);
        tripList = findViewById(R.id.trip_list);

        String []location ={"Univeristy of Alberta", "Univeristy of Alberta", "Nait", "Univeristy of Alberta","Airport","City Center"};
        String []destination={"Southgate","Joillbee","Southgate","Mayfair","Virking Arms","Hendrix"};

        tripDataList = new ArrayList<>();

        for (int i=0;i<location.length;i++){
            tripDataList.add((new Trip(location[i], destination[i])));
        }

        tripAdapter = new TripList(this, tripDataList);
        tripList.setAdapter(tripAdapter);


        tripList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trip tripedit = tripDataList.get(position);

                Intent intent = new Intent(SearchRequestActivity.this, MakeOfferActivity.class);
                intent.putExtra("trip_location",tripedit.getLocationName());
                intent.putExtra("trip_destination",tripedit.getDestination());
                startActivity(intent);
                SearchRequestActivity.this.finish();

            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchRequestActivity.this, MainActivity.class);
                startActivity(intent);
                SearchRequestActivity.this.finish();
            }
        });



    }

}

