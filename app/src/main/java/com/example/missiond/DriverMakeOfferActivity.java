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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

/**
 * Display a map with driver current location
 * Driver can start to see trip requests by pressing see trip button
 * @author
 *  Weiting Chi, XinRong Zhou
 * @version
 *  Mar.30 2020
 */
public class DriverMakeOfferActivity extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback {
    private ImageButton button_back;
    private Button button_confirm;
    private TextView LocationName;
    private TextView DestinationName;
    private TextView FairMoney;

    private GoogleMap makeOfferMap;
    private LatLng LatLng1;
    private LatLng LatLng2;

    MarkerOptions MarkerOptions1 = new MarkerOptions();
    MarkerOptions MarkerOptions2 = new MarkerOptions();

    private String Location;
    private String Destination;
    private String Rider;

    private Address userAddress1;
    private Address userAddress2;

    private SupportMapFragment newMapFragment;

    private float startLat;
    private float startLng;
    private float endLat;
    private float endLng;
    private float Cost;

    private Polyline currentPolyline;

    private String Order_id;
    private String driver_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_make_offer);


        Location = getIntent().getExtras().getString("trip_location");
        Destination = getIntent().getExtras().getString("trip_destination");
        startLat = getIntent().getExtras().getFloat("startLocationLat");
        startLng = getIntent().getExtras().getFloat("startLocationLng");
        endLat = getIntent().getExtras().getFloat("endLocationLat");
        endLng = getIntent().getExtras().getFloat("endLocationLng");
        Rider = getIntent().getExtras().getString("rider");
        Cost = getIntent().getExtras().getFloat("cost");
        Order_id= getIntent().getExtras().getString("order_id");
        driver_name= getIntent().getExtras().getString("user_name");

        LocationName = findViewById(R.id.start_location);
        DestinationName = findViewById(R.id.Destination_text);
        button_back = findViewById(R.id.DriverBack);
        button_confirm = findViewById(R.id.MoneyConfirm);
        FairMoney = findViewById(R.id.DriverEstimateFareConfirm);

        LocationName.setText(Location);
        DestinationName.setText(Destination);
        FairMoney.setText(String.valueOf(Cost));

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
                bundle.putFloat("startLocationLat",startLat);
                bundle.putFloat("startLocationLng",startLng);
                bundle.putFloat("endLocationLat",endLat);
                bundle.putFloat("endLocationLng",endLng);
                bundle.putString("rider",Rider);
                bundle.putFloat("cost",Cost);
                bundle.putString("order_id",Order_id);
                bundle.putString("user_name",driver_name);

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

        LatLng1 = new LatLng(startLat,startLng);
        MarkerOptions1.position(LatLng1);
        MarkerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        MarkerOptions1.title("start address");

        makeOfferMap.addMarker(MarkerOptions1);

        LatLng2 = new LatLng(endLat,endLng);
        MarkerOptions2.position(LatLng2);
        MarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        MarkerOptions2.title("destination address");

        makeOfferMap.addMarker(MarkerOptions2);

        String url = getUrl(MarkerOptions1.getPosition(),MarkerOptions2.getPosition(),"driving");
        new FetchURL(DriverMakeOfferActivity.this).execute(url,"driving");


        makeOfferMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng1,11));
        makeOfferMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng2,11));


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
        currentPolyline = makeOfferMap.addPolyline((PolylineOptions)values[0]);


    }
}
