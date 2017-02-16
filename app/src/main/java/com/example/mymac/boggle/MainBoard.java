package com.example.mymac.boggle;

import android.graphics.Color;
import android.graphics.PorterDuff;
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

public class MainBoard extends AppCompatActivity implements View.OnClickListener{
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

    int prevRow = 0;
    int prevCol = 0;

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
            long total_seconds = millisUntilFinished / 1000;
            long seconds = total_seconds % 60;
            long minutes = total_seconds / 60;
            timerText.setText("TIME LEFT: " + minutes + ":" + seconds);
        }
    }

    private void buttonCreation() {

        p1_button = (Button)findViewById(R.id.button1);
        p1_button.setText((dice[0].topLetter));
        p1_button.setOnClickListener(this);
        p2_button = (Button)findViewById(R.id.button2);
        p2_button.setText((dice[1].topLetter));
        p2_button.setOnClickListener(this);
        p3_button = (Button)findViewById(R.id.button3);
        p3_button.setText((dice[2].topLetter));
        p3_button.setOnClickListener(this);
        p4_button = (Button)findViewById(R.id.button4);
        p4_button.setText((dice[3].topLetter));
        p4_button.setOnClickListener(this);
        p5_button = (Button)findViewById(R.id.button5);
        p5_button.setText((dice[4].topLetter));
        p5_button.setOnClickListener(this);
        p6_button = (Button)findViewById(R.id.button6);
        p6_button.setText((dice[5].topLetter));
        p6_button.setOnClickListener(this);
        p7_button = (Button)findViewById(R.id.button7);
        p7_button.setText((dice[6].topLetter));
        p7_button.setOnClickListener(this);
        p8_button = (Button)findViewById(R.id.button8);
        p8_button.setText((dice[7].topLetter));
        p8_button.setOnClickListener(this);
        p9_button = (Button)findViewById(R.id.button9);
        p9_button.setText((dice[8].topLetter));
        p9_button.setOnClickListener(this);
        p10_button = (Button)findViewById(R.id.button10);
        p10_button.setText((dice[9].topLetter));
        p10_button.setOnClickListener(this);
        p11_button = (Button)findViewById(R.id.button11);
        p11_button.setText((dice[10].topLetter));
        p11_button.setOnClickListener(this);
        p12_button = (Button)findViewById(R.id.button12);
        p12_button.setText((dice[11].topLetter));
        p12_button.setOnClickListener(this);
        p13_button = (Button)findViewById(R.id.button13);
        p13_button.setText((dice[12].topLetter));
        p13_button.setOnClickListener(this);
        p14_button = (Button)findViewById(R.id.button14);
        p14_button.setText((dice[13].topLetter));
        p14_button.setOnClickListener(this);
        p15_button = (Button)findViewById(R.id.button15);
        p15_button.setText((dice[14].topLetter));
        p15_button.setOnClickListener(this);
        p16_button = (Button)findViewById(R.id.button16);
        p16_button.setText((dice[15].topLetter));
        p16_button.setOnClickListener(this);

        Button submitBtn = (Button)findViewById(R.id.submit);
        submitBtn.setOnClickListener(this);
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
    public void onClick(View v) {
        // default method for handling onClick Events.
        int curRow;
        int curCol;
        switch (v.getId()) {
            case R.id.submit:
                System.out.println("Checking user's words to dictionary.");
                //if user's submitted word is valid
                //if(Arrays.asList(possibleWords).contains(selectingWord.toString())){
                if(dictionary.isValid(selectingWord.toString())){
                    //check if it's already in the list of found word
                    if(wordsFound.contains(selectingWord.toString())){
                        Toast.makeText(getApplicationContext(), "THIS WORD HAS ALREADY BEEN SUBMITTED!!!", Toast.LENGTH_SHORT).show();
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
                        }else{
                            pts = 10;
                        }
                        userScore += pts;
                        flag = new boolean[16];
                        CharSequence text = "YOU EARNED " + pts + " POINTS!!!";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        updateTextView();
                        selectingWord.delete(0, selectingWord.length());
                        resetBtnBackground();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "INVALID WORD!!!", Toast.LENGTH_SHORT).show();
                    resetBtnBackground();
                }
                break;
            case R.id.button1:
                curRow = 0;
                curCol = 0;
                if (flag[0] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    flag = new boolean[16];
                    resetBtnBackground();
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 0;
                        prevCol = 0;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[0] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        System.out.println(prevCol + " " + prevRow + " " + curRow + " " + curCol);
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[0] = true;
                            prevCol = 0;
                            prevRow = 0;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            flag = new boolean[16];
                            resetBtnBackground();
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.button2:
                curRow = 0;
                curCol = 1;
                if (flag[1] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 0;
                        prevCol = 1;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[1] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[1] = true;
                            prevRow = 0;
                            prevCol = 1;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button3:
                curRow = 0;
                curCol = 2;
                if (flag[2] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 0;
                        prevCol = 2;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[2] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[2] = true;
                            prevRow = 0;
                            prevCol = 2;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button4:
                curRow = 0;
                curCol = 3;
                if (flag[3] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 0;
                        prevCol = 3;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[3] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[3] = true;
                            prevRow = 0;
                            prevCol = 3;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button5:
                curRow = 1;
                curCol = 0;
                if (flag[4] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 1;
                        prevCol = 0;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[4] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[4] = true;
                            prevRow = 1;
                            prevCol = 0;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button6:
                curRow = 1;
                curCol = 1;
                if (flag[5] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 1;
                        prevCol = 1;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[5] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[5] = true;
                            prevRow = 1;
                            prevCol = 1;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button7:
                curRow = 1;
                curCol = 2;
                if (flag[6] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 1;
                        prevCol = 2;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[6] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[6] = true;
                            prevRow = 1;
                            prevCol = 2;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button8:
                curRow = 1;
                curCol = 3;
                if (flag[7] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 1;
                        prevCol = 3;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[7] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[7] = true;
                            prevRow = 1;
                            prevCol = 3;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button9:
                curRow = 2;
                curCol = 0;
                if (flag[8] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 2;
                        prevCol = 0;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[8] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[8] = true;
                            prevRow = 2;
                            prevCol = 0;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button10:
                curRow = 2;
                curCol = 1;
                if (flag[9] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 2;
                        prevCol = 1;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[9] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[9] = true;
                            prevRow = 2;
                            prevCol = 1;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button11:
                curRow = 2;
                curCol = 2;
                if (flag[10] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 2;
                        prevCol = 2;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[10] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[10] = true;
                            prevRow = 2;
                            prevCol = 2;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button12:
                curRow = 2;
                curCol = 3;
                if (flag[11] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 2;
                        prevCol = 3;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[11] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[11] = true;
                            prevRow = 2;
                            prevCol = 3;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button13:
                curRow = 3;
                curCol = 0;
                if (flag[12] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 3;
                        prevCol = 0;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[12] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[12] = true;
                            prevRow = 3;
                            prevCol = 0;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button14:
                curRow = 3;
                curCol = 1;
                if (flag[13] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 3;
                        prevCol = 1;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[13] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[13] = true;
                            prevRow = 3;
                            prevCol = 1;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button15:
                curRow = 3;
                curCol = 2;
                if (flag[14] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 3;
                        prevCol = 2;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[14] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[14] = true;
                            prevRow = 3;
                            prevCol = 2;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            case R.id.button16:
                curRow = 3;
                curCol = 3;
                if (flag[15] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    Toast.makeText(getApplicationContext(), "THIS BUTTON HAS ALREADY BEEN PRESSED!!!", Toast.LENGTH_SHORT).show();
                    flag = new boolean[16];
                    resetBtnBackground();
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 3;
                        prevCol = 3;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[15] = true;
                        v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (curRow - prevRow <= 1 && curRow - prevRow >= -1
                                && curCol - prevCol <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[15] = true;
                            prevRow = 3;
                            prevCol = 3;
                            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            Toast.makeText(getApplicationContext(), "TWO BUTTONS MUST BE ADJACENT!!!", Toast.LENGTH_SHORT).show();
                            flag = new boolean[16];
                            resetBtnBackground();
                        }
                    }
                }
                break;
            default:
                break;
        }
        System.out.println(selectingWord);
    }
}




