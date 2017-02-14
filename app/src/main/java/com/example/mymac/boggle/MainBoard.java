package com.example.mymac.boggle;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;


public class MainBoard extends AppCompatActivity implements View.OnClickListener {

    public TextView timerText;
    public TextView user_score;
    private CountDownTimer timer;


    public Die[][] Dice;
    private String[] possibleWords;
    private String[] wordsFound;
    private Dictionary dictionary;
    private StringBuilder selectingWord = new StringBuilder();

    private boolean[] flag = new boolean[16];

    int prevRow = 0;
    int prevCol = 0;


    @Override
    public void onClick(View v) {
        // default method for handling onClick Events.
        int curRow;
        int curCol;
        switch (v.getId()) {
            case R.id.submit:
                System.out.println("Checking user's words to dictionary.");
                break;
            case R.id.button1:
                curRow = 0;
                curCol = 0;
                if (flag[0] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    //toast
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 0;
                        prevCol = 0;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[0] = true;
                    } else {
                        if (curRow - prevRow <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[0] = true;
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            //toast
                        }
                    }
                }
            case R.id.button2:
                curRow = 0;
                curCol = 1;
                if (flag[1] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    //toast
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 0;
                        prevCol = 1;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[1] = true;
                    } else {
                        if (curRow - prevRow <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[1] = true;
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            //toast
                        }
                    }
                }
            case R.id.button3:
                curRow = 0;
                curCol = 2;
                if (flag[2] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    //toast
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 0;
                        prevCol = 2;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[2] = true;
                    } else {
                        if (curRow - prevRow <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[2] = true;
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            //toast
                        }
                    }
                }
            case R.id.button4:
                curRow = 0;
                curCol = 3;
                if (flag[3] == true) {
                    selectingWord.delete(0, selectingWord.length());
                    //toast
                } else {
                    if (selectingWord.length() == 0) {
                        prevRow = 0;
                        prevCol = 3;
                        selectingWord.append(((Button)v).getText().toString());
                        flag[3] = true;
                    } else {
                        if (curRow - prevRow <= 1 && curCol - prevCol >= -1) {
                            selectingWord.append(((Button)v).getText().toString());
                            flag[3] = true;
                        } else {
                            selectingWord.delete(0, selectingWord.length());
                            //toast
                        }
                    }
                }
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
            case R.id.button10:
            case R.id.button11:
            case R.id.button12:
            case R.id.button13:
            case R.id.button14:
            case R.id.button15:
            case R.id.button16:
            default:
                selectingWord.append(((Button)v).getText().toString());
                System.out.println(selectingWord);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

        dictionary = new Dictionary();

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

    //Sets up the board with a number of helper functions
    private boolean newBoard() {
        while(true) {
            wordsFound = new String[0];
            randomDice();
            String[] wordPossiblilities = possiblePaths();
            if (validateBoard(wordPossiblilities)) continue;
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
        Dice = new Die[4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                Dice[i][j] = new Die();
            }
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
        p1_button.setText(Character.toString(Dice[0][0].topLetter));

        Button p2_button = (Button)findViewById(R.id.button2);
        p2_button.setText(Character.toString(Dice[0][1].topLetter));

        Button p3_button = (Button)findViewById(R.id.button3);
        p3_button.setText(Character.toString(Dice[0][2].topLetter));

        Button p4_button = (Button)findViewById(R.id.button4);
        p4_button.setText(Character.toString(Dice[0][3].topLetter));

        Button p5_button = (Button)findViewById(R.id.button5);
        p5_button.setText(Character.toString(Dice[1][0].topLetter));

        Button p6_button = (Button)findViewById(R.id.button6);
        p6_button.setText(Character.toString(Dice[1][1].topLetter));

        Button p7_button = (Button)findViewById(R.id.button7);
        p7_button.setText(Character.toString(Dice[1][2].topLetter));

        Button p8_button = (Button)findViewById(R.id.button8);
        p8_button.setText(Character.toString(Dice[1][3].topLetter));

        Button p9_button = (Button)findViewById(R.id.button9);
        p9_button.setText(Character.toString(Dice[2][0].topLetter));

        Button p10_button = (Button)findViewById(R.id.button10);
        p10_button.setText(Character.toString(Dice[2][1].topLetter));

        Button p11_button = (Button)findViewById(R.id.button11);
        p11_button.setText(Character.toString(Dice[2][2].topLetter));

        Button p12_button = (Button)findViewById(R.id.button12);
        p12_button.setText(Character.toString(Dice[2][3].topLetter));

        Button p13_button = (Button)findViewById(R.id.button13);
        p13_button.setText(Character.toString(Dice[3][0].topLetter));

        Button p14_button = (Button)findViewById(R.id.button14);
        p14_button.setText(Character.toString(Dice[3][1].topLetter));

        Button p15_button = (Button)findViewById(R.id.button15);
        p15_button.setText(Character.toString(Dice[3][2].topLetter));

        Button p16_button = (Button)findViewById(R.id.button16);
        p16_button.setText(Character.toString(Dice[3][3].topLetter));
    }
}




