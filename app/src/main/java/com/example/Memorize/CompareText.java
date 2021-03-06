package com.example.Memorize;

import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class CompareText extends AsyncTask<CompareText.CompareTextParams, Void, ArrayList<String>>{ //This container allows me to pass multiple params to the doInBackground


    public static class CompareTextParams {
        String referenceText;
        String speechText;


        CompareTextParams(String referenceText, String speechText) {
            this.referenceText = referenceText;
            this.speechText = speechText;

        }
    }

    @Override
    protected ArrayList<String> doInBackground(CompareTextParams... params) {
        ArrayList<String> result= null;
        try {
             result = compareText(params[0].referenceText, params[0].speechText);

        } catch (Exception e) {
        e.printStackTrace();
        }
        return result;

    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);

    }

    //Test search method
        public String [] deletePunctuationFromText(String text){

            String[] splittedText = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            return splittedText;
        }

        private ArrayList<String> stringArrayToList(String[] text){

            ArrayList<String> alText = new ArrayList<String>(Arrays.asList(text));
           // alText = Arrays.asList(text);

            //da array to arraylist

            return alText;
        }


        public String deleteSpacesFromBeginningAndBottom(String inputString){

            inputString=inputString.replaceAll("^\\s+", "");
            inputString=inputString.replaceAll("\\s+$", "");
            inputString=inputString.trim();
            return inputString;
        }

        public String[] arrayListToString(ArrayList<String> input){

            //This method converts arrayList to array of strings
            Object[] objNames = input.toArray();

            //Second Step: convert Object array to String array

            String[] output = Arrays.copyOf(objNames, objNames.length, String[].class);
            return output;
        }

        public boolean search(String word, int mask, String[] referenceText, int index){

            //This method search the word in the given mask.

            if(mask <= 3)
                mask = 3;
            if(mask>referenceText.length)
                mask = referenceText.length-1;

            //Parte iniziale dell'array
            if (index - mask < 0){
                for (int i = 0; i <= mask; i++){
                    if (i >= referenceText.length) {
                        break;
                    }
                    else if (word.equals(referenceText[i]))
                        return true;

                }
            }
            //Parte finale dell'array
            else if (index + mask >= Array.getLength(referenceText)){
                for (int j=Array.getLength(referenceText)-1; j>=Array.getLength(referenceText)-mask-1; j--){
                    if(word.equals(referenceText[j]))
                        return true;
                }
            }
            //Parte centrale dell'array
            else {
                for (int i=index-mask; i<=index+mask; i++){
                    if(word.equals(referenceText[i]))
                        return true;
                }
            }

            return false;

        }

        public ArrayList<String> compareText (String referenceText, String speechText){

            ArrayList<String> wrongWords = new ArrayList<>();

            referenceText = deleteSpacesFromBeginningAndBottom(referenceText);
            speechText = deleteSpacesFromBeginningAndBottom(speechText);

            String[] splittedText = deletePunctuationFromText(referenceText);
            String[] splittedSpeechText = deletePunctuationFromText(speechText);

            int speechSize, referenceSize;
            speechSize = Array.getLength(splittedSpeechText);
            referenceSize = Array.getLength(splittedText);

            int mask = Math.abs(speechSize - referenceSize);

            for (int i = 0; i<speechSize; i++){
                if(!search(splittedSpeechText[i], mask, splittedText, i))
                    wrongWords.add(splittedSpeechText[i]);
            }

            for (int i = 0; i<referenceSize; i++){
                if(!search(splittedText[i], mask, splittedSpeechText, i))
                    wrongWords.add(splittedText[i]);
            }

            return wrongWords;
            //Ritorniamo la lista delle parole errate e mancanti
        }

    public ArrayList<String> compareText (String[] referenceText, String[] speechText){


        ArrayList<String> wrongWords = new ArrayList<>();


        int speechSize, referenceSize;
        speechSize = Array.getLength(referenceText);
        referenceSize = Array.getLength(speechText);

        int mask = Math.abs(speechSize - referenceSize);

        for (int i = 0; i<referenceSize; i++){
            if(!search(referenceText[i], mask, speechText, i))
                wrongWords.add(referenceText[i]);
        }

        for (int i = 0; i<speechSize; i++){
            if(!search(speechText[i], mask, referenceText, i))
                wrongWords.add(speechText[i]);
        }


        return wrongWords;
        //Ritorniamo la lista delle parole errate e mancanti
    }





}
