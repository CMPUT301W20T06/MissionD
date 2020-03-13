package com.example.missiond;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 * Rider can search locations by inputting locations or moving pin as an option
 * Estimate fare is calculated and rider can add extra fee
 * @author
 *  Xinrong Zhou, Weiyi Wu
 * @version
 *  Mar.13 2020
 *  @Reference
 *      //https://github.com/google-developer-training/android-advanced
 */
public class MapActivity extends FragmentActivity implements
        OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, RiderAddMoneyFragment.OnAddMoneyFragmentListener,
        GoogleMap.OnMarkerDragListener
{

    private static final String TAG = MapActivity.class.getSimpleName();

    public GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    private SupportMapFragment mapFragment;

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    String startAddress;
    String destinationAddress;

    Address userAddress1;
    Address userAddress2;

    LatLng LatLng1;
    LatLng LatLng2;

    private float money;

    private Button request;
    private ImageButton back;
    private Button addMoney;
    private RiderAddMoneyFragment addMoneyFrag;
    private float addAmount;
    private TextView addAmountShow;


    /**
     *
     * @param savedInstanceState
     * @Reference
     * /https://github.com/SimCoderYoutube/UberClone
     */
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.



        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        /**
         * check permissions
         */
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

        }else {

            mapFragment.getMapAsync(this);
        }


        request = findViewById(R.id.requestRide_request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMoney(v);
            }
        });

        back = findViewById(R.id.riderLocBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
            }
        });


    }

    /**
     * this will go back to the previous page
     */
    public void backButton(){
        finish();
    }

    /**
     * users input location and google map will find that address
     * @param v
     * @Reference
     *  //https://github.com/mitchtabian/Google-Maps-Google-Places/tree/e8ad8f165c7df3f25b6a9128c70f4c0e3ed84f94
     * @Reference
     *  //https://www.youtube.com/watch?v=NxQY0-QRM1c
     */
    public void onClick(View v){

        switch (v.getId())
        {
            case R.id.search_address1:
                EditText addressField1 =  findViewById(R.id.location_search1);
                startAddress = addressField1.getText().toString();

                List<Address> addressList1 = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();


                if(!TextUtils.isEmpty(startAddress)){
                    Geocoder geocoder = new Geocoder(this);
                    try
                    {
                        addressList1 = geocoder.getFromLocationName(startAddress,1);

                        if(addressList1 != null)

                        {
                            userAddress1 = addressList1.get(0);
                            LatLng1 = new LatLng(userAddress1.getLatitude(),userAddress1.getLongitude());


                            userMarkerOptions.position(LatLng1);
                            userMarkerOptions.title(startAddress);
                            userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                            userMarkerOptions.draggable(true);

                            mMap.addMarker(userMarkerOptions);

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng1));

                            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                        }
                        else{
                            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }


                }else {
                    Toast.makeText(this,"please write any location name",Toast.LENGTH_SHORT).show();
                }
                break;

//

            case R.id.search_address2:
                EditText addressField2 =  findViewById(R.id.location_search2);
                destinationAddress = addressField2.getText().toString();

                List<Address> addressList2 = null;
                MarkerOptions userMarkerOptions2 = new MarkerOptions();


                if(!TextUtils.isEmpty(destinationAddress)){
                    Geocoder geocoder = new Geocoder(this);
                    try
                    {
                        addressList2 = geocoder.getFromLocationName(destinationAddress,1);

                        if(addressList2 != null)

                        {

                            userAddress2 = addressList2.get(0);
                            LatLng2 = new LatLng(userAddress2.getLatitude(),userAddress2.getLongitude());


                            userMarkerOptions2.position(LatLng2);
                            userMarkerOptions2.title(destinationAddress);
                            userMarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                            userMarkerOptions2.draggable(true);

                            mMap.addMarker(userMarkerOptions2);

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng2));

                            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                        }
                        else{
                            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }


                }else {
                    Toast.makeText(this,"please write any location name",Toast.LENGTH_SHORT).show();
                }

