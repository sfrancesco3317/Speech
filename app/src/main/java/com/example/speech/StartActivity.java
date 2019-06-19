package com.example.speech;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    //activity iniziale che si chiuderà in automatico dopo 2 secondi
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mHandler.postDelayed(new Runnable() {
            public void run() {

                Intent i = new Intent(StartActivity.this, MainActivity.class);
                startActivity(i);

            }
        }, 2000);


    }
}
