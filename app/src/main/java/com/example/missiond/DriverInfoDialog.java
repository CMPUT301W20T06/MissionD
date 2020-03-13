package com.example.missiond;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

/**
 * Displays driver's information
 * Can call driver by clicking on the call button
 * @author
 *  Weiyi Wu
 * @version
 *  Mar.12 2020
 */
public class DriverInfoDialog extends DialogFragment {

    private Button close,call,email;
    String s;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.driver_info_dialog,container,false);

        /*
        All info should be loaded from database
        set driver name, plate number, rating
         */

        call = v.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "123456";
                s = "tel:" + phone; // s = "tel:" + driver.getPhone
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(s));
                /**
                 * https://stackoverflow.com/questions/40125931/how-to-ask-permission-to-make-phone-call-from-android-from-android-version-marsh
                 * @author
                 *  Fabian Tamp
                 */
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    startActivity(callIntent);
                }
            }
        });


        close = v.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return v;
    }
}
