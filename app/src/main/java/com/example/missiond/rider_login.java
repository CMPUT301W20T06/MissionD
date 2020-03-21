package com.example.missiond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class rider_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        Button login;
        final EditText user_name,user_email,phone;
        final DataBaseHelper DB = DataBaseHelper.getInstance();
        login = findViewById(R.id.Login);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_user_name,str_user_email,str_phone;
                str_phone = phone.getText().toString();
                str_user_email = user_email.getText().toString();
                str_user_name = user_name.getText().toString();
                if (str_user_name.length()==0) return;
                if(DB.userExist(str_user_name,true)) {
                    Intent i = new Intent(rider_login.this, DriverActivity.class);
                    startActivity(i);
                } else{
                    Toast.makeText(rider_login.this,"Rider is not find in database",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


    }
}
