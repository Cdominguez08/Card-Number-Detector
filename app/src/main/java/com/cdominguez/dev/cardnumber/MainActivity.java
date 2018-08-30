package com.cdominguez.dev.cardnumber;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cdominguez.dev.cardnumber.ocr.OcrCaptureActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 200;
    private final int REQUEST_OCR = 100;
    private EditText edTNumCard;
    private ImageView imgTCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edTNumCard = findViewById(R.id.edTNumero);
        imgTCam = findViewById(R.id.imgTCamera);

        imgTCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOCR();
            }
        });
    }

    private void loadOCR() {

        startActivityForResult(new Intent(this, OcrCaptureActivity.class), REQUEST_OCR);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
//        }else {
//
//        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_OCR){

            if (resultCode == RESULT_OK){

                String cardNumber = data.getStringExtra("cardNumber");
                edTNumCard.setText(cardNumber);
            }
        }
    }

}
