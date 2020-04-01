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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
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
    private String phone = null;
    private String emailAddr = null;
    private String driver_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.driver_info_dialog,container,false);

        final TextView name = v.findViewById(R.id.driver_name);
        final TextView rating = v.findViewById(R.id.driver_rating);
        /**
         * read orderID that passed from the activity
         * find order by orderID
         * get driver name from order
         * find driver by driver name
         * read driver's info
         */
        driver_name = getArguments().getString("driver");

        DataBaseHelper DB = DataBaseHelper.getInstance();
        DB.getDriver(driver_name, new Consumer<Driver>() {
            @Override
            public void accept(Driver driver) {
                String driver_name = driver.getUserName();
                final float driver_rating = driver.getRating();
                name.setText(driver_name);
                rating.setText(String.valueOf(driver_rating));
                phone = driver.getPhoneNumber();
                emailAddr = driver.getEmailAddress();
            }
        });

        call = v.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone == null) {
                    return;
                }
                String s = "tel:" + phone;
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

        email = v.findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * https://stackoverflow.com/questions/28588255/no-application-can-perform-this-action-when-send-email
                 * @author
                 * yubaraj poudel
                 */
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddr});
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
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
