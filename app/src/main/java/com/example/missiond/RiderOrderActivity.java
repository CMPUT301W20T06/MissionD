package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Show user's order history
 * @author
 *  Weiyi Wu
 * @version
 *  March.26 2020
 */

public class RiderOrderActivity extends AppCompatActivity {
    final DataBaseHelper DB = DataBaseHelper.getInstance();
    private ImageButton back;
    private String name;
    private String userType;
    ListView orderList;
    ArrayAdapter<OrderHistory> orderAdapter;
    ArrayList<OrderHistory> orderDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_order);
        Intent i = getIntent();
        name = i.getStringExtra("user_name");
        userType = i.getStringExtra("user_type");
        Toast.makeText(this, name,Toast.LENGTH_SHORT).show();
        orderList = findViewById(R.id.order_list);

        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderDataList = new ArrayList<>();

        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                Order active_order;
                for (int i=0; i < orders.size();i++){
                    active_order = orders.get(i);
                    if (userType.equals("rider")) {
                        if (active_order.getRider().equals(name)) {
                            orderDataList.add((new OrderHistory(active_order.getStartLocation(), active_order.getEndLocation(),
                                    active_order.getDriver(), active_order.getCost().toString())));
                        }
                    }
                    else {
                        if (active_order.getDriver() != null && active_order.getDriver().equals(name)) {
                        orderDataList.add((new OrderHistory(active_order.getStartLocation(), active_order.getEndLocation(),
                                active_order.getDriver(), active_order.getCost().toString())));
                        }
                    }

                }
                onLoaded();

            }
        });

    }

    private void onLoaded(){
        orderAdapter = new OrderList(this, orderDataList);
        orderList.setAdapter(orderAdapter);
    }
}
