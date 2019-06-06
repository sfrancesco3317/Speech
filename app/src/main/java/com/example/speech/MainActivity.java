package com.example.speech;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speech.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mVoiceInputTv;
    private TextView riferimento;
    private Button BTTOK;
    private EditText etCtrlV;
    private ImageButton Bttfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        riferimento = (TextView) findViewById(R.id.riferimentoTV);
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        etCtrlV = (EditText) findViewById(R.id.etCtrlV);
        BTTOK = (Button) findViewById(R.id.bttOK);
        Bttfoto= (ImageButton) findViewById(R.id.btFotocamera);



        BTTOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etCtrlV.getText().toString();
                if(text.length()>0){
                    Intent i = new Intent(getString(R.string.LAUNCH_RECORDING_ACTIVITY));
                    i.putExtra(getString(R.string.STRINGA_TESTO_RIFERIMENTO_INPUT), text);
                    startActivity(i);
                }
                else {
                    Context context = getApplicationContext();
                    CharSequence testoToast = "Attenzione, un testo di riferimento vuoto non è un testo di riferimento!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, testoToast, duration);
                    toast.show();
                }

            }

        });

        Bttfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fotoIntent= new Intent(getString(R.string.LAUNCH_OCR_ACTIVITY));
                startActivity(fotoIntent);
            }
        });

    }

}