//
        }

    }

    /**
     * Calculate the estimate fare and show a fragment ask rider to confirm the request
     * @param v
     */
    public void getMoney(View v){
        if((userAddress1==null)||(userAddress2==null)){
            Toast.makeText(this,"please input location",Toast.LENGTH_SHORT).show();
        }
        else {

            Location loc1 = new Location("");
            Location loc2 = new Location("");
            loc1.setLatitude(userAddress1.getLatitude());
            loc1.setLongitude(userAddress1.getLongitude());
            loc2.setLatitude(userAddress2.getLatitude());
            loc2.setLongitude(userAddress2.getLongitude());

            float distance = loc1.distanceTo(loc2);
            money = (float) (0.004 * distance);
//        showMoney.setText(String.valueOf(money));
            Toast.makeText(this, String.valueOf(money), Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("moneyAmount", String.valueOf(money));
            addMoneyFrag = new RiderAddMoneyFragment();
            addMoneyFrag.setArguments(bundle);
            addMoneyFrag.show(getSupportFragmentManager(), "addMoneyFragment");
        }

    }

    /**
     * Ask if rider would like to add an extra fee and calculate the total amount
     * After rider confirms, start the request and wait for driver to accepts
     * @param amount
     *  This is the estimate fare
     */

    @Override
    public void onAddSaveClick(int amount){
        addAmount = ((float)amount) + money;  // Should be saved to database
        //save startAddress & destinationAddress to database
        Bundle extras = new Bundle();
        extras.putString("RiderPickUp",startAddress);
        extras.putString("RiderDest",destinationAddress);
        extras.putFloat("EstimateFare",money);

        extras.putFloat("startAddressLatitude", (float) userAddress1.getLatitude());
        extras.putFloat("startAddressLongitude", (float) userAddress1.getLongitude());
        extras.putFloat("destinationAddressLatitude", (float) userAddress2.getLatitude());
        extras.putFloat("destinationAddressLongitude", (float) userAddress2.getLongitude());
        Intent i = new Intent(MapActivity.this, RiderMakeRequestActivity.class);

        i.putExtras(extras);

        Location loc1 = new Location("");
        Location loc2 = new Location("");
        loc1.setLatitude(userAddress1.getLatitude());
        loc1.setLongitude(userAddress1.getLongitude());
        loc2.setLatitude(userAddress2.getLatitude());
        loc2.setLongitude(userAddress2.getLongitude());
        float distance = loc1.distanceTo(loc2);
        DataBaseHelper DB = DataBaseHelper.getInstance();
        Rider rider = DB.getRider("Isaac");
        Driver driver = DB.getDriver("Yifei");
        Order order = new Order(userAddress1,userAddress2,distance,addAmount,1,rider,driver);
        DB.addOrder(order,"Isaac");

        startActivity(i);

        finish();
    }

    /**
     * Triggered when the map is ready to be used.
     *
     * @param googleMap The GoogleMap object representing the Google Map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //if (mLocationPermissionsGranted) {
        getDeviceLocation();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerDragListener(this);

    }
    //}

    /**
     * Ask permission and get device current location
     * @Reference
     *  //https://github.com/mitchtabian/Google-Maps-Google-Places/tree/e8ad8f165c7df3f25b6a9128c70f4c0e3ed84f94
     */

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 11));

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    protected synchronized void buildGoogleApiClient(){

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }


    /**
     * monitor location
     * @param location
     * @Reference
     *  //https://www.youtube.com/watch?v=4kk-dYWVNsc
     */

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        LatLng LatLng = new LatLng(location.getLatitude(),location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(LatLng);
        markerOptions.title("user Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


    }


    /**
     * monitor location
     * @param bundle
     * @Reference
     *  //https://www.youtube.com/watch?v=4kk-dYWVNsc
     */

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        //mLocationRequest.setInterval(1000);
        //mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);

    }

    /**
     * monitor location
     * @param i
     * @Reference
     *  //https://www.youtube.com/watch?v=4kk-dYWVNsc
     */
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    /**
     * monitor location
     * @param connectionResult
     * @Reference
     *  //https://www.youtube.com/watch?v=4kk-dYWVNsc
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = ");
    }


    /**
     * check the result of permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @Reference
     *  //https://github.com/SimCoderYoutube/UberClone
     */
    final int LOCATION_REQUEST_CODE = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mapFragment.getMapAsync(this);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please provide the permission",Toast.LENGTH_LONG).show();
                }
                break;

            }
        }

    }

    /**
     * Drag listener
     * @param marker
     * //https://github.com/googlemaps/android-samples
     */
    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    /**
     * Drag listener
     * @param marker
     * //https://github.com/googlemaps/android-samples
     */

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    /**
     * Drag listener
     * @param marker
     * //https://github.com/googlemaps/android-samples
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(this, String.valueOf(marker.getPosition()), Toast.LENGTH_LONG).show();


    }
}

