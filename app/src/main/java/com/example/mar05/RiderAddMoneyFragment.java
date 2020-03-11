package com.example.mar05;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class RiderAddMoneyFragment extends DialogFragment {
//    private static final String TAG = "AddMoneyFragment";

    private EditText enterAmount;
    private Button close,save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rider_add_money,container,false);

        close = v.findViewById(R.id.closeAddMoney);
        save = v.findViewById(R.id.saveAddMoney);
        enterAmount = v.findViewById(R.id.enterAddMoneyAmount);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG,"onClick:closing dialog");
                getDialog().dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = enterAmount.getText().toString();
                getDialog().dismiss();
            }
        });

        return v;
    }
}
