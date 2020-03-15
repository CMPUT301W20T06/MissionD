package com.example.missiond;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class Register extends AppCompatActivity {


    public Button confirm;
    public Switch user_type;
    private EditText user_name,user_email,phone;
    private String type_confirm = "Rider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        confirm = findViewById(R.id.confirm);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        DataBaseHelper DB = DataBaseHelper.getInstance();

        final String str_user_name,str_user_email,str_phone;
        str_phone = phone.getText().toString();
        str_user_email = user_email.getText().toString();
        str_user_name = user_name.getText().toString();

        user_type = findViewById(R.id.switch1);
        user_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){type_confirm = "Driver";}
                else type_confirm="Rider";
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_name == null || user_email == null || phone==null){
                    Toast.makeText(Register.this,"Please Input in missing blanks",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (type_confirm =="Rider"){
                        Rider user = new Rider(str_user_name,str_phone,str_user_email);
                        DataBaseHelper DB = DataBaseHelper.getInstance();
                        DB.AddRider(user);
                        Toast.makeText(Register.this,"Congratulations! Registered as Rider!",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Driver user = new Driver(str_user_name,str_phone,str_user_email);
                        DataBaseHelper DB = DataBaseHelper.getInstance();
                        DB.AddDriver(user);
                        Toast.makeText(Register.this,"Congratulations! Registered as Driver!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }
}
