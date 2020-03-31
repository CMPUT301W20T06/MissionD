package com.example.missiond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderList extends ArrayAdapter<OrderHistory> {
    private ArrayList<OrderHistory> orderHistory;
    private Context context;
    public OrderList (Context context, ArrayList<OrderHistory> orderHistory){
        super(context, 0, orderHistory);
        this.context = context;
        this.orderHistory = orderHistory;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.order_content, parent,false);
        }

        OrderHistory order = orderHistory.get(position);
        TextView pickUpText = view.findViewById(R.id.Location1);
        TextView destText = view .findViewById(R.id.Location2);
        TextView driver = view.findViewById(R.id.driver);
        TextView fare = view.findViewById(R.id.fare);

        pickUpText.setText(order.getPickUp());
        destText.setText(order.getDest());
        driver.setText(order.getDriver());
        fare.setText(order.getFare());

        return view;
    }
}
