package com.example.missiond;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.DialogFragment;

import java.util.List;


/**
 * Ask driver to confirm the money that will be paid on that trip
 * @author
 *  Weiting Chi
 * @version
 *  Mar.26 2020
 */
public class DriverWaitRiderConfrimFragment extends DialogFragment {

    /////////////////////////////////////
    private Button tesing_button;
    private Button testing_cancel;
    //////////////////////////////////////

    public String Location;
    public String Destination;
    private String Rider;
    private float startLat,startLng,endLat,endLng;
    private String Order_id, driver_name;
    private Boolean riderAccept = false;;
    private Boolean riderCancel = false;

    private Handler handler = new Handler();

    final DataBaseHelper DB = DataBaseHelper.getInstance();
    Order order1;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wait_rider_confirm_fragment, container, false);
        startRepeating1();
        //startRepeating2();


        Bundle bundle = getArguments();
        if (bundle!=null){
            Location = bundle.getString("trip_location");
            Destination = bundle.getString("trip_destination");
            startLat = bundle.getFloat("startLocationLat");
            startLng = bundle.getFloat("startLocationLng");
            endLat =bundle.getFloat("endLocationLat");
            endLng = bundle.getFloat("endLocationLng");
            Rider = bundle.getString("rider");
            Order_id = bundle.getString("order_id");
            driver_name = bundle.getString("user_name");
        }

        //////////////////////////////////////////////////////////////////
        tesing_button = v.findViewById(R.id.just_for_testing);
        testing_cancel = v.findViewById(R.id.cancel_for_testing);

        DB.getOrderById(Order_id, new Consumer<Order>() {
            @Override
            public void accept(Order order) {
                order1 = order;
                onLoaded(order1);
            }
        });

        tesing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order1.setOrderStatus(0);
                DB.updateOrder(order1);
//                getDialog().dismiss();
//                Bundle bundle = new Bundle();
//                bundle.putString("trip_location",Location);
//                bundle.putString("trip_destination",Destination);
//                bundle.putFloat("startLocationLat",startLat);
//                bundle.putFloat("startLocationLng",startLng);
//                bundle.putFloat("endLocationLat",endLat);
//                bundle.putFloat("endLocationLng",endLng);
//                bundle.putString("rider",Rider);
//
//                DriverAfterRiderConfrimFragment fragment = new DriverAfterRiderConfrimFragment();
//                fragment.setArguments(bundle);
//                fragment.show(getFragmentManager(),"test1");
            }
        });

        testing_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order1.setOrderStatus(3);
                DB.updateOrder(order1);
//                getDialog().dismiss();
//                new DriverAfterRiderCancelFragment().show(getFragmentManager(),"test2");
            }
        });

        return v;
    }


    public void onLoaded(Order order){
        order.setDriver(driver_name);
        order.setOrderStatus(2);
        DB.updateOrder(order);
    }

    public void startRepeating1(){
        runnable1.run();

    }
//    public void startRepeating2(){
//        runnable2.run();
//    }

    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            if (riderAccept){
                handler.removeCallbacks(runnable1);
                getDialog().dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("trip_location",Location);
                bundle.putString("trip_destination",Destination);
                bundle.putFloat("startLocationLat",startLat);
                bundle.putFloat("startLocationLng",startLng);
                bundle.putFloat("endLocationLat",endLat);
                bundle.putFloat("endLocationLng",endLng);
                bundle.putString("rider",Rider);

                DriverAfterRiderConfrimFragment fragment = new DriverAfterRiderConfrimFragment();
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(),"test1");
            }
            if (riderCancel){
                handler.removeCallbacks(runnable1);
                getDialog().dismiss();
                new DriverAfterRiderCancelFragment().show(getFragmentManager(),"test2");
            }
            else{
                getOrder1();
                handler.postDelayed(this, 2000);
            }
        }
    };

//    private Runnable runnable2 = new Runnable() {
//        @Override
//        public void run() {
//            if (riderCancel){
//                handler.removeCallbacks(runnable2);
//                getDialog().dismiss();
//                new DriverAfterRiderCancelFragment().show(getFragmentManager(),"test2");
//            }
//            else{
//                getOrder2();
//                handler.postDelayed(this, 2000);
//            }
//        }
//    };

    private void getOrder1(){
        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                for (int i=0;i <orders.size();i++) {
                    Order order = orders.get(i);
                    if (order.getId().equals(Order_id)) {
                        order1 = order;
                    }
                }
                onLoaded1();
            }
        });
    }
//    private void getOrder2(){
//        DB.getAllOrders(new Consumer<List<Order>>() {
//            @Override
//            public void accept(List<Order> orders) {
//                for (int i=0;i <orders.size();i++) {
//                    Order order = orders.get(i);
//                    if (order.getId().equals(Order_id)) {
//                        order1 = order;
//                    }
//                }
//                onLoaded2();
//            }
//        });
//    }

    public void onLoaded1(){
        if (order1.getOrderStatus()==3) {
            riderAccept=true;

            Toast.makeText(getActivity(),"status changed 3",Toast.LENGTH_SHORT).show();
        }
        if (order1.getOrderStatus()==0) {
            riderCancel=true;
        }

        Toast.makeText(getActivity(),order1.getOrderStatus().toString(),Toast.LENGTH_SHORT).show();
    }

    public void onLoaded2(){
        if (order1.getOrderStatus()==0) {
            riderCancel=true;
        }

        Toast.makeText(getActivity(),order1.getOrderStatus().toString(),Toast.LENGTH_SHORT).show();
    }
}

