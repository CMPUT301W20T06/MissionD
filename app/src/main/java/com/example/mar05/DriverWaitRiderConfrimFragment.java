package com.example.mar05;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DriverWaitRiderConfrimFragment extends DialogFragment {
    private Button tesing_button;
    private Button testing_cancel;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wait_rider_confirm_fragment, container, false);

        tesing_button = v.findViewById(R.id.just_for_testing);
        testing_cancel = v.findViewById(R.id.cancel_for_testing);

        tesing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                new DriverAfterRiderConfrimFragment().show(getFragmentManager(),"test1");
            }
        });

        testing_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                new DriverAfterRiderCancelFragment().show(getFragmentManager(),"test2");
            }
        });

        return v;
    }
}
