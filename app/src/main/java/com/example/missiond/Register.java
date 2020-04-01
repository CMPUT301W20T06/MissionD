package com.example.missiond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        final DataBaseHelper DB = DataBaseHelper.getInstance();

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
                final String str_user_name,str_user_email,str_phone;
                str_phone = phone.getText().toString();
                str_user_email = user_email.getText().toString();
                str_user_name = user_name.getText().toString();

                if (str_user_name.length() == 0 || str_user_email.length() == 0 || str_phone.length() == 0){
                    Toast.makeText(Register.this,"Please complete missing blanks",Toast.LENGTH_SHORT).show();
                    return;
                }

                // check if email input is valid
                if (!validInput(str_user_email)){
                    Toast.makeText(Register.this, "Wrong email format!", Toast.LENGTH_SHORT).show();
                }

                else {
                    final boolean isRider = type_confirm == "Rider";

                    DB.userExist(str_user_name, isRider, new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean isExist) {
                            if (!isExist){
                                if (isRider){
                                    Rider user = new Rider(str_user_name,str_phone,str_user_email);
                                    DataBaseHelper DB = DataBaseHelper.getInstance();
                                    DB.AddRider(user);
                                    Toast.makeText(Register.this,"Congratulations! Registered as Rider!",Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Driver user = new Driver(str_user_name,str_phone,str_user_email);
                                    DataBaseHelper DB = DataBaseHelper.getInstance();
                                    DB.AddDriver(user);
                                    Toast.makeText(Register.this,"Congratulations! Registered as Driver!",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            } else {
                                Toast.makeText(Register.this,"User is already exist！",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean validInput(String string){
        String datePattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
