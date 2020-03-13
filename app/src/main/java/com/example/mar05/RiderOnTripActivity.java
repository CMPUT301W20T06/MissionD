package com.example.mar05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RiderOnTripActivity extends AppCompatActivity implements RiderConfirmCancelDialog.RiderConfirmCancelDialogListener, RiderConfirmPayDialog.RiderConfirmPayDialogListener {
    private ImageButton back;
    private Button confirm;
    private TextView driverName,pickUpText,destText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_on_trip);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String pickUp = extras.getString("pickUp");
        final String dest = extras.getString("dest");

        pickUpText = findViewById(R.id.Location1);
        pickUpText.setText(pickUp);

        destText = findViewById(R.id.Location2);
        destText.setText(dest);

        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderConfirmCancelDialog riderConfirmCancelDialog = new RiderConfirmCancelDialog();
                riderConfirmCancelDialog.show(getSupportFragmentManager(),"cancelConfirmDialog");
            }
        });

        driverName = findViewById(R.id.driverName);
        driverName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverInfoDialog driverInfoDialog = new DriverInfoDialog();
                driverInfoDialog.show(getSupportFragmentManager(),"addMoneyFragment");
            }
        });

        confirm = findViewById(R.id.confirmArrival_rider);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderConfirmPayDialog riderConfirmPayDialog = new RiderConfirmPayDialog();
                riderConfirmPayDialog.show(getSupportFragmentManager(),"confirmAndPay");
            }
        });

    }

    @Override
    public void onCancelConfirmClick() {
        finish();
    }

    @Override
    public void onArriveConfirmClick() {
        Intent i = new Intent(RiderOnTripActivity.this, RiderEndPayActivity.class);
        startActivity(i);
        finish();
    }
}
