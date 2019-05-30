package com.example.speech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.Image;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class recording_activity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    TextView voiceInput_TV = null;
    ImageButton speech_BTT = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_activity);

        voiceInput_TV = (TextView) findViewById(R.id.voiceInput);
        speech_BTT = (ImageButton) findViewById(R.id.btnSpeak);

        speech_BTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });


    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<String> speech = new ArrayList<>();

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    speech.add(result.get(0));
                    //mVoiceInputTv.setText(result.get(0));
                }
                break;
            }
        }

        for (int i=0; i<speech.size(); i++)
            voiceInput_TV.setText(speech.get(i));

        Intent intent = getIntent();
        String reference = intent.getStringExtra(getString(R.string.STRINGA_TESTO_RIFERIMENTO_INPUT));

        Intent intentExit = new Intent(getString(R.string.LAUNCH_GRADE_ACTIVITY));
        intentExit.putExtra(getString(R.string.STRINGA_TESTO_SPEECH_INPUT), speech);
        intentExit.putExtra(getString(R.string.STRINGA_TESTO_RIFERIMENTO_INPUT), reference);

        startActivity(intentExit);
    }



}
