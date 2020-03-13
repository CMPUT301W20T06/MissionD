package com.example.missiond;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static java.lang.Integer.valueOf;

/**
 * Ask rider to confirm and if they would like to add extra amount of fare
 * @author
 *  Weiyi Wu
 * @version
 *  Mar.12 2020
 */
public class RiderAddMoneyFragment extends DialogFragment {
//    private static final String TAG = "AddMoneyFragment";

    private EditText enterAmount;
    private Button close,save;
    private OnAddMoneyFragmentListener listener;
    private TextView amount;

    public interface OnAddMoneyFragmentListener{
        void onAddSaveClick(int amount);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String money = this.getArguments().getString("moneyAmount");
        View v = inflater.inflate(R.layout.rider_add_money,container,false);

//        Toast.makeText(getActivity(),money,Toast.LENGTH_SHORT).show();
        amount = v.findViewById(R.id.amount);
        amount.setText(money);

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
//                if (amount.length()>0) {
//                    listener.onAddSaveClick(amount);
//                }
                int return_amount;
                if (amount.length()==0){
                    return_amount = 0;
                }
                else{
                    return_amount = valueOf(amount);
                }

                listener.onAddSaveClick(return_amount);
                getDialog().dismiss();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RiderAddMoneyFragment.OnAddMoneyFragmentListener){
            listener = (RiderAddMoneyFragment.OnAddMoneyFragmentListener) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + " must implement OnAddMoneyFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
