package com.example.mymac.boggle;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File;


public class MainBoard  extends AppCompatActivity  {

    public TextView timerText;
    public TextView user_score;
    private CountDownTimer timer;


    public Die[][] Dice;
    private String[] possibleWords;
    private String[] wordsFound;
    private Dictionary dictionary;

    public Die[] dice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

        dictionary = new Dictionary();

        //fully create a new Board
        try {
            newBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //attach new board to UI Buttons
        buttonCreation();

        timerText = (TextView) this.findViewById(R.id.timer);
        timer = new countDownTimer(180 * 1000, 1 * 1000);
        timer.start();

        user_score = (TextView) this.findViewById(R.id.score);
        user_score.setText("Your score: " + 0);


    }

    //Sets up the board with a number of helper functions
    private boolean newBoard() throws IOException {
        while(true) {
            wordsFound = new String[0];
            randomDice();
            DiceGraph diceGraph = new DiceGraph(Dice);
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
        Dice = new Die[4][4];
        int counter = 1;    //used to number the buttons
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                Dice[i][j] = new Die(counter);
                counter++;
            }
        }
        return true;
    }

    //todo
    private String[] possiblePaths() throws IOException{

        List<String> generatedWords = new LinkedList<>();

        Scanner filescan = new Scanner(new File("possible_paths.txt"));
        while (filescan.hasNext()) {

            String word = "";
            String line = filescan.nextLine();
            for (String node: line.split("-")) {
                String letter = dice[Integer.parseInt(node)].topLetter;
                word += letter;
            }
            generatedWords.add(word);
        }
        System.out.print(generatedWords);
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
        p1_button.setText(Dice[0][0].topLetter);

        Button p2_button = (Button)findViewById(R.id.button2);
        p2_button.setText(Dice[0][1].topLetter);

        Button p3_button = (Button)findViewById(R.id.button3);
        p3_button.setText(Dice[0][2].topLetter);

        Button p4_button = (Button)findViewById(R.id.button4);
        p4_button.setText(Dice[0][3].topLetter);

        Button p5_button = (Button)findViewById(R.id.button5);
        p5_button.setText((Dice[1][0].topLetter));

        Button p6_button = (Button)findViewById(R.id.button6);
        p6_button.setText((Dice[1][1].topLetter));

        Button p7_button = (Button)findViewById(R.id.button7);
        p7_button.setText((Dice[1][2].topLetter));

        Button p8_button = (Button)findViewById(R.id.button8);
        p8_button.setText((Dice[1][3].topLetter));

        Button p9_button = (Button)findViewById(R.id.button9);
        p9_button.setText((Dice[2][0].topLetter));

        Button p10_button = (Button)findViewById(R.id.button10);
        p10_button.setText((Dice[2][1].topLetter));

        Button p11_button = (Button)findViewById(R.id.button11);
        p11_button.setText((Dice[2][2].topLetter));

        Button p12_button = (Button)findViewById(R.id.button12);
        p12_button.setText((Dice[2][3].topLetter));

        Button p13_button = (Button)findViewById(R.id.button13);
        p13_button.setText((Dice[3][0].topLetter));

        Button p14_button = (Button)findViewById(R.id.button14);
        p14_button.setText((Dice[3][1].topLetter));

        Button p15_button = (Button)findViewById(R.id.button15);
        p15_button.setText((Dice[3][2].topLetter));

        Button p16_button = (Button)findViewById(R.id.button16);
        p16_button.setText((Dice[3][3].topLetter));
    }
}




