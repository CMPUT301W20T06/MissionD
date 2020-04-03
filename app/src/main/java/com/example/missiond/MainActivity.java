package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * User can register or sign in
 * @author
 *  Isaac Zhang, Weiyi Wu
 * @version
 *  April.02 2020
 */

public class MainActivity extends AppCompatActivity {
    private Button button_rider;
    private Button button_driver;
    private TextView button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_register = findViewById(R.id.register);
        button_rider = findViewById(R.id.login_rider);
        button_driver = findViewById(R.id.login_driver);

        button_rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, rider_login.class);
                startActivity(i);
            }
        });

        button_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, driver_login.class);
                startActivity(intent);
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });

    }


}
