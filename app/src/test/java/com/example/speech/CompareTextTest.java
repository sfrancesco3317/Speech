package com.example.speech;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CompareTextTest {

    //Test search method
    @Test
    public void testTrueIndexZeroMaskZero () throws Exception {

        String word = "test";
        int mask = 2;
        int index = 0;
        String [] referenceTest = {"test", "no", "no", "no"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(true, output);
    }

    @Test
    public void testTrueIndexZeroMask2 () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 0;
        String [] referenceTest = {"test", "no", "no", "no"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(true, output);
    }

    @Test
    public void testFalseIndexZero () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 0;
        String [] referenceTest = {"no", "no", "no"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(false, output);
    }

    @Test
    public void testTrueLastIndexMask0 () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 4;
        String [] referenceTest = {"no", "no", "no","no", "test"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(true, output);
    }

    @Test
    public void testTrueCentralIndex () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 0;
        String [] referenceTest = {"no", "no", "test", "no", "NO"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(true, output);
    }
    @Test
    public void testTrueLastIndex () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 4;
        String [] referenceTest = {"no", "no", "test", "no", "NO"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(true, output);
    }

    @Test
    public void testLargerArray () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 8;
        String [] referenceTest = {"no", "no", "no", "no,", "no", "test", "no", "NO"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(true, output);
    }

    @Test
    public void testFalseLargeArray () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 0;
        String [] referenceTest = {"no", "no", "no", "no,", "no", "test", "no", "NO"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(false, output);
    }

    @Test
    public void testTrueLargerArrayCentralIndex () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 4;
        String [] referenceTest = {"no", "no", "no", "no,", "no", "test", "no", "NO"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(true, output);
    }

    @Test
    public void testOutOfLengthIndex () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 15;
        String [] referenceTest = {"no", "no", "no", "no,", "no", "test", "no", "NO"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(true, output);
    }



    //test arrayListToString method
    @Test
    public void testTrue () throws Exception {


        ArrayList<String> inputArray = new ArrayList<>();
        inputArray.add("Nel");
        inputArray.add("mezzo");
        inputArray.add("del");
        inputArray.add("cammin");
        String message = "Test arrayToString";

        String expected[] = {"Nel", "mezzo", "del", "cammin"};
        CompareText test = new CompareText();
        String[] input = test.arrayListToString(inputArray);

        assertArrayEquals("True?", expected, input);

    }

    @Test
    public void testComma () throws Exception {


        ArrayList<String> inputArray = new ArrayList<>();
        inputArray.add("Nel,");
        inputArray.add("mezzo");
        inputArray.add("del");
        inputArray.add("cammin");

        String expected[] = {"Nel,", "mezzo", "del", "cammin"};
        CompareText test = new CompareText();
        String[] input = test.arrayListToString(inputArray);

        assertArrayEquals("True?", expected, input);

    }


    //Test deletePunctuation
    @Test
    public void testDeleteTrue () throws Exception {


        String inputString = "Test, test, test!";

        String expected[] = {"test", "test", "test"};
        CompareText test = new CompareText();
        String[] input = test.deletePunctuationFromText(inputString);

        assertArrayEquals("True?", expected, input);

    }

    //fulltest

    @Test
    public void fullTestZeroErrors () throws Exception {


        ArrayList<String> inputArray = new ArrayList<>();
        inputArray.add("nel");
        inputArray.add("mezzo");
        inputArray.add("del");
        inputArray.add("cammin");

        String inputString = "Nel mezzo del cammin";


        CompareText test = new CompareText();
        ArrayList<String> output = test.compareText(inputString, inputArray);

        assertEquals(0, output.size());

    }
    @Test
    public void fullTest1Errors () throws Exception {


        ArrayList<String> inputArray = new ArrayList<>();
        inputArray.add("nel");
        inputArray.add("pezzo");
        inputArray.add("del");
        inputArray.add("cammin");

        String inputString = "Nel mezzo del cammin";

        CompareText test = new CompareText();
        ArrayList<String> output = test.compareText(inputString, inputArray);



        assertEquals(1, output.size());

    }

    @Test
    public void fullTestMaskAndPunctuation () throws Exception {


        ArrayList<String> inputArray = new ArrayList<>();
        inputArray.add("nel");
        inputArray.add("pezzo");
        inputArray.add("mezzo");
        inputArray.add("del");
        inputArray.add("cammin");

        String inputString = "Nel, mezzo. del cammin";


        CompareText test = new CompareText();
        ArrayList<String> output = test.compareText(inputString, inputArray);

        assertEquals(1, output.size());

    }

    @Test
    public void fullTestErrors () throws Exception {


        ArrayList<String> inputArray = new ArrayList<>();
        inputArray.add("nel");
        inputArray.add("mezzo");
        inputArray.add("del");
        inputArray.add("cammin");

        String inputString = "Nel, mezzo. del cammin, prova prova prova";


        CompareText test = new CompareText();
        ArrayList<String> output = test.compareText(inputString, inputArray);

        assertEquals(3, output.size());

    }

    @Test
    public void fullTest() throws Exception {


        ArrayList<String> inputArray = new ArrayList<>();
        inputArray.add("nel");
        inputArray.add("pezzo");
        inputArray.add("mezzo");
        inputArray.add("del");
        inputArray.add("cammin");
        //inputArray.add("test");

        String inputString = "Nel, mezzo. del cammin, prova prova,";

        CompareText test = new CompareText();
        ArrayList<String> output = test.compareText(inputString, inputArray);

        assertEquals(3, output.size());

    }







}