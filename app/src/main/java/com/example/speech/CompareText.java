package com.example.speech;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareText {

        private String [] deletePunctuationFromText(String text){

            String[] splittedText = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            return splittedText;
        }

        private ArrayList<String> stringArrayToList(String[] text){

            ArrayList<String> alText = new ArrayList<String>(Arrays.asList(text));
           // alText = Arrays.asList(text);

            //da array to arraylist

            return alText;
        }

        private String[] arrayListToString(ArrayList<String> input){

            //This method converts arrayList to array of strings

            String[] output = input.toArray(new String[input.size()]);
            return output;
        }

        private boolean search(String word, int mask, String[] referenceText, int index){

            //This method search the word in the given mask.

            if(mask <= 2)
                mask = 2;
            if (mask <= index){
                for (int i = 0; i < mask; i++){
                    if(word == referenceText[i])
                        return true;
                }
            }

            if (index + mask >= Array.getLength(referenceText)){
                for (int j=index; j>=index-mask; j--){
                    if(word == referenceText[j])
                        return true;
                }
            }

            else {
                for (int i=index-mask; i<=index+mask; i++){
                    if(word == referenceText[i])
                        return true;
                }
            }
            return false;
        }

        private float compareText (String referenceText, ArrayList<String> speechText){

            int errorCount=0;
            float evaluation = 0;

            String[] splittedText = deletePunctuationFromText(referenceText);
            //ArrayList<String> referenceTextArray= new ArrayList<>();
            //referenceTextArray= stringArrayToList(splittedText);

            //Converts speechText to string array,
            String [] speechArray = arrayListToString(speechText);
            //Now we have both speech and reference in array of string type

            int speechSize, referenceSize;
            speechSize = Array.getLength(speechArray);
            referenceSize = Array.getLength(splittedText);

            int mask = Math.abs(speechSize - referenceSize);

            for (int i = 0; i<speechSize; i++){
                if(!search(splittedText[i], mask, splittedText, i))
                    errorCount++;
            }

            evaluation = (float)errorCount/(float)referenceSize;

            if(evaluation>=0 && evaluation <=1)
                return(evaluation);

            return -1;
        }


}
