package com.example.mymac.boggle;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Random;

import static java.lang.System.in;


public class MainBoard extends AppCompatActivity implements View.OnClickListener{
    public TextView timerText;
    public TextView user_score;
    private CountDownTimer timer;


    public Die[] Dice;
    private String[] possibleWords;
    private String[] wordsFound;
    private Dictionary dictionary;
    private StringBuilder selectingWord = new StringBuilder();
    private boolean [] value = new boolean [16];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);
        try {
            InputStream inputS = getResources().openRawResource(R.raw.dictionary);
            dictionary = new Dictionary(inputS);
        } catch (Exception e) { }

        //fully create a new Board
        newBoard();

        //attach new board to UI Buttons
        buttonCreation();

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
    private boolean newBoard() {
        while(true) {
            wordsFound = new String[0];
            randomDice();
            String[] wordPossiblilities = possiblePaths();
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
        if(englishWords.length < 2) return false;

        //add these words to the list of possible words for the board
        possibleWords = englishWords;

        //success
        return true;
    }


    //creates a new 2d array of Die objects
    private boolean randomDice(){
        Dice = new Die[16];
        for(int i = 0; i < 16; i++) {
            Dice[i] = new Die();
        }
        return true;
    }

    //todo
    private String[] possiblePaths(){
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
        }
        @Override
        public void onTick(long millisUntilFinished) {
            timerText.setText("TIME LEFT: " + millisUntilFinished / 1000);
        }
    }

    private void buttonCreation() {
        Button p1_button = (Button)findViewById(R.id.button1);
        p1_button.setText(Character.toString(Dice[0].topLetter));
        p1_button.setOnClickListener(this);

        Button p2_button = (Button)findViewById(R.id.button2);
        p2_button.setText(Character.toString(Dice[1].topLetter));
        p2_button.setOnClickListener(this);

        Button p3_button = (Button)findViewById(R.id.button3);
        p3_button.setText(Character.toString(Dice[2].topLetter));
        p3_button.setOnClickListener(this);

        Button p4_button = (Button)findViewById(R.id.button4);
        p4_button.setText(Character.toString(Dice[3].topLetter));
        p4_button.setOnClickListener(this);

        Button p5_button = (Button)findViewById(R.id.button5);
        p5_button.setText(Character.toString(Dice[4].topLetter));
        p5_button.setOnClickListener(this);

        Button p6_button = (Button)findViewById(R.id.button6);
        p6_button.setText(Character.toString(Dice[5].topLetter));
        p6_button.setOnClickListener(this);

        Button p7_button = (Button)findViewById(R.id.button7);
        p7_button.setText(Character.toString(Dice[6].topLetter));
        p7_button.setOnClickListener(this);

        Button p8_button = (Button)findViewById(R.id.button8);
        p8_button.setText(Character.toString(Dice[7].topLetter));
        p8_button.setOnClickListener(this);

        Button p9_button = (Button)findViewById(R.id.button9);
        p9_button.setText(Character.toString(Dice[8].topLetter));
        p9_button.setOnClickListener(this);

        Button p10_button = (Button)findViewById(R.id.button10);
        p10_button.setText(Character.toString(Dice[9].topLetter));
        p10_button.setOnClickListener(this);

        Button p11_button = (Button)findViewById(R.id.button11);
        p11_button.setText(Character.toString(Dice[10].topLetter));
        p11_button.setOnClickListener(this);

        Button p12_button = (Button)findViewById(R.id.button12);
        p12_button.setText(Character.toString(Dice[11].topLetter));
        p12_button.setOnClickListener(this);

        Button p13_button = (Button)findViewById(R.id.button13);
        p13_button.setText(Character.toString(Dice[12].topLetter));
        p13_button.setOnClickListener(this);

        Button p14_button = (Button)findViewById(R.id.button14);
        p14_button.setText(Character.toString(Dice[13].topLetter));
        p14_button.setOnClickListener(this);

        Button p15_button = (Button)findViewById(R.id.button15);
        p15_button.setText(Character.toString(Dice[14].topLetter));
        p15_button.setOnClickListener(this);

        Button p16_button = (Button)findViewById(R.id.button16);
        p16_button.setText(Character.toString(Dice[15].topLetter));
        p16_button.setOnClickListener(this);

        Button submitBtn = (Button)findViewById(R.id.submit);
        submitBtn.setOnClickListener(this);
    }
}




