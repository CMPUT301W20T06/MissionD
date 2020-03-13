package com.example.mar05;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DriverMakeOfferActivity extends AppCompatActivity {
    private ImageButton button_back;
    private Button button_confirm;
    private TextView LocationName;
    private TextView DestinationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_make_offer);

        String Location;
        String Destination;

        Location = getIntent().getExtras().getString("trip_location");
        Destination = getIntent().getExtras().getString("trip_destination");

        LocationName = findViewById(R.id.start_location);
        DestinationName = findViewById(R.id.Destination_text);
        button_back = findViewById(R.id.DriverBack);
        button_confirm = findViewById(R.id.MoneyConfirm);

        LocationName.setText(Location);
        DestinationName.setText(Destination);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IsMoneyOKFragment().show(getSupportFragmentManager(),"Confirm");
            }
        });


    }

}
