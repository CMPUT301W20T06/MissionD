package com.example.missiond;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Let Driver to choose his/her pick up location.
 * @author
 *  Weiting Chi and Xinrong Zhou
 * @version
 *  Mar.29 2020
 */
public class DriverPickupActivity extends AppCompatActivity {
    private ImageButton back_button;
    private ImageButton confrim_buton;
    private Button search_button;

    private EditText pickUpLocation;

    //我先给周总弄出来了 貌似你需要用到
    //但具体怎么拿到string和address我不太知道
    //辛苦了
    String pickupName;
    Address pickupAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_pickup);

        back_button = findViewById(R.id.driverLocBack);
        //这个是后面那个绿色对勾按钮 不知道周总要不要用
        confrim_buton = findViewById(R.id.search_address1);
        search_button = findViewById(R.id.searchOrder);

        //这个是那个输入地址但那个输入框 也不知道周总要不要用到
        pickUpLocation = findViewById(R.id.pickup_Location);

        /**
         * Go back to the previous page
         */
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * Go to the new request list activity
         */
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverPickupActivity.this, DriverSearchRequestActivity.class);
                // 要传的东西
                //intent.putExtra("pickup_location",...);
                startActivity(intent);

            }
        });





    }


}
