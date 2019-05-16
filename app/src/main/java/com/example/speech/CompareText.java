package com.example.speech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareText {

        private String deletePunctuationFromText(String text){

            String[] splittedText = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            return text;
        }

        private List<String> StringArrayToList(String[] text){

            List<String> alText = new ArrayList<String>();
            alText = Arrays.asList(text);

            return alText;
        }

        private String CompareStringAndArrayList (String referenceText, ArrayList<String> speechText){

            String splittedText = deletePunctuationFromText(referenceText);

            return "-1";
        }


}
