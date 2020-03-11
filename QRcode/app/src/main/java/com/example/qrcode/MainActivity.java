package com.example.qrcode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    public Button submit;
    public Button scan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = findViewById(R.id.submit_button);
        scan = findViewById(R.id.scan_button);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GenerateQR();
            }
        });


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
            } else {
//                pass (result) to DB
                Toast.makeText(this, "Scan:" + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

        public void GenerateQR() {
            EditText text = findViewById(R.id.editText);
            String message = text.getText().toString();
            ImageView mImageView = findViewById(R.id.iv);
            Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(message, 480, 480);
            mImageView.setImageBitmap(mBitmap);
            submit.setText("AWESOME!");
    }

}
