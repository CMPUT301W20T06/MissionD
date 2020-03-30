package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

public class UserProfileActivity extends AppCompatActivity {
    private Button close,confirm;
    private DataBaseHelper db;
    private TextView nameBig;
    private EditText mName,mPhone,mEmail;
    private String name,phone,email,newName,newPhone,newEmail;
    private String type;
    private Rider rider;
    private Driver driver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent i = getIntent();
        name = i.getStringExtra("user_name");
        type = i.getStringExtra("user_type");
        db = DataBaseHelper.getInstance();

        Log.d("Profile",type);
        if (type == "rider"){
            try {
                db.getRider(name).wait();
                rider = db.getRider(name);
                phone = rider.getPhoneNumber();
                email = rider.getEmailAddress();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Log.d("Profile",phone);
        } else {
            try {
                db.getDriver(name).wait();
                driver = db.getDriver(name);
                phone = driver.getPhoneNumber();
                email = driver.getEmailAddress();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        nameBig = findViewById(R.id.user_name);
        mName = findViewById(R.id.editName_profile);
        mPhone = findViewById(R.id.editPhone_profile);
        mEmail = findViewById(R.id.editEmail_profile);
        close = findViewById(R.id.profileBack);
        confirm = findViewById(R.id.confirm_profile_edit);

        nameBig.setText(name);
        mName.setText(name);
        mPhone.setText(phone);
        mEmail.setText(email);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPhone = mPhone.getText().toString();
                newEmail = mEmail.getText().toString();

                if (type == "rider"){
                    rider.setEmailAddress(newPhone);
                    rider.setPhoneNumber(newEmail);
                    db.UpdateRiderData(rider);
                } else {
                    driver.setEmailAddress(newPhone);
                    driver.setPhoneNumber(newEmail);
                    db.UpdateDriverData(driver);
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
