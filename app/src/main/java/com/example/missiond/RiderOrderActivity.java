package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import java.util.ArrayList;
import java.util.List;

public class RiderOrderActivity extends AppCompatActivity {
    final DataBaseHelper DB = DataBaseHelper.getInstance();
    private ImageButton back;
    private String name;
    ListView orderList;
    ArrayAdapter<OrderHistory> orderAdapter;
    ArrayList<OrderHistory> orderDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_order);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        orderList = findViewById(R.id.order_list);

        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderDataList = new ArrayList<>();

        DB.GetUserOrders(name, new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                Order active_order;
                for (int i=0; i < orders.size();i++){
                    active_order = orders.get(i);
                    orderDataList.add((new OrderHistory(active_order.getStartLocation(), active_order.getEndLocation(),
                            active_order.getDriver(), active_order.getCost().toString())));
                }
            }
        });

        orderAdapter = new OrderList(this, orderDataList);
        orderList.setAdapter(orderAdapter);
    }
}
