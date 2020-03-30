package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Ask driver to confirm the money that will be paid on that trip
 * @author
 *  Weiting Chi
 * @version
 *  Mar.26 2020
 */
public class DriverAfterRiderConfrimFragment extends DialogFragment {
    private Button tesing_button;

    public String Location;
    public String Destination;

    private float startLat,startLng,endLat,endLng;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.after_rider_confirm_fragment, container, false);

        Bundle bundle = getArguments();
        if (bundle!=null){
            Location = bundle.getString("trip_location");
            Destination = bundle.getString("trip_destination");
            startLat = bundle.getFloat("startLocationLat");
            startLng = bundle.getFloat("startLocationLng");
            endLat =bundle.getFloat("endLocationLat");
            endLng = bundle.getFloat("endLocationLng");
        }

        tesing_button = v.findViewById(R.id.just_for_testing);

        tesing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                Intent intent = new Intent(getActivity(), DriverPickeupRiderActivity.class);
                intent.putExtra("location",Location);
                intent.putExtra("destination",Destination);
                intent.putExtra("startLocationLat",startLat);
                intent.putExtra("startLocationLng",startLng);
                intent.putExtra("endLocationLat",endLat);
                intent.putExtra("endLocationLng",endLng);
                startActivity(intent);
            }
        });

        return v;
    }
}
