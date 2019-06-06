package com.example.speech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Output_OCR extends AppCompatActivity {

    EditText editText = null;
    Button bttOk= null;
    Button bttReturn= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output__ocr);

        Intent intentInput = getIntent();


        bttOk= (Button) findViewById(R.id.bttOKOutputOCR);
        bttReturn= (Button) findViewById(R.id.bttReturnToOCR);
        editText= (EditText) findViewById(R.id.etOutputOCR);
        editText.setText(intentInput.getStringExtra(getString(R.string.STRINGA_TESTO_RIFERIMENTO_INPUT)));

        bttOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();  //predìndo quello dall'edit text
                Intent i = new Intent(getString(R.string.LAUNCH_RECORDING_ACTIVITY));
                i.putExtra(getString(R.string.STRINGA_TESTO_RIFERIMENTO_INPUT), text);
                startActivity(i);

            }
        });

        bttReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(getString(R.string.LAUNCH_OCR_ACTIVITY));
                startActivity(i);

            }
        });

    }
}
