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
import androidx.core.util.Consumer;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.fragment.app.DialogFragment;

import java.util.List;

/**
 * Displays driver's information
 * if rider confirms drivers, the go to the next activity, else find another driver
 * @author
 *  Weiyi Wu
 * @version
 *  Mar.12 2020
 */
public class RiderConfirmDriverDialog extends DialogFragment {
    private Button email,call,cancel,confirm;
    private RiderConfirmDriverListener listener;
    private String rider_name;
    private String phone,emailAddr,id,driver_name;
    private float driver_rating;
    private TextView rating,name;
    Order order1;

    public interface RiderConfirmDriverListener{
        void onConfirmClick(String type);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.rider_confirm_driver_dialog,container,false);

        final DataBaseHelper DB = DataBaseHelper.getInstance();
        id = getArguments().getString("orderID");
        driver_name = getArguments().getString("driver");

        DB.getDriver(driver_name, new Consumer<Driver>() {
            @Override
            public void accept(Driver driver) {
                onLoaded(v,driver);
            }
        });

        call = v.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone == null){
                    Toast.makeText(getActivity(),"phone==null",Toast.LENGTH_LONG).show();
                    return;
                }
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


        email = v.findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailAddr == null){
                    Toast.makeText(getActivity(),"email==null",Toast.LENGTH_LONG).show();
                    return;
                }
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

        cancel = v.findViewById(R.id.cancel_river_confirm);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmClick("cancel");
                getDialog().dismiss();
            }
        });

        confirm = v.findViewById(R.id.confirm_river_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmClick("confirm");
                getDialog().dismiss();
            }
        });

        return v;
    }

    public void onLoaded(View v ,Driver driver){
        driver_rating = driver.getRating();
        phone = driver.getPhoneNumber();
        emailAddr = driver.getEmailAddress();
        driver_name = driver.getUserName();

        rating = v.findViewById(R.id.driver_rating);
        rating.setText(String.valueOf(driver_rating));
        name = v.findViewById(R.id.driver_name);
        name.setText(driver_name);
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
