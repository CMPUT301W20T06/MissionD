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

/**
 * This class will have the user profile
 * @author
 *  Weiyi Wu, Weiting Chi, Isaac Zhang
 */

public class UserProfileActivity extends AppCompatActivity {
    private Button close,confirm,order;

    private DataBaseHelper db;
    private TextView mName,nameBig;
    private EditText mPhone,mEmail;
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


        nameBig = findViewById(R.id.user_name);
        mName = findViewById(R.id.editName_profile);
        mPhone = findViewById(R.id.editPhone_profile);
        mEmail = findViewById(R.id.editEmail_profile);
        close = findViewById(R.id.profileBack);
        confirm = findViewById(R.id.confirm_profile_edit);
        order = findViewById(R.id.orderHistory);

        nameBig.setText(name);
        mName.setText(name);

        if (type.equals("rider")) {
            Log.d("profile","Get Rider");
            db.getRider(name, new Consumer<Rider>() {
                @Override
                public void accept(Rider tempRider) {
                    rider = tempRider;
                    phone = rider.getPhoneNumber();
                    email = rider.getEmailAddress();
                    mPhone.setText(phone);
                    mEmail.setText(email);
                }
            });
        } else {
            Log.d("profile","Get Driver");
            db.getDriver(name, new Consumer<Driver>() {
                @Override
                public void accept(Driver tempDriver) {
                    driver = tempDriver;
                    phone = driver.getPhoneNumber();
                    email = driver.getEmailAddress();
                    mPhone.setText(phone);
                    mEmail.setText(email);
                }
            });
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPhone = mPhone.getText().toString();
                newEmail = mEmail.getText().toString();

                if (type.equals("rider")){
                    if (rider == null){
                        Toast.makeText(UserProfileActivity.this,"Rider is null",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    rider.setEmailAddress(newEmail);
                    rider.setPhoneNumber(newPhone);
                    db.UpdateRiderData(rider);
                } else {
                    if (driver == null){
                        Toast.makeText(UserProfileActivity.this,"Driver is null",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    driver.setEmailAddress(newEmail);
                    driver.setPhoneNumber(newPhone);
                    db.UpdateDriverData(driver);
                }
                Toast.makeText(UserProfileActivity.this,"Your info is updated",Toast.LENGTH_SHORT).show();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, RiderOrderActivity.class);
                i.putExtra("user_name", name);
                i.putExtra("user_type", type);
                startActivity(i);
            }
        });

    }
}
