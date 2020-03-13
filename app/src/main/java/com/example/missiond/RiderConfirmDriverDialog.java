package com.example.missiond;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ViewfinderView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.fragment.app.DialogFragment;

public class RiderConfirmDriverDialog extends DialogFragment {
    private Button email,call,cancel,confirm;
    private RiderConfirmDriverListener listener;
    public interface RiderConfirmDriverListener{
        void onConfirmClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rider_confirm_driver_dialog,container,false);

        /*
        set driver's TextView: rating, plate num, name
        get driver's button: phone, email
         */

        DataBaseHelper DB = DataBaseHelper.getInstance();
        final Driver driver = DB.getDriver("Yifei");
        String driver_name = driver.getUserName();
        final float driver_rating = driver.getRating();
        TextView name = v.findViewById(R.id.driver_name);
        TextView rating = v.findViewById(R.id.driver_rating);
        name.setText(driver_name);
        rating.setText(String.valueOf(driver_rating));

        call = v.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = driver.getPhoneNumber(); // phone = driver.getPhone
                String s = "tel:" + phone;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(s));
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    startActivity(callIntent);
                }
            }
        });

        cancel = v.findViewById(R.id.cancel_river_confirm);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        confirm = v.findViewById(R.id.confirm_river_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmClick();
                getDialog().dismiss();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RiderConfirmDriverListener){
            listener = (RiderConfirmDriverListener) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + " must implement RiderConfirmDriverListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
