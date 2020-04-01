package com.example.missiond;

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
public class DriverWaitRiderConfrimFragment extends DialogFragment {

    /////////////////////////////////////
    private Button tesing_button;
    private Button testing_cancel;
    //////////////////////////////////////

    public String Location;
    public String Destination;
    private String Rider;
    private float startLat,startLng,endLat,endLng;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wait_rider_confirm_fragment, container, false);

        Bundle bundle = getArguments();
        if (bundle!=null){
            Location = bundle.getString("trip_location");
            Destination = bundle.getString("trip_destination");
            startLat = bundle.getFloat("startLocationLat");
            startLng = bundle.getFloat("startLocationLng");
            endLat =bundle.getFloat("endLocationLat");
            endLng = bundle.getFloat("endLocationLng");
            Rider = bundle.getString("rider");
        }

        //////////////////////////////////////////////////////////////////
        tesing_button = v.findViewById(R.id.just_for_testing);
        testing_cancel = v.findViewById(R.id.cancel_for_testing);

        tesing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("trip_location",Location);
                bundle.putString("trip_destination",Destination);
                bundle.putFloat("startLocationLat",startLat);
                bundle.putFloat("startLocationLng",startLng);
                bundle.putFloat("endLocationLat",endLat);
                bundle.putFloat("endLocationLng",endLng);
                bundle.putString("rider",Rider);

                DriverAfterRiderConfrimFragment fragment = new DriverAfterRiderConfrimFragment();
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(),"test1");
            }
        });

        testing_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                new DriverAfterRiderCancelFragment().show(getFragmentManager(),"test2");
            }
        });

        return v;
    }
}
