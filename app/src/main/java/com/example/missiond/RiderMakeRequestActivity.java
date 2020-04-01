package com.example.missiond;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import androidx.core.util.Consumer;

import java.util.List;

/**
 * Once there is a drive to accept the request, the information about that drive will be displayed
 * Rider can choose to ride with this diver or wait for another driver
 * @author
 *  Xinrong Zhou, Weiyi Wu
 * @version
 *  Mar.12 2020
 */

public class RiderMakeRequestActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, RiderConfirmDriverDialog.RiderConfirmDriverListener, RiderConfirmCancelDialog.RiderConfirmCancelDialogListener,
TaskLoadedCallback{

    private GoogleMap newMap;
    LatLng LatLng1,LatLng2;
    MarkerOptions MarkerOptions1 = new MarkerOptions();
    MarkerOptions MarkerOptions2 = new MarkerOptions();

    String pickUp,dest;
    Float fare,address1Lat,address1Lng,address2Lat,address2Lng;
    final DataBaseHelper DB = DataBaseHelper.getInstance();
    Order order1;
    private Boolean driverAccept = false;
    private Handler handler = new Handler();


    private SupportMapFragment mapFragment;
    private RiderConfirmDriverDialog confirmDriverDialog;
    private ImageButton close;
    private TextView pickupText,destText,next;
    private Polyline currentPolyline;
    private String rider_name,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_make_request);


        Intent i = getIntent();
        rider_name = i.getStringExtra("rider_name");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        pickUp = extras.getString("RiderPickUp");
        dest = extras.getString("RiderDest");
        fare = extras.getFloat("EstimateFare");
        id = extras.getString("orderID");
        address1Lat = extras.getFloat("startAddressLatitude");
        address1Lng = extras.getFloat("startAddressLongitude");
        address2Lat = extras.getFloat("destinationAddressLatitude");
        address2Lng = extras.getFloat("destinationAddressLongitude");


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.newMap);

        mapFragment.getMapAsync(this);

        pickupText = findViewById(R.id.Location1_makeRequest);
        pickupText.setText(pickUp);

        destText = findViewById(R.id.Location2_makeRequest);
        destText.setText(dest);

        close = findViewById(R.id.riderBack_makeRequest);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderConfirmCancelDialog riderConfirmCancelDialog = new RiderConfirmCancelDialog();
                riderConfirmCancelDialog.show(getSupportFragmentManager(),"cancelConfirmDialog");
            }
        });

        startRepeating();
//        DB.getOrderById(id, new Consumer<Order>() {
//            @Override
//            public void accept(Order order) {
//                order1 = order;
//                onLoaded();
//            }
//        });

        /**
        testing
         **/
        next = findViewById(R.id.next_makeRequest);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order1.setOrderStatus(2);
                order1.setDriver("Yifei");
                DB.updateOrder(order1);
                Toast.makeText(RiderMakeRequestActivity.this,"test",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void startRepeating(){
        runnable.run();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (driverAccept){
                handler.removeCallbacks(runnable);
                Bundle bundle = new Bundle();
                bundle.putString("orderID", id);
                RiderConfirmDriverDialog confirmDriverDialog = new RiderConfirmDriverDialog();
                confirmDriverDialog.setArguments(bundle);
                confirmDriverDialog.show(getSupportFragmentManager(), "confirmDriverFragment");
            }
            else{
                getOrder();
                handler.postDelayed(this, 2000);
            }
        }
    };

    private void getOrder(){
        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                for (int i=0;i <orders.size();i++) {
                    Order order = orders.get(i);
                    if (order.getId().equals(id)) {
                        order1 = order;
                    }
                }
                onLoaded();
            }
        });
    }

    public void onLoaded(){
        if (order1.getOrderStatus()==2) {
            driverAccept=true;
            Toast.makeText(RiderMakeRequestActivity.this,"status changed 2",Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(RiderMakeRequestActivity.this,order1.getOrderStatus().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
   public void onMapReady(GoogleMap googleMap) {
        newMap = googleMap;
//
        LatLng1 = new LatLng(address1Lat,address1Lng);
        MarkerOptions1.position(LatLng1);
        MarkerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        MarkerOptions1.title("start address");

        newMap.addMarker(MarkerOptions1);

        LatLng2 = new LatLng(address2Lat,address2Lng);
        MarkerOptions2.position(LatLng2);
        MarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        MarkerOptions2.title("destination address");

        newMap.addMarker(MarkerOptions2);

        String url = getUrl(MarkerOptions1.getPosition(),MarkerOptions2.getPosition(),"driving");
        new FetchURL(RiderMakeRequestActivity.this).execute(url,"driving");


        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng1,11));
        newMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng2,11));
    }
//
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    /**
     * If rider confirm driver, rider waits for driver to pick up
     */
    @Override
    public void onConfirmClick() {
        /**
         * get order id
         * find order by order id
         * order.setOrderStatus(3)  //driver and rider accept(have not picked up yet)
         * pass order id to next activity
         */
        Bundle extras = new Bundle();
        extras.putString("pickUp",pickUp);
        extras.putString("dest",dest);

        extras.putFloat("startAddressLatitude", (float) address1Lat);
        extras.putFloat("startAddressLongitude", (float) address1Lng);
        extras.putFloat("destinationAddressLatitude", (float) address2Lat);
        extras.putFloat("destinationAddressLongitude", (float) address2Lng);
        Intent i = new Intent(RiderMakeRequestActivity.this, RiderWaitForPickUp.class);

        i.putExtras(extras);

        startActivity(i);

        finish();
    }

    /**
     * If rider confirm cancel, go back to the previous activity
     */
    @Override
    public void onCancelConfirmClick() {
        /**
         * get order id
         * find order by order id
         * order.setOrderStatus(0)  //cancel
         */
        finish();
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
        currentPolyline = newMap.addPolyline((PolylineOptions)values[0]);


    }
}


