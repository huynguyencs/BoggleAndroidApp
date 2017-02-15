package com.example.mymac.boggle;

import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * Created by brettchafin on 2/9/17.
 */

public class Dictionary extends AppCompatActivity {
    Hashtable <Integer, ArrayList<String>> wordDictionary;

    //constructor
    Dictionary(InputStream in) {//K-V
        wordDictionary = new Hashtable <Integer, ArrayList<String>> ();
        createWordDictionary(in);
    }

    private boolean createWordDictionary(InputStream inputS) {
        BufferedReader textReader = new BufferedReader(new InputStreamReader(inputS));
        //StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = textReader.readLine()) != null) {
                ArrayList <String> tempList = null;
                int key = generateHashKey(line);

                if (wordDictionary.containsKey(key)) {
                    tempList = wordDictionary.get(key);
                    if(tempList == null)
                        tempList = new ArrayList<String>();
                    tempList.add(line);
                } else {
                    tempList = new ArrayList<String>();
                    tempList.add(line);
                }
                wordDictionary.put(key,tempList);
            }
            inputS.close();
            //sb.toString();
        }catch (Exception e) { }
        return true;
    }

    public String[] findValidWords(String[] possibleString) {
        ArrayList<String> possibleWords = new ArrayList<String>();
        if(possibleString == null) return null;
        for(int i = 0; i < possibleString.length; ++i){
            if(isValid(possibleString[i]))
                possibleWords.add(possibleString[i]);
        }
        Collections.sort(possibleWords, String.CASE_INSENSITIVE_ORDER);
        return possibleWords.toArray(new String[0]);
    }

    public boolean isValid(String word) {
        int key = generateHashKey(word);
        if(wordDictionary.containsKey(key)){
            ArrayList <String> tempList = wordDictionary.get(key);
            if(tempList.contains(word))
                return true;
        }
        return false;
    }

    private int generateHashKey(String a_word){
        if (a_word.length() >= 3 && a_word.length() <= 4){
            return 1;
        }
        else if (a_word.length() == 5){
            return 2;
        }
        else if (a_word.length() == 6){
            return 3;
        }
        else if (a_word.length() == 7){
            return 5;
        }
        return 10;
    }
}
