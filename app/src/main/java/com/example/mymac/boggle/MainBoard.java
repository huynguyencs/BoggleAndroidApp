package com.example.mymac.boggle;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import static com.example.mymac.boggle.R.id.button1;
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
    private int [] BtnIds = {R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                             R.id.button5, R.id.button6, R.id.button7, R.id.button8,
                             R.id.button9, R.id.button10, R.id.button11, R.id.button12,
                             R.id.button13, R.id.button14, R.id.button15, R.id.button16};
    private GestureDetector gestureDetector;

    int prevRow = 0;
    int prevCol = 0;
    int curRow;
    int curCol;
    private Point[] BtnLocation;
    RelativeLayout mainScreen;
    Button object;
    int offsetX, offsetY;

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
        resetBtnBackground();
        //get possibleWords from dictionary

        //get location from buttons
        BtnLocation = new Point [16];
        mainScreen = (RelativeLayout) findViewById(R.id.activity_main_board);

        mainScreen.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Layout has happened here.
                        readLocation();
                        // Don't forget to remove your listener when you are done with it.
                        if (Build.VERSION.SDK_INT<16) {
                            removeLayoutListenerPre16(mainScreen.getViewTreeObserver(),this);
                        } else {
                            removeLayoutListenerPost16(mainScreen.getViewTreeObserver(), this);
                        }
                    }
                });

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
        p1_button = (Button)findViewById(button1);
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
    public boolean onTouch(View v, MotionEvent event) {
            //TODO: Finger Sliding
            int dieNo;
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:   // start here
                    dieNo = trackLocation((int)event.getRawX(),(int)event.getRawY());
                    if(dieNo != -1)
                        ButtonHandler(dieNo);
                    if(v.getId() == R.id.submit){
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
                    }
                    break;


                case MotionEvent.ACTION_MOVE:
                    dieNo = trackLocation((int)event.getRawX(),(int)event.getRawY());
                    if(dieNo != -1)
                        ButtonHandler(dieNo);
                    break;
            }



        return true;
    }

    private void ButtonHandler(int dieNumber) {
        curRow = dieNumber/4;
        curCol = dieNumber%4;
        View v = (View) findViewById(BtnIds[dieNumber]);

        if (flag[dieNumber] == true) {
            for(int i = 0; i <16; ++i){
                System.out.print(flag[i] + " ");
            }
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
        System.out.println(selectingWord);
    }

    private int trackLocation(int x, int y){
        //System.out.println("X: " + x);
        //System.out.println("Y: " + y);
        for(int i = 0; i < 16; ++i){
            if (x > (BtnLocation[i].x + offsetX/4) && x < (BtnLocation[i].x + offsetX* 3/4) &&
                    y > (BtnLocation[i].y + offsetY/4) && y < (BtnLocation[i].y + offsetY * 3/4)){
                    //System.out.println("true");
                    //System.out.println("i: " + i);
                    if(!flag[i]) {
                        return i;
                    }
                }
        }
        return -1;
    }

    private void readLocation(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //offsetX = displayMetrics.widthPixels - mainScreen.getMeasuredWidth();
        //offsetY = displayMetrics.heightPixels - mainScreen.getMeasuredHeight();
        offsetX = p1_button.getWidth();
        offsetY = p1_button.getHeight();
        int[] locationInWindow = new int[2];

        p1_button.getLocationInWindow(locationInWindow);
        BtnLocation[0] = new Point(locationInWindow[0], locationInWindow[1]);
        p2_button.getLocationInWindow(locationInWindow);
        BtnLocation[1] = new Point(locationInWindow[0], locationInWindow[1]);
        p3_button.getLocationInWindow(locationInWindow);
        BtnLocation[2] = new Point(locationInWindow[0], locationInWindow[1]);
        p4_button.getLocationInWindow(locationInWindow);
        BtnLocation[3] = new Point(locationInWindow[0], locationInWindow[1]);
        p5_button.getLocationInWindow(locationInWindow);
        BtnLocation[4] = new Point(locationInWindow[0], locationInWindow[1]);
        p6_button.getLocationInWindow(locationInWindow);
        BtnLocation[5] = new Point(locationInWindow[0], locationInWindow[1]);
        p7_button.getLocationInWindow(locationInWindow);
        BtnLocation[6] = new Point(locationInWindow[0], locationInWindow[1]);
        p8_button.getLocationInWindow(locationInWindow);
        BtnLocation[7] = new Point(locationInWindow[0], locationInWindow[1]);
        p9_button.getLocationInWindow(locationInWindow);
        BtnLocation[8] = new Point(locationInWindow[0], locationInWindow[1]);
        p10_button.getLocationInWindow(locationInWindow);
        BtnLocation[9] = new Point(locationInWindow[0], locationInWindow[1]);
        p11_button.getLocationInWindow(locationInWindow);
        BtnLocation[10] = new Point(locationInWindow[0], locationInWindow[1]);
        p12_button.getLocationInWindow(locationInWindow);
        BtnLocation[11] = new Point(locationInWindow[0], locationInWindow[1]);
        p13_button.getLocationInWindow(locationInWindow);
        BtnLocation[12] = new Point(locationInWindow[0], locationInWindow[1]);
        p14_button.getLocationInWindow(locationInWindow);
        BtnLocation[13] = new Point(locationInWindow[0], locationInWindow[1]);
        p15_button.getLocationInWindow(locationInWindow);
        BtnLocation[14] = new Point(locationInWindow[0], locationInWindow[1]);
        p16_button.getLocationInWindow(locationInWindow);
        BtnLocation[15] = new Point(locationInWindow[0], locationInWindow[1]);


        //int[] locationOnScreen = new int[2];
        //object.getLocationOnScreen(locationOnScreen);
        for(int i = 0; i < 16; ++i) {
            System.out.println(
                    "\n"
                            + "getLocationInWindow() - " + BtnLocation[i].x + " : " + BtnLocation[i].y + "\n"
                            //+ "getLocationOnScreen() - " + locationOnScreen[0] + " : " + locationOnScreen[1] + "\n"
                            + "Offset x: y - " + offsetX + " : " + offsetY);
        }

    }

    @SuppressWarnings("deprecation")
    private void removeLayoutListenerPre16(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener){
        observer.removeGlobalOnLayoutListener(listener);
    }

    @TargetApi(16)
    private void removeLayoutListenerPost16(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener){
        observer.removeOnGlobalLayoutListener(listener);
    }
}



