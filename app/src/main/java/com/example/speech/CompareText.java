package com.example.speech;

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

        private String compareStringAndArrayList (String referenceText, ArrayList<String> speechText){

            String[] splittedText = deletePunctuationFromText(referenceText);
            ArrayList<String> referenceTextArray= new ArrayList<>();
            referenceTextArray= stringArrayToList(splittedText);

            return "-1";
        }


}
