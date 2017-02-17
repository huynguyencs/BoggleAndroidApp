package com.example.mymac.boggle;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import java.util.Arrays;
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

public class MainBoard extends AppCompatActivity implements View.OnTouchListener{
    public TextView timerText;
    public TextView user_score;
    private CountDownTimer timer;

    public Die[] dice;

    private String[] possibleWords;
    private ArrayList<String> wordsFound;
    private Dictionary dictionary;
    private StringBuilder selectingWord = new StringBuilder();

    private int userScore;
    private boolean[] flag = new boolean[16];
    private GestureDetector gestureDetector;

    int prevRow = 0;
    int prevCol = 0;
    int curRow;
    int curCol;

    Button p1_button; Button p2_button; Button p3_button; Button p4_button; Button p5_button; Button p6_button;
    Button p7_button; Button p8_button; Button p9_button; Button p10_button; Button p11_button; Button p12_button;
    Button p13_button; Button p14_button; Button p15_button; Button p16_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);
        try {
            InputStream inputS = getResources().openRawResource(R.raw.dictionary);
            dictionary = new Dictionary(inputS);
        } catch (Exception e) {
        }


        //fully create a new Board
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        try {
            newBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //attach new board to UI Buttons
        buttonCreation();
        resetBtnBackground();
        //get possibleWords from dictionary


        timerText = (TextView) this.findViewById(R.id.timer);
        timer = new countDownTimer(60 * 1000, 1 * 1000);
        timer.start();


        user_score = (TextView) this.findViewById(R.id.score);

        user_score.setText("Your score: " + userScore);
    }



    //Sets up the board with a number of helper functions
    private boolean newBoard() throws IOException {
        while(true) {
            userScore = 0;
            wordsFound = new ArrayList<String>();
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
        //possibleWords = englishWords;

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

    private void endGame(){
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
            endGame();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            long total_seconds = millisUntilFinished / 1000;
            long seconds = total_seconds % 60;
            long minutes = total_seconds / 60;
            timerText.setText("TIME LEFT: " + minutes + ":" + seconds);
        }
    }

    private void buttonCreation() {
        p1_button = (Button)findViewById(R.id.button1);
        p1_button.setText((dice[0].topLetter));
        p1_button.setOnTouchListener(this);
        p2_button = (Button)findViewById(R.id.button2);
        p2_button.setText((dice[1].topLetter));
        p2_button.setOnTouchListener(this);
        p3_button = (Button)findViewById(R.id.button3);
        p3_button.setText((dice[2].topLetter));
        p3_button.setOnTouchListener(this);
        p4_button = (Button)findViewById(R.id.button4);
        p4_button.setText((dice[3].topLetter));
        p4_button.setOnTouchListener(this);
        p5_button = (Button)findViewById(R.id.button5);
        p5_button.setText((dice[4].topLetter));
        p5_button.setOnTouchListener(this);
        p6_button = (Button)findViewById(R.id.button6);
        p6_button.setText((dice[5].topLetter));
        p6_button.setOnTouchListener(this);
        p7_button = (Button)findViewById(R.id.button7);
        p7_button.setText((dice[6].topLetter));
        p7_button.setOnTouchListener(this);
        p8_button = (Button)findViewById(R.id.button8);
        p8_button.setText((dice[7].topLetter));
        p8_button.setOnTouchListener(this);
        p9_button = (Button)findViewById(R.id.button9);
        p9_button.setText((dice[8].topLetter));
        p9_button.setOnTouchListener(this);
        p10_button = (Button)findViewById(R.id.button10);
        p10_button.setText((dice[9].topLetter));
        p10_button.setOnTouchListener(this);
        p11_button = (Button)findViewById(R.id.button11);
        p11_button.setText((dice[10].topLetter));
        p11_button.setOnTouchListener(this);
        p12_button = (Button)findViewById(R.id.button12);
        p12_button.setText((dice[11].topLetter));
        p12_button.setOnTouchListener(this);
        p13_button = (Button)findViewById(R.id.button13);
        p13_button.setText((dice[12].topLetter));
        p13_button.setOnTouchListener(this);
        p14_button = (Button)findViewById(R.id.button14);
        p14_button.setText((dice[13].topLetter));
        p14_button.setOnTouchListener(this);
        p15_button = (Button)findViewById(R.id.button15);
        p15_button.setText((dice[14].topLetter));
        p15_button.setOnTouchListener(this);
        p16_button = (Button)findViewById(R.id.button16);
        p16_button.setText((dice[15].topLetter));
        p16_button.setOnTouchListener(this);

        Button submitBtn = (Button)findViewById(R.id.submit);
        submitBtn.setOnTouchListener(this);
    }

    private void resetBtnBackground(){
        p1_button.getBackground().clearColorFilter();
        p2_button.getBackground().clearColorFilter();
        p3_button.getBackground().clearColorFilter();
        p4_button.getBackground().clearColorFilter();
        p5_button.getBackground().clearColorFilter();
        p6_button.getBackground().clearColorFilter();
        p7_button.getBackground().clearColorFilter();
        p8_button.getBackground().clearColorFilter();
        p9_button.getBackground().clearColorFilter();
        p10_button.getBackground().clearColorFilter();
        p11_button.getBackground().clearColorFilter();
        p12_button.getBackground().clearColorFilter();
        p13_button.getBackground().clearColorFilter();
        p14_button.getBackground().clearColorFilter();
        p15_button.getBackground().clearColorFilter();
        p16_button.getBackground().clearColorFilter();
    }

    public void updateTextView() {
        TextView textView = (TextView) findViewById(R.id.score);
        textView.setText("Your score: " + userScore);
        textView.setTextColor( getRandomColor());
    }

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

    }

    @Override
    public boolean onTouch(View v, MotionEvent arg1) {
        // default method for handling onClick Events.

        if (gestureDetector.onTouchEvent(arg1)) {
            switch (v.getId()) {
                case R.id.submit:
                    System.out.println("Checking user's words to dictionary.");
                    //if user's submitted word is valid
                    //if(Arrays.asList(possibleWords).contains(selectingWord.toString())){
                    if(dictionary.isValid(selectingWord.toString())){
                        //check if it's already in the list of found word
                        if(wordsFound.contains(selectingWord.toString())){
                            Toast.makeText(getApplicationContext(), "THIS WORD HAS ALREADY BEEN SUBMITTED!!!", Toast.LENGTH_SHORT).show();
                            resetBtnBackground();
                        }
                        else{
                            int pts = 0;
                            wordsFound.add(selectingWord.toString());
                            if (selectingWord.length() >= 3 && selectingWord.length() <= 4){
                                pts = 1;
                            }else if (selectingWord.length() == 5){
                                pts = 2;
                            }else if (selectingWord.length() == 6){
                                pts = 3;
                            }else if (selectingWord.length() == 7){
                                pts = 5;
                            }else if (selectingWord.length() >= 8) {
                                pts = 10;
                            }
                            userScore += pts;
                            CharSequence text = "YOU EARNED " + pts + " POINTS!!!";
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                            updateTextView();
                            resetBtnBackground();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "INVALID WORD!!!", Toast.LENGTH_SHORT).show();
                        resetBtnBackground();
                    }
                    selectingWord.delete(0, selectingWord.length());
                    flag = new boolean[16];
                    break;

                case R.id.button1:
                    ButtonHandler(v, 0);
                    break;
                case R.id.button2:
                    ButtonHandler(v, 1);
                    break;
                case R.id.button3:
                    ButtonHandler(v, 2);
                    break;
                case R.id.button4:
                    ButtonHandler(v, 3);
                    break;
                case R.id.button5:
                    ButtonHandler(v, 4);
                    break;
                case R.id.button6:
                    ButtonHandler(v, 5);
                    break;
                case R.id.button7:
                    ButtonHandler(v, 6);
                    break;
                case R.id.button8:
                    ButtonHandler(v, 7);
                    break;
                case R.id.button9:
                    ButtonHandler(v, 8);
                    break;
                case R.id.button10:
                    ButtonHandler(v, 9);
                    break;
                case R.id.button11:
                    ButtonHandler(v, 10);
                    break;
                case R.id.button12:
                    ButtonHandler(v, 11);
                    break;
                case R.id.button13:
                    ButtonHandler(v, 12);
                    break;
                case R.id.button14:
                    ButtonHandler(v, 13);
                    break;
                case R.id.button15:
                    ButtonHandler(v, 14);
                    break;
                case R.id.button16:
                    ButtonHandler(v, 15);
                    break;
                default:
                    break;
            }
            System.out.println(selectingWord);
            return false;
        } else {
            //TODO: Finger Sliding
        }

        return false;
    }

    private void ButtonHandler(View v, int dieNumber) {
        curRow = dieNumber/4;
        curCol = dieNumber%4;
        if (flag[dieNumber] == true) {
            selectingWord.delete(0, selectingWord.length());
            flag = new boolean[16];
            resetBtnBackground();
            Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
        } else {
            if (selectingWord.length() == 0) {
                prevRow = curRow;
                prevCol = curCol;
                selectingWord.append(((Button)v).getText().toString());
                flag[dieNumber] = true;
                v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            } else {
                System.out.println(prevCol + " " + prevRow + " " + curRow + " " + curCol);
                if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                        && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                    selectingWord.append(((Button)v).getText().toString());
                    flag[dieNumber] = true;
                    prevRow = curRow;
                    prevCol = curCol;
                    v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                } else {
                    selectingWord.delete(0, selectingWord.length());
                    flag = new boolean[16];
                    resetBtnBackground();
                    Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}




