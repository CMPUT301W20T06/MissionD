package com.example.missiond;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RiderOrderActivity extends AppCompatActivity {
    private ImageButton back;
    ListView orderList;
    ArrayAdapter<OrderHistory> orderAdapter;
    ArrayList<OrderHistory> orderDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_order);
        orderList = findViewById(R.id.order_list);

        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String []location ={"University of Alberta", "University of Alberta", "NAIT", "University of Alberta","Edmonton International Airport","Mayfair South"};
        String []destination={"Southgate Centre","Edmonton International Airport","Southgate Centre","Mayfair South","Corona Station","Churchill Station"};
        String []driver = {"Driver1","Driver2","Driver3","Driver4","Driver5","Driver6"};
        String []fare = {"123","456","789","101112","131415","161718"};

        orderDataList = new ArrayList<>();

        for (int i=0;i<location.length;i++){
            /**
             * location[i],destination[i],driver[i],fare[i]应该从order中读取
             * e.g. OrderHistory(order.getPickUpLocaiton, order.getDest, order.getDriver, order.getMoney)
             */
            orderDataList.add((new OrderHistory(location[i], destination[i], driver[i], fare[i])));
        }

        orderAdapter = new OrderList(this, orderDataList);
        orderList.setAdapter(orderAdapter);
    }
}