/*
FOR BACKUP
if (gestureDetector.onTouchEvent(event)) {
            System.out.println("tap");
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
                    ButtonHandler(0);
                    break;
                case R.id.button2:
                    ButtonHandler(1);
                    break;
                case R.id.button3:
                    ButtonHandler(2);
                    break;
                case R.id.button4:
                    ButtonHandler(3);
                    break;
                case R.id.button5:
                    ButtonHandler(4);
                    break;
                case R.id.button6:
                    ButtonHandler(5);
                    break;
                case R.id.button7:
                    ButtonHandler(6);
                    break;
                case R.id.button8:
                    ButtonHandler(7);
                    break;
                case R.id.button9:
                    ButtonHandler(8);
                    break;
                case R.id.button10:
                    ButtonHandler(9);
                    break;
                case R.id.button11:
                    ButtonHandler(10);
                    break;
                case R.id.button12:
                    ButtonHandler(11);
                    break;
                case R.id.button13:
                    ButtonHandler(12);
                    break;
                case R.id.button14:
                    ButtonHandler(13);
                    break;
                case R.id.button15:
                    ButtonHandler(14);
                    break;
                case R.id.button16:
                    ButtonHandler(15);
                    break;
                default:
                    break;
            }
            System.out.println(selectingWord);
            return false;
        }

        private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //do something
            return true;
        }
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }
    }
 */
