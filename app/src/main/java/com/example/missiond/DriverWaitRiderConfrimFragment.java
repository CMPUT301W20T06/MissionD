package com.example.missiond;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
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
 *  Mar.30 2020
 */
public class DriverWaitRiderConfrimFragment extends DialogFragment {

    AnimationDrawable loadingAnimation;

    public String Location;
    public String Destination;
    private String Rider;
    private float startLat,startLng,endLat,endLng;
    private String Order_id, driver_name;
    private Boolean stop = false;;
    private Boolean riderCancel = false;
    private Boolean driverConfirm = false;

    private Handler handler = new Handler();

    final DataBaseHelper DB = DataBaseHelper.getInstance();
    private Order order1;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wait_rider_confirm_fragment, container, false);

        /**
         * add a loading Animation
         */
        ImageView imageView = v.findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.loading_animation);
        loadingAnimation = (AnimationDrawable) imageView.getBackground();


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

        /**
         * use the database helper to get the current order
         * assign driver to the order
         * set status to 2
         */
        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                for (int i=0;i <orders.size();i++) {
                    Order order = orders.get(i);
                    if (order.getId().equals(Order_id)) {
                        order1=order;
                        order1.setDriver(driver_name);
                        order1.setOrderStatus(2);
                        DB.updateOrder(order1);
                        //Toast.makeText(getActivity(),order1.getOrderStatus().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        startRepeating();

        loadingAnimation.start();

        return v;
    }





    public void startRepeating(){
        runnable.run();
    }

    /**
     * set up the handle to check order status every second
     */

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (stop){
                if (riderCancel){
                    getDialog().dismiss();
                    new DriverAfterRiderCancelFragment().show(getFragmentManager(),"test2");
                } else {
                    handler.removeCallbacks(runnable);
                    getDialog().dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putString("trip_location", Location);
                    bundle.putString("trip_destination", Destination);
                    bundle.putFloat("startLocationLat", startLat);
                    bundle.putFloat("startLocationLng", startLng);
                    bundle.putFloat("endLocationLat", endLat);
                    bundle.putFloat("endLocationLng", endLng);
                    bundle.putString("order_id",Order_id);
                    bundle.putString("rider", Rider);

                    DriverAfterRiderConfrimFragment fragment = new DriverAfterRiderConfrimFragment();
                    fragment.setArguments(bundle);
                    fragment.show(getFragmentManager(), "test1");
                }
            } else {
                getOrder();
                handler.postDelayed(this, 2000);
            }
        }
    };

    /**
     * use database to get the order
     */

    private void getOrder(){
        DB.getAllOrders(new Consumer<List<Order>>() {
            @Override
            public void accept(List<Order> orders) {
                for (int i=0;i <orders.size();i++) {
                    Order order = orders.get(i);
                    if (order.getId().equals(Order_id)) {
                        order1 = order;
                    }
                }
                onLoaded();
            }
        });
    }

    /**
     * set the status to 3 if rider confirm the driver
     * set the status to 1 if rider cancel the driver
     */


    public void onLoaded(){
        if (order1.getOrderStatus()==3) {
            stop=true;
            //Toast.makeText(getActivity(),"status changed 3",Toast.LENGTH_SHORT).show();
        }
        if (order1.getOrderStatus()==2){
            driverConfirm=true;
        }
        else if (driverConfirm){
            if (order1.getOrderStatus()==1) {
                stop = true;
                riderCancel = true;
                //Toast.makeText(getActivity(), "cancel" + order1.getOrderStatus().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        //Toast.makeText(getActivity(),order1.getOrderStatus().toString(),Toast.LENGTH_SHORT).show();
    }

}

