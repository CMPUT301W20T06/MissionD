package com.example.missiond;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Ask driver to confirm the money that will be paid on that trip
 * @author
 *  Weiting Chi
 * @version
 *  Mar.12 2020
 */
public class DriverAfterRiderCancelFragment extends DialogFragment {
    private Button button_back;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.after_rider_cancel_fragment, container, false);

        button_back = v.findViewById(R.id.backtoRequest_button);

        /**
         * click the back button
         * the dialog will dismiss
         * go to back to the DriverSearchRequest
         */
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                Intent intent = new Intent(getActivity(), DriverSearchRequestActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
