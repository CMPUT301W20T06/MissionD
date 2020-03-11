package com.example.mar05;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class IsMoneyOKFragment extends DialogFragment {

    private Button close_button;
    private Button confirm_button;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.is_money_ok_fragment, container, false);

        close_button = v.findViewById(R.id.close_button);
        confirm_button = v.findViewById(R.id.ConfirmMoney_button);

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG,"onClick:closing dialog");
                getDialog().dismiss();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;

        }
}
