package com.example.mar05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverActivity extends AppCompatActivity {
    private Button seeTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity);

        seeTrip = findViewById(R.id.driverSeeTrip);

        seeTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverActivity.this, SearchRequestActivity.class);
                startActivity(intent);
                DriverActivity.this.finish();

            }
        });
    }
}
