package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

/**
 * Display a map with driver current location
 * Driver can start to see trip requests by pressing see trip button
 * @author
 *  Isaac Zhang
 * @version
 *  Mar.12 2020
 */

public class ScanQRcode extends AppCompatActivity {

    private Button scan;

    final DataBaseHelper DB = DataBaseHelper.getInstance();
    private Order order1;
    private String Order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_get_paid);

        scan = findViewById(R.id.scan_button);
        Order_id = getIntent().getExtras().getString("order_id");

        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                for (int i=0;i <orders.size();i++) {
                    Order order = orders.get(i);
                    if (order.getId().equals(Order_id)) {
                        order1=order;
                        order1.setOrderStatus(5);
                        DB.updateOrder(order1);
                        //Toast.makeText(getActivity(),order1.getOrderStatus().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /**
         * press the scan button
         * it will open the camera
         */


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScanQRcode.this);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
            }
        });
    }

    /**
     * This method will set the driver current location on the map
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
            } else {
//                username: isaac
//                start location : edmonton
//                destnation location: calgary
//                Cost: $23
                Toast.makeText(this, "Scan:" + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
