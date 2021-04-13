package com.example.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRcode extends AppCompatActivity {
    ImageView qrCode;
    TextView stringResult;
    String qrDetails;
    Intent intent;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_rcode);
        qrCode=findViewById(R.id.qrCode);
        stringResult=findViewById(R.id.stringResult);
        intent=getIntent();
        qrDetails=intent.getStringExtra("result");
        stringResult.setText(qrDetails);
        QRGEncoder qrgEncoder = new QRGEncoder(qrDetails,null, QRGContents.Type.TEXT,500);
        bitmap=qrgEncoder.getBitmap();
        qrCode.setImageBitmap(bitmap);
    }
}