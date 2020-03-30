package com.example.missiond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TripList extends ArrayAdapter<Trip> {
    private ArrayList<Trip> trips;
    private Context context;

    public TripList(Context context, ArrayList<Trip> trips){
        super(context, 0 , trips);
        this.context= context;
        this.trips = trips;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.trip_content, parent, false);
        }
        Trip trip = trips.get(position);

        TextView locationName = view.findViewById(R.id.location_text);
        TextView destinationName = view.findViewById(R.id.destination_text);

        //之后把distance show出来
        TextView distance = view.findViewById(R.id.distance_text);

        locationName.setText(trip.getLocationName());
        destinationName.setText(trip.getDestination());
        distance.setText(String.valueOf(trip.getDistance()));

        return view;
    }

}
