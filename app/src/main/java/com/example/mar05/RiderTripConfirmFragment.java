package com.example.mar05;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class RiderTripConfirmFragment extends Fragment {
    private riderTripConfirmFragment listener;
    private initFragment initF;
    private RiderAddMoneyFragment addMoneyFragment;
    private TextView pickUpLocation, Destination;
    private ImageButton back;
    private Button addMoney,confirm;
//    private Button addMoney;

    public interface riderTripConfirmFragment{
        void useConfirmRequest();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rider_confirm_trip_fragment,container,false);

//        pickUpLocation = v.findViewById(R.id.Location1Confirm);
//        Destination = v.findViewById(R.id.Location2Confirm);
//        back = v.findViewById(R.id.riderBackConfirm);
//        addMoney = v.findViewById(R.id.addMoneyConfirm);
//        confirm = v.findViewById(R.id.requestRideConfirm);
//
//
//        RiderActivity activity = (RiderActivity) getActivity();
//        pickUpLocation.setText(activity.getPickupLoc());
//        Destination.setText(activity.getDestLoc());
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initF = new initFragment();
//
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.initFrag, initF)
//                        .commit();
//
//                getFragmentManager().beginTransaction().remove(RiderTripConfirmFragment.this).commit();
//            }
//        });
//
//        addMoney.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RiderAddMoneyFragment addMoneyFragment = new RiderAddMoneyFragment();
//                addMoneyFragment.show(getFragmentManager(),"addMoneyFragment");
//            }
//        });
//
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // save all request details
//                getFragmentManager().beginTransaction().remove(RiderTripConfirmFragment.this).commit();
//
//                listener.useConfirmRequest();
//            }
//        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof riderTripConfirmFragment){
            listener = (riderTripConfirmFragment) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + " must implement initFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
