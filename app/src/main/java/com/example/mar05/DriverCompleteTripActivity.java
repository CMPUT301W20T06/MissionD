package com.example.mar05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DriverCompleteTripActivity extends AppCompatActivity {
    private Button completet_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_complete_trip);

        completet_button = findViewById(R.id.complete_button);

        completet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverCompleteTripActivity.this, ScanQRcode.class);
                startActivity(intent);

            }
        });



    }

}
