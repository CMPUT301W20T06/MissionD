package com.example.missiond;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

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
    private DataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = DataBaseHelper.getInstance();
        Driver driver = DB.getDriver("Yifei");
        String driver_name = driver.getUserName();
        TextView name = findViewById(R.id.driverName);
        name.setText(driver_name);

        setContentView(R.layout.activity_rider_end_pay);
        finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        List<Order> orders = DB.GetUserOrders("Isaac");
        Float cost;
        String stringcost;
        stringcost = "No current active orders";
        for (int i=0; i<orders.size();i++ ){
            if (orders.get(i).getOrderStatus() ==1) {
                cost = orders.get(i).getCost();
                stringcost = String.valueOf(cost);}
        }


        String InputDate = stringcost;
        ImageView mImageView = findViewById(R.id.iv);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(InputDate, 500, 500);
        mImageView.setImageBitmap(mBitmap);
//        submit.setText("AWESOME!");
    }
}
