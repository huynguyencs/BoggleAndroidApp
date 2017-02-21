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
    Hashtable <String, String> wordDictionary;
    String board[][] = new String[4][4];
    static ArrayList<String> possibleWords;

    //constructor
    Dictionary(InputStream in, Die[] dice) {//K-V
        wordDictionary = new Hashtable <String, String> ();
        createWordDictionary(in);

        possibleWords = new ArrayList<String>();

        //set up the board with correct letters
        int count = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                board[i][j] = dice[count].topLetter;
                count++;
            }
        }
    }

    private boolean createWordDictionary(InputStream inputS) {
        BufferedReader textReader = new BufferedReader(new InputStreamReader(inputS));
        //StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = textReader.readLine()) != null) {
                if (!wordDictionary.containsKey(line)) {
                    wordDictionary.put(line, line);
                }
            }
            inputS.close();
            //sb.toString();
        }catch (Exception e) { }
        return true;
    }

    public void findWords(String board[][], boolean visited[][], int i, int j, String str) {

        //mark current die as visited and add the letter to the string
        visited[i][j] = true;
        str += board[i][j];

        //if this creates a new word, add it to possible words
        if(isValid(str)){
            possibleWords.add(str);
        }

        //traverse all the dice of the board
        for(int row = i-1; row <= i+1 && row < 4; row++){
            for(int col = j-1; col <= j+1 && col < 4; col++){
                if(row >= 0 && col >= 0 && !visited[row][col]){
                    findWords(board, visited, row, col, str);
                }
            }
        }

        //Erase the recently added char and marked die as unvisited
        str = str.substring(0, str.length()-1);
        visited[i][j] = false;

    }


    public String[] findPossibleWords() {
        boolean visited[][] = new boolean[4][4];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                visited[i][j] = false;
            }
        }

        String str = "";

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                findWords(board, visited, i, j, str);
            }
        }


        return possibleWords.toArray(new String[0]);
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
        if(wordDictionary.containsKey(word)){
            return true;
        }
        return false;
    }
}

/*

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
 */