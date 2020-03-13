package com.example.mar05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DriverActivity extends AppCompatActivity {
    private Button seeTrip_button;
    private Button profile_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        seeTrip_button = findViewById(R.id.driverSeeTrip);
        profile_button = findViewById(R.id.profile_driver);

        seeTrip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverActivity.this, DriverSearchRequestActivity.class);
                startActivity(intent);

            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
