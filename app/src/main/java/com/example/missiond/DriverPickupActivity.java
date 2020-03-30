package com.example.missiond;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Let Driver to choose his/her pick up location.
 * @author
 *  Weiting Chi
 * @version
 *  Mar.29 2020
 */
public class DriverPickupActivity extends AppCompatActivity {
    private ImageButton back_button;
    private ImageButton confrim_buton;
    private Button search_button;

    private EditText pickUpLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_pickup);


}
