package com.example.mar05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {
//    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
//    private initFragment initF;
//    private mapFragment mapF;
//    private String pickupLoc,DestLoc,tempLoc;
    private Button button_rider;
    private Button button_driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_rider = findViewById(R.id.login_rider);
        button_driver = findViewById(R.id.login_driver);

        button_rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RiderActivity.class);
                startActivity(i);
            }
        });

        button_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DriverActivity.class);
                startActivity(intent);
            }
        });



//        initF = new initFragment();
//        mapF = new mapFragment();
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.initFrag, initF)
//                .commit();
    }


//    @Override
//    public void onRiderStartTripClick() {
//        Intent intent = new Intent(MainActivity.this, MapActivity.class);
//        startActivity(intent);
//
//    }

//
//    public String getPickupLoc(){
//        return pickupLoc;
//    }
//
//    public String getDestLoc(){
//        return DestLoc;
//    }

}
