package com.example.speech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class grade extends AppCompatActivity {




    TextView reference_tv = null;
    TextView speech_tv = null;
    TextView grade_tv = null;
    TextView numWrongWords_tv = null;
    TextView wrongWords_tv =null;
    Button return_btt = null;
    CompareText compareObj = new CompareText();
    private static DecimalFormat df = new DecimalFormat("0");


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        float finalGrade = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        wrongWords_tv=(TextView)findViewById(R.id.tvWrongWords);
        numWrongWords_tv= (TextView) findViewById(R.id.tvNumWrongWords);
        reference_tv = (TextView) findViewById(R.id.referenceTV);
        speech_tv = (TextView) findViewById(R.id.speechTV);
        grade_tv = (TextView) findViewById(R.id.gradeTV);
        return_btt = (Button) findViewById(R.id.returnBTT);

        Intent intentInput = getIntent();
        String reference = intentInput.getStringExtra(getString(R.string.STRINGA_TESTO_RIFERIMENTO_INPUT));
        String speech = null;
        speech = intentInput.getStringExtra(getString(R.string.STRINGA_TESTO_SPEECH_INPUT));

        CompareText.CompareTextParams inputParams = new CompareText.CompareTextParams(reference, speech);
        CompareText myCompareText = new CompareText();

        //Chiamo Compare Text in Background con Async
        ArrayList<String> wrongWords = new ArrayList<>();
        //wrongWords = compareObj.compareText(reference, speech);

        try{
        wrongWords = myCompareText.execute(inputParams).get();}
    catch (Exception e) {
        e.printStackTrace();
    }

        reference_tv.setText(reference);
        speech_tv.setText(speech);

        String[] referenceSplitted =compareObj.deletePunctuationFromText(reference);
        finalGrade = 30*(float)(referenceSplitted.length-wrongWords.size())/(float)referenceSplitted.length;
        if(finalGrade<0) finalGrade = 0;
        grade_tv.setText("Il tuo voto è: " + df.format(finalGrade) + "/30");
        //stampo numero parole sbagliate
        numWrongWords_tv.setText("Il numero di parole sbagliate è: "+ wrongWords.size());
        //stampo le parole sbagliate eliminando le [] dell'arraylist
        String wrongWordsString= new String();
        wrongWordsString = wrongWords.toString().replace("[","").replace("]","");
        wrongWords_tv.setText("Le parole sbagliate sono: " + wrongWordsString + "\n");

        return_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Torno alla home page.
                Intent i = new Intent(grade.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

    }
}
