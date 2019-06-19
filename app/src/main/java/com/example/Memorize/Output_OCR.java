package com.example.Memorize;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


                boolean stringIsOnlySpaces = false;
                for (int i = 0; i < text.length(); i++) {
                    if (!Character.isLetter(text.charAt(i)))
                        stringIsOnlySpaces = true;
                    else{
                        stringIsOnlySpaces = false;
                        break;
                    }
                }

                if(text.length()==0 || text.isEmpty() || stringIsOnlySpaces){
                    Context context = getApplicationContext();
                    CharSequence testoToast = "Attenzione, un testo di riferimento vuoto non è un testo di riferimento!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, testoToast, duration);
                    toast.show();
                }

                else {
                    Intent i = new Intent(getString(R.string.LAUNCH_RECORDING_ACTIVITY));
                    i.putExtra(getString(R.string.STRINGA_TESTO_RIFERIMENTO_INPUT), text);
                    startActivity(i);
                }

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
