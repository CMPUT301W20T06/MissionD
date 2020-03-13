package com.example.missiond;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RiderMakeRequestFragment extends Fragment {
    public riderMakeRequestFragmentListener listener;
    private Button request, addMoney;

    public interface riderMakeRequestFragmentListener{
        void onRequestTripClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rider_request_detail,container,false);
//
//        request = v.findViewById(R.id.requestRide_request);
//        request.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onRequestTripClick();
//            }
//        });

        return v;
    }
}
