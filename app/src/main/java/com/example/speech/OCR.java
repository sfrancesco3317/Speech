package com.example.speech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class OCR extends AppCompatActivity {

    Button btt= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        btt= (Button) findViewById(R.id.button);

    }
}
