package com.example.missiond;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {
    private Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        DataBaseHelper DB = DataBaseHelper.getInstance();
        Rider rider = DB.getRider("Isaac");
        String rider_name = "Isaac";
        String rider_phone = rider.getPhoneNumber();
        String rider_email = rider.getEmailAddress();
        TextView phone = findViewById(R.id.editPhone_profile);
        TextView email = findViewById(R.id.editEmail_profile);
        TextView name = findViewById(R.id.editName_profile);
        name.setText(rider_name);
        phone.setText(rider_phone);
        email.setText(rider_email);

        close = findViewById(R.id.profileBack);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
