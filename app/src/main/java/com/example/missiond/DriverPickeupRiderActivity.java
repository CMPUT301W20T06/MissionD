package com.example.missiond;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
public class DriverPickeupRiderActivity extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback{

    private Button pick_up_button;
    private TextView start_location;
    private TextView destination;
    private TextView rider_name;
    private TextView rider_phone;
    private TextView rider_email;

    private Button phone_call;

    private Button email_rider;

    private SupportMapFragment newMapFragment;

    private LatLng LatLng1;
    private LatLng LatLng2;

    MarkerOptions MarkerOptions1 = new MarkerOptions();
    MarkerOptions MarkerOptions2 = new MarkerOptions();

    private Polyline currentPolyline;
    private GoogleMap PickupMap;

    private DataBaseHelper db;
    private Rider rider;
    private String phone,email;

    String Location;
    String Destination;
    String Rider;
    private String Order_id;

    private float startLat,startLng,endLat,endLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_pickup_rider);

        Location = getIntent().getExtras().getString("location");
        Destination = getIntent().getExtras().getString("destination");
        startLat = getIntent().getExtras().getFloat("startLocationLat");
        startLng = getIntent().getExtras().getFloat("startLocationLng");
        endLat = getIntent().getExtras().getFloat("endLocationLat");
        endLng = getIntent().getExtras().getFloat("endLocationLng");
        Rider = getIntent().getExtras().getString("rider");
        Order_id = getIntent().getExtras().getString("order_id");

        pick_up_button = findViewById(R.id.pickup_button);
        start_location = findViewById(R.id.start_location);
        destination = findViewById(R.id.Destination_text);
        rider_name = findViewById(R.id.rider_name);
        rider_phone = findViewById(R.id.rider_phone);
        phone_call = findViewById(R.id.call);
        email_rider = findViewById(R.id.email);
        rider_email = findViewById(R.id.rider_email);

        start_location.setText(Location);
        destination.setText(Destination);
        rider_name.setText(Rider);

        /**
         * use database helper to get the rider
         * and the phone number, email of the rider
         */
        db = DataBaseHelper.getInstance();
        db.getRider(Rider, new Consumer<Rider>() {
            @Override
            public void accept(Rider tempRider) {
                rider = tempRider;
                phone = rider.getPhoneNumber();
                email = rider.getEmailAddress();
                rider_phone.setText(phone);
                rider_email.setText(email);

            }
        });

        /**
         * when press the pick up button
         * it will go the DriverCompleteTripActivity
         */
        pick_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverPickeupRiderActivity.this, DriverCompleteTripActivity.class);
                intent.putExtra("location",Location);
                intent.putExtra("destination",Destination);
                intent.putExtra("startLocationLat",startLat);
                intent.putExtra("startLocationLng",startLng);
                intent.putExtra("endLocationLat",endLat);
                intent.putExtra("endLocationLng",endLng);
                intent.putExtra("rider",Rider);
                intent.putExtra("order_id",Order_id);
                startActivity(intent);
            }
        });


        phone_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "tel:" + rider_phone.getText();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(s));
                /**
                 * https://stackoverflow.com/questions/40125931/how-to-ask-permission-to-make-phone-call-from-android-from-android-version-marsh
                 * @author
                 *  Fabian Tamp
                 */
                if (ContextCompat.checkSelfPermission(DriverPickeupRiderActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DriverPickeupRiderActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    startActivity(callIntent);
                }

            }
        });


        email_rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * https://stackoverflow.com/questions/28588255/no-application-can-perform-this-action-when-send-email
                 * @author
                 * yubaraj poudel
                 */
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(rider_email.getText())});
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
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
        currentPolyline = PickupMap.addPolyline((PolylineOptions)values[0]);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        PickupMap = googleMap;

        LatLng1 = new LatLng(startLat,startLng);
        MarkerOptions1.position(LatLng1);
        MarkerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        MarkerOptions1.title("start address");

        PickupMap.addMarker(MarkerOptions1);

        LatLng2 = new LatLng(endLat,endLng);
        MarkerOptions2.position(LatLng2);
        MarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        MarkerOptions2.title("destination address");

        PickupMap.addMarker(MarkerOptions2);

        String url = getUrl(MarkerOptions1.getPosition(),MarkerOptions2.getPosition(),"driving");
        new FetchURL(DriverPickeupRiderActivity.this).execute(url,"driving");


        PickupMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng1,11));
        PickupMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng2,11));

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
