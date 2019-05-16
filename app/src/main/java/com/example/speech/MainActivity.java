package com.example.speech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.speech.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private TextView riferimento;
    private ImageButton mSpeakBtn;
    private Button BTTOK;
    private EditText etCtrlV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        riferimento = (TextView) findViewById(R.id.riferimentoTV);
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        etCtrlV = (EditText) findViewById(R.id.etCtrlV);
        BTTOK = (Button) findViewById(R.id.bttOK);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        BTTOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etCtrlV.getText().toString();
                riferimento.setText(text);
                //String str[] = text.split(" ");
                //List<String> alText = new ArrayList<String>();
                //alText = Arrays.asList(str);


                //for (int i=0; i<alText.size(); i++)
                   // riferimento.setText(alText.get(i));
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
            mVoiceInputTv.setText(speech.get(i));
    }
}