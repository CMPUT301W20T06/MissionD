package com.example.missiond;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Allow rider to start the trip
 * @author
 *  Weiyi Wu
 * @version
 *  Mar.12 2020
 */
public class initFragment extends Fragment {
    private initFragmentListener listener;
    private Button startTrip;

    public interface initFragmentListener {
//        void RiderStartClick(String input1, String input2);
        void onRiderStartTripClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.init_fragment,container, false);

        startTrip = v.findViewById(R.id.StartTrip_Init);

        startTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRiderStartTripClick();
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof initFragmentListener){
            listener = (initFragmentListener) context;
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
