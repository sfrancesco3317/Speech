package com.example.speech;

import org.junit.Test;

import static org.junit.Assert.*;

public class CompareTextTest {
    @Test
    public void search () throws Exception {

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
    public void search2 () throws Exception {

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
    public void search3 () throws Exception {

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
    public void search4 () throws Exception {

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
    public void search5 () throws Exception {

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
    public void search6 () throws Exception {

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
    public void search7 () throws Exception {

        String word = "test";
        int mask = 0;
        int index = 8;
        String [] referenceTest = {"no", "no", "no", "no,", "no", "test", "no", "NO"};

        CompareText test = new CompareText();
        boolean output = test.search(word, mask, referenceTest, index);
        boolean expextedOut = true;

        assertEquals(true, output);
    }


}