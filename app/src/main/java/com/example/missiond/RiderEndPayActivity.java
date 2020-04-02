package com.example.missiond;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import android.content.Intent;

import java.util.List;


/**
 * Generate a QR code which driver can use to scan
 * @author
 *  Isaac Zhang, Weiyi Wu
 * @version
 *  Mar.12 2020
 */
public class RiderEndPayActivity extends AppCompatActivity {
    private Button finish;
    private TextView driverName,location1,location2;
    private String pickUp,dest,id,driver_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_end_pay);

        /**
         * orderID = getExtras("orderID");
         * find order by order id
         * get driver name from order
         */

        DataBaseHelper DB = DataBaseHelper.getInstance();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        pickUp = extras.getString("pickUp");
        dest = extras.getString("dest");
        id = extras.getString("orderID");
        driver_name = extras.getString("driver");

        driverName = findViewById(R.id.driverName);
        driverName.setText(driver_name);

        location1 = findViewById(R.id.Location1);
        location2 = findViewById(R.id.Location2);
        location1.setText(pickUp);
        location2.setText(dest);

        finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                //extras.putString("name",driver_name);
                //extras.putString("pickUp",pickUp);
                //extras.putString("dest",dest);
                /**
                 * pass orderID to next activity
                 */
                Intent i = new Intent(RiderEndPayActivity.this, RiderRateActivity.class);
                extras.putString("orderID",id);
                extras.putString("driver",driver_name);
                extras.putString("pickUp",pickUp);
                extras.putString("dest",dest);
                i.putExtras(extras);
                startActivity(i);

                finish();
            }
        });



        GenerateQR();


    }

    /**
     * Generate a QR for pay
     */
    public void GenerateQR() {
//            EditText text = findViewById(R.id.editText);
//            String message = text.getText().toString();
        DataBaseHelper DB = DataBaseHelper.getInstance();
//        Rider rider = DB.getRider("Isaac");

        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override

            public void accept(List<Order> orders) {
                String stringcost = "no current active orders";
                Order order1;
                for (int i=0;i <orders.size();i++) {
                    Order order = orders.get(i);
                    if (order.getId().equals(id)) {
                        order1 = order;
                        stringcost = String.valueOf(order1.getCost());
                    }
                }
                String InputDate = stringcost;
                ImageView mImageView = findViewById(R.id.iv);
                Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(InputDate, 500, 500);
                mImageView.setImageBitmap(mBitmap);
            }
        });


//        submit.setText("AWESOME!");
    }
}
