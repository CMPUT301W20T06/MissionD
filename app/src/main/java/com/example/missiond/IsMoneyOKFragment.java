package com.example.missiond;

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

public class IsMoneyOKFragment extends DialogFragment {

    private Button close_button;
    private Button confirm_button;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.is_money_ok_fragment, container, false);

        close_button = v.findViewById(R.id.close_button);
        confirm_button = v.findViewById(R.id.ConfirmMoney_button);

        /**
         * click the close button
         * the dialog will dismiss
         */
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG,"onClick:closing dialog");
                getDialog().dismiss();
            }
        });

        /**
         * press the confirm button
         * the dialog will dismiss first
         * then, will show the driver wait rider confirm Fragment
         */
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                new DriverWaitRiderConfrimFragment().show(getFragmentManager(),"Waiting");
            }
        });

        return v;

        }
}
