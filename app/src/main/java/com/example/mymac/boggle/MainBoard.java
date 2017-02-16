package com.example.mymac.boggle;


import android.content.Intent;
import android.content.res.AssetManager;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.util.Random;
import java.util.Scanner;
import java.io.File;

import static java.lang.System.in;


public class MainBoard extends AppCompatActivity implements View.OnClickListener{
    public TextView timerText;
    public TextView user_score;
    private CountDownTimer timer;


    private String[] possibleWords;
    private String[] wordsFound;
    private Dictionary dictionary;
    private StringBuilder selectingWord = new StringBuilder();
    private boolean [] value = new boolean [16];

    //private DiceGraph graph = new DiceGraph();

    public Die[] dice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);
        try {
            InputStream inputS = getResources().openRawResource(R.raw.dictionary);
            dictionary = new Dictionary(inputS);
        } catch (Exception e) { }


        //fully create a new Board
        try {
            newBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //attach new board to UI Buttons
        buttonCreation();

        //get possibleWords from dictionary


        timerText = (TextView) this.findViewById(R.id.timer);
        timer = new countDownTimer(180 * 1000, 1 * 1000);
        timer.start();

        user_score = (TextView) this.findViewById(R.id.score);
        user_score.setText("Your score: " + 0);
    }

    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..
        switch (v.getId()) {
            case R.id.submit:
                System.out.println("Checking user's words to dictionary.");
                break;

            default:
                //array button id 0-15

                selectingWord.append(((Button)v).getText().toString());
                System.out.println(selectingWord);
                break;
        }

    }

    //Sets up the board with a number of helper functions
    private boolean newBoard() throws IOException {
        while(true) {
            wordsFound = new String[0];
            randomDice();
            DiceGraph diceGraph = new DiceGraph();
            String[] wordPossiblilities = possiblePaths();
            possibleWords = dictionary.findValidWords(wordPossiblilities);
            System.out.println(possibleWords);
            //if(!validateBoard(possibleWords))
            //        continue;
            //if (validateBoard(wordPossiblilities)) continue;
            return true;
        }
    }


    /* Takes a list of word possibilities and checks with the Dictionary to get
    a list of valid words back for the board. Returns false if the board
    doesnt meet the difficulty criteria */
    private boolean validateBoard(String[] wordPossibilities) {
        String[] englishWords = dictionary.findValidWords(wordPossibilities);

        //todo Add difficulty levels
        if(englishWords == null) return false;
        //add these words to the list of possible words for the board
        possibleWords = englishWords;

        //success
        return true;
    }


    //creates a new 2d array of Die objects
    private boolean randomDice() {
        dice = new Die[16];
        for(int i = 0; i < dice.length ; i++) {
            dice[i] = new Die(i);
        }
        return true;
    }

    private void endGame(String[] possibleWords, String[] wordsFound, int userScore){
        Intent i = new Intent(MainBoard.this, Results.class);
        i.putExtra("possibleWords", possibleWords);
        i.putExtra("wordsFound", wordsFound);
        i.putExtra("userScore", userScore);
        startActivity(i);
    }

    //todo
    private String[] possiblePaths() throws IOException{

        List<String> generatedWords = new LinkedList<>();


        Scanner filescan = new Scanner(getAssets().open("possible_paths.txt"));
        while (filescan.hasNext()) {

            String word = "";
            String line = filescan.nextLine();
            String parsedLine = line.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\,", "");
            for (String node: parsedLine.split(" ")) {
                String letter = dice[Integer.parseInt(node) - 1].topLetter;
                word += letter;
            }
            generatedWords.add(word);
        }
        System.out.println(generatedWords.toString());
        return null;
    }

    protected char rndChar () {
        Random rnd = new Random();
        return (char) (65 + rnd.nextInt(26));
    }

    public class countDownTimer extends CountDownTimer {
        public countDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onFinish() {
            timerText.setText("TIME'S UP!");
            String[] possibleWords = {"test", "one", "two", "three"};
            String[] wordsFound = {"one"};
            int user_score = 3242;
            endGame(possibleWords, wordsFound, user_score);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            timerText.setText("TIME LEFT: " + millisUntilFinished / 1000);
        }
    }

    private void buttonCreation() {
        Button p1_button = (Button)findViewById(R.id.button1);
        p1_button.setText((dice[0].topLetter));
        p1_button.setOnClickListener(this);

        Button p2_button = (Button)findViewById(R.id.button2);
        p2_button.setText((dice[1].topLetter));
        p2_button.setOnClickListener(this);

        Button p3_button = (Button)findViewById(R.id.button3);
        p3_button.setText((dice[2].topLetter));
        p3_button.setOnClickListener(this);

        Button p4_button = (Button)findViewById(R.id.button4);
        p4_button.setText((dice[3].topLetter));
        p4_button.setOnClickListener(this);

        Button p5_button = (Button)findViewById(R.id.button5);
        p5_button.setText((dice[4].topLetter));
        p5_button.setOnClickListener(this);

        Button p6_button = (Button)findViewById(R.id.button6);
        p6_button.setText((dice[5].topLetter));
        p6_button.setOnClickListener(this);

        Button p7_button = (Button)findViewById(R.id.button7);
        p7_button.setText((dice[6].topLetter));
        p7_button.setOnClickListener(this);

        Button p8_button = (Button)findViewById(R.id.button8);
        p8_button.setText((dice[7].topLetter));
        p8_button.setOnClickListener(this);

        Button p9_button = (Button)findViewById(R.id.button9);
        p9_button.setText((dice[8].topLetter));
        p9_button.setOnClickListener(this);

        Button p10_button = (Button)findViewById(R.id.button10);
        p10_button.setText((dice[9].topLetter));
        p10_button.setOnClickListener(this);

        Button p11_button = (Button)findViewById(R.id.button11);
        p11_button.setText((dice[10].topLetter));
        p11_button.setOnClickListener(this);

        Button p12_button = (Button)findViewById(R.id.button12);
        p12_button.setText((dice[11].topLetter));
        p12_button.setOnClickListener(this);

        Button p13_button = (Button)findViewById(R.id.button13);
        p13_button.setText((dice[12].topLetter));
        p13_button.setOnClickListener(this);

        Button p14_button = (Button)findViewById(R.id.button14);
        p14_button.setText((dice[13].topLetter));
        p14_button.setOnClickListener(this);

        Button p15_button = (Button)findViewById(R.id.button15);
        p15_button.setText((dice[14].topLetter));
        p15_button.setOnClickListener(this);

        Button p16_button = (Button)findViewById(R.id.button16);
        p16_button.setText((dice[15].topLetter));
        p16_button.setOnClickListener(this);

        Button submitBtn = (Button)findViewById(R.id.submit);
        submitBtn.setOnClickListener(this);

    }
}




