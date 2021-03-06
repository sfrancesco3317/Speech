package com.example.Memorize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class grade extends AppCompatActivity {

    TextView reference_tv = null;
    TextView speech_tv = null;
    TextView grade_tv = null;
    TextView numWrongWords_tv = null;
    TextView wrongWords_tv =null;
    Button return_btt = null;
    CompareText compareObj = new CompareText();
    private static DecimalFormat df = new DecimalFormat("0");
    ImageView immVoto= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        float finalGrade = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        immVoto= (ImageView) findViewById(R.id.ivImmVoto);
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

        boolean testIfEquals = false;
        reference = compareObj.deleteSpacesFromBeginningAndBottom(reference);
        speech = compareObj.deleteSpacesFromBeginningAndBottom(speech);
        String[] referenceSplitted = compareObj.deletePunctuationFromText(reference);
        String[] speechSplitted = compareObj.deletePunctuationFromText(speech);

        reference_tv.setText(reference);
        speech_tv.setText(speech);

        if(referenceSplitted.length == speechSplitted.length)
            for(int i = 0; i < referenceSplitted.length; i++){
                if(referenceSplitted[i].equals(speechSplitted[i])){
                    testIfEquals = true;
                }
                else {
                    testIfEquals = false;
                    break;
                }
            }

        if (testIfEquals == true){
            finalGrade = 30;

            grade_tv.setText("\n" + "\n" + "Il tuo voto è: " + df.format(finalGrade) + "/30" + "\n");
            immVoto.setImageResource(R.drawable.promosso);
        }

        else {
            CompareText.CompareTextParams inputParams = new CompareText.CompareTextParams(reference, speech);
            CompareText myCompareText = new CompareText();

            //Chiamo Compare Text in Background con Async
            ArrayList<String> wrongWords = new ArrayList<>();

            try {
                wrongWords = myCompareText.execute(inputParams).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            reference_tv.setText(reference);
            speech_tv.setText(speech);

            if(wrongWords == null){
                finalGrade = 29;
                grade_tv.setText("\n" + "\n" + "Il tuo voto è: " + finalGrade + "/30" + "\n");
            }
            else {

                //Variabile che ci dà un'idea di quanto abbiamo ripetuto del riferimento
                float referencePercent = (float) speechSplitted.length /referenceSplitted.length;
                if(referencePercent>1) referencePercent = 1;
                if(referencePercent<0) referencePercent = 0;

                if(referencePercent < 0.7){ //Questo ci aiuta ad aumentare la gravità dell'errore dato da una parola mancante nel caso di bassa percentale
                    finalGrade = referencePercent * 30 * (referenceSplitted.length - ((float) wrongWords.size())) / (float) referenceSplitted.length;
                }
                else{
                    finalGrade = referencePercent * 30 * (referenceSplitted.length - ((float) wrongWords.size()/ 2)) / (float) referenceSplitted.length;
                }
                if (finalGrade < 0) finalGrade = 0;
                if (finalGrade > 29) finalGrade = 29;

                grade_tv.setText("\n" + "\n" + "Il tuo voto è: " + df.format(finalGrade) + "/30" + "\n");

                //stampo numero parole sbagliate
                if(wrongWords.size()>0)
                    numWrongWords_tv.setText("Il numero di parole sbagliate è: " + wrongWords.size() + "\n");

                //stampo le parole sbagliate eliminando le [] dell'arraylist
                String wrongWordsString = null;
                wrongWordsString = wrongWords.toString().replace("[", "").replace("]", "");

                wrongWords_tv.setText("Le parole sbagliate sono: " + wrongWordsString + "\n");
            }


            if((finalGrade >= 18) && (finalGrade < 30)) {
                immVoto.setImageResource(R.drawable.promosso);
                }
            else{
                immVoto.setImageResource(R.drawable.bocciato);
                }
            }


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
