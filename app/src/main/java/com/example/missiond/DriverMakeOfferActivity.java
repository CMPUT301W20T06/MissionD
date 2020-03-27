package com.example.missiond;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Display a map with driver current location
 * Driver can start to see trip requests by pressing see trip button
 * @author
 *  Weiting Chi, XinRong Zhou
 * @version
 *  Mar.12 2020
 */
public class DriverMakeOfferActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageButton button_back;
    private Button button_confirm;
    private TextView LocationName;
    private TextView DestinationName;

    private GoogleMap makeOfferMap;
    private LatLng LatLng1;
    private LatLng LatLng2;


    private String Location;
    private String Destination;

    private Address userAddress1;
    private Address userAddress2;

    private SupportMapFragment newMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_make_offer);


        Location = getIntent().getExtras().getString("trip_location");
        Destination = getIntent().getExtras().getString("trip_destination");

        LocationName = findViewById(R.id.start_location);
        DestinationName = findViewById(R.id.Destination_text);
        button_back = findViewById(R.id.DriverBack);
        button_confirm = findViewById(R.id.MoneyConfirm);

        LocationName.setText(Location);
        DestinationName.setText(Destination);

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

        /**
         * when click on the confirm button
         * it will let driver to confirm if the money is ok or not
         */
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsMoneyOKFragment fragment = new IsMoneyOKFragment();
                Bundle bundle = new Bundle();
                bundle.putString("trip_location",Location);
                bundle.putString("trip_destination",Destination);
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(),"Confirm");
            }
        });

        newMapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        newMapFragment.getMapAsync(this);




    }

    /**
     * This method will set the driver current location on the map
     *  @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        makeOfferMap = googleMap;

        List<Address> addressList1 = null;
        List<Address> addressList2 = null;



        Geocoder geocoder = new Geocoder(this);
//
        try {
            addressList1 = geocoder.getFromLocationName(Location, 1);

            if (addressList1 != null) {
//                            for(int i=0;i<addressList.size();i++){
                userAddress1 = addressList1.get(0);
                LatLng1 = new LatLng(userAddress1.getLatitude(), userAddress1.getLongitude());

                makeOfferMap.addMarker(new MarkerOptions().position(LatLng1).title("start address"));

                makeOfferMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng1,11));
//}
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        try {
            addressList2 = geocoder.getFromLocationName(Destination, 1);

            if (addressList2 != null) {
//                            for(int i=0;i<addressList.size();i++){
                userAddress2 = addressList2.get(0);
                LatLng2 = new LatLng(userAddress2.getLatitude(), userAddress2.getLongitude());

                makeOfferMap.addMarker(new MarkerOptions().position(LatLng2).title("start address"));
//}
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
