package com.example.missiond;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {
    private Button close;
    private DataBaseHelper db;
    private TextView nameBig;
    private EditText mName,mPhone,mEmail;
    private String name,phone,email;
    private Driver currentDriver;
    private Rider currentRider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        db = DataBaseHelper.getInstance();
        nameBig = findViewById(R.id.textName_profile);
        mName = findViewById(R.id.editName_profile);
        mPhone = findViewById(R.id.editPhone_profile);
        mEmail = findViewById(R.id.editEmail_profile);
        close = findViewById(R.id.profileBack);
        currentDriver = db.getDriver("Yifei");
        name = currentDriver.getUserName();
        phone = currentDriver.getPhoneNumber();
        email = currentDriver.getEmailAddress();
        nameBig.setText(name);
        mName.setText(name);
        mPhone.setText(phone);
        mEmail.setText(email);



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
