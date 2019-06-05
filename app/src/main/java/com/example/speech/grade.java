package com.example.speech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class grade extends AppCompatActivity {


    TextView reference_tv = null;
    TextView speech_tv = null;
    TextView grade_tv = null;

    Button return_btt = null;
    CompareText compareObj = new CompareText();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int finalGrade = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        reference_tv = (TextView) findViewById(R.id.referenceTV);
        speech_tv = (TextView) findViewById(R.id.speechTV);
        grade_tv = (TextView) findViewById(R.id.gradeTV);
        return_btt = (Button) findViewById(R.id.returnBTT);

        Intent intentInput = getIntent();
        String reference = intentInput.getStringExtra(getString(R.string.STRINGA_TESTO_RIFERIMENTO_INPUT));
        ArrayList<String> speech = new ArrayList<>();
        speech = intentInput.getStringArrayListExtra(getString(R.string.STRINGA_TESTO_SPEECH_INPUT));

        //Chiamo Compare Text in Background con Async
        ArrayList<String> wrongWords = new ArrayList<>();
        wrongWords = compareObj.compareText(reference, speech);

        ArrayList<String> testArray = new ArrayList<>();



        reference_tv.setText(reference);
        for (int i = 0; i< wrongWords.size(); i++)
            speech_tv.setText(wrongWords.get(i));


        finalGrade = wrongWords.size();

        grade_tv.setText("Il tuo voto è: " + finalGrade);

        return_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Torno alla home page.

                Intent returnIntent = new Intent(getString(R.string.LAUNCH_MAINACTIVITY));
                startActivity(returnIntent);
            }
        });

    }
}
