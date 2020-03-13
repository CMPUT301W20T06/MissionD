package com.example.missiond;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Confirm if rider wants to cancel the request
 * @author
 *  Weiyi Wu
 * @version
 *  Mar.12 2020
 */
public class RiderConfirmCancelDialog extends DialogFragment {
    private Button close, confirm;
    private RiderConfirmCancelDialogListener listener;

    public interface RiderConfirmCancelDialogListener{
        void onCancelConfirmClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rider_confirm_cancel_dialog,container,false);

        close = v.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        confirm = v.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                listener.onCancelConfirmClick();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RiderConfirmCancelDialog.RiderConfirmCancelDialogListener){
            listener = (RiderConfirmCancelDialog.RiderConfirmCancelDialogListener) context;
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
