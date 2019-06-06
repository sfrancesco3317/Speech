package com.example.speech;

import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareText{


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
                for (int i = 0; i < mask; i++){
                    if(word.equals(referenceText[i]))
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

            int errorCount=0;
            float evaluation = 0;
            ArrayList<String> wrongWords = new ArrayList<>();

            String[] splittedText = deletePunctuationFromText(referenceText);
            String[] splittedSpeechText = deletePunctuationFromText(speechText);
            //ArrayList<String> referenceTextArray= new ArrayList<>();
            //referenceTextArray= stringArrayToList(splittedText);


            int speechSize, referenceSize;
            speechSize = Array.getLength(splittedSpeechText);
            referenceSize = Array.getLength(splittedText);

            int mask = Math.abs(speechSize - referenceSize);

            for (int i = 0; i<speechSize; i++){
                if(!search(splittedSpeechText[i], mask, splittedText, i))
                    wrongWords.add(splittedSpeechText[i]);
            }

            if(referenceSize>speechSize) {
                for (int i = speechSize - 1; i < referenceSize; i++) {           //Aggiunge le parole mancanti alla fine del testo di riferimento
                    if (!search(splittedText[i], mask, splittedSpeechText, i))
                        wrongWords.add(splittedText[i]);
                }
            }

            return wrongWords;
            //Ritorniamo la lista delle parole errate e mancanti
        }





}
