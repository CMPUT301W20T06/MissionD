package com.example.mar05;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RiderEndPayActivity extends AppCompatActivity {
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_end_pay);

        finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GenerateQR();


    }

    public void GenerateQR() {
//            EditText text = findViewById(R.id.editText);
//            String message = text.getText().toString();
        String InputDate = "WHAT IS IN YOUR DATABASE?";
        ImageView mImageView = findViewById(R.id.iv);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(InputDate, 500, 500);
        mImageView.setImageBitmap(mBitmap);
//        submit.setText("AWESOME!");
    }
}
