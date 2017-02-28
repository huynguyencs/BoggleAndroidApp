package com.example.mymac.boggle;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
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

/**
 * Created by brettchafin on 2/23/17.
 */



public class MultiplayerBoard extends AppCompatActivity implements View.OnTouchListener{



    /*********************** Main Board Data elements **************************/

    //flags for bluetooth and game mode
    public boolean flag_host = false;
    public boolean flag_guest;
    public boolean flag_basicMode;
    public boolean flag_cutThroatMode;

    /************ new varribles to be used for multiplayer ***********/
    public TextView p1_timerText;
    public TextView p2_timerText;
    public TextView correct_word;
    public TextView p1_scoreText;
    public TextView p2_scoreText;
    private int p1_score;
    private int p2_score;
    private CountDownTimer p1_timer;
    private CountDownTimer p2_timer;


    //Set of 16 dice of the current board
    public Die[] dice;

    //All possible words on the board, along with the user found words so far
    private String[] possibleWords;
    private String pairedDeviceName;
    private ArrayList<String> wordsFound;

    //Helper class to validate words and solve the board
    private Dictionary dictionary;

    //For generating a word by the user
    private StringBuilder selectingWord = new StringBuilder();

    //Model for button selection
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

    Button p1_button;
    Button p2_button;
    Button p3_button;
    Button p4_button;
    Button p5_button;
    Button p6_button;
    Button p7_button;
    Button p8_button;
    Button p9_button;
    Button p10_button;
    Button p11_button;
    Button p12_button;
    Button p13_button;
    Button p14_button;
    Button p15_button;
    Button p16_button;


    // Local Bluetooth adapter

    private BluetoothAdapter mBluetoothAdapter = null;

    // Member object for the chat services
    private BluetoothManager mBluetoothManager = null;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;


    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    /***************************
     * Multiplayer Board Methods
     *****************************/


    //Contructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_mode);


        randomDice(); //Generates a new set of random dice

        //Grabs the Dictionary text file and creates an instance of Dictionary
        try {
            InputStream inputS = getResources().openRawResource(R.raw.dictionary);
            dictionary = new Dictionary(inputS, dice);
        } catch (Exception e) {
        }

        //shakeDetector = new ShakeDetector(this);



        //fully create a new Board
        try {
            newBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //attach new board to UI Buttons
        buttonCreation();
        resetBtnBackground();

        //get location from buttons
        BtnLocation = new Point [16];
        mainScreen = (RelativeLayout) findViewById(R.id.activity_basic_mode);

        mainScreen.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Layout has happened here.
                        readLocation();
                        // Don't forget to remove your listener when you are done with it.
                        if (Build.VERSION.SDK_INT < 16) {
                            removeLayoutListenerPre16(mainScreen.getViewTreeObserver(), this);
                        } else {
                            removeLayoutListenerPost16(mainScreen.getViewTreeObserver(), this);
                        }
                    }
                });



        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //Create Board Timer + User Score
        p1_timerText = (TextView) this.findViewById(R.id.p1_timer);
        p2_timerText = (TextView) this.findViewById(R.id.p2_timer);

        //TODO: get the correct time/points from the prev round if nessesary
        p1_timer = new MultiplayerBoard.countDownTimer(60 * 1000, 1 * 1000);
        p2_timer = new MultiplayerBoard.countDownTimer(60 * 1000, 1 * 1000);
        p1_timer.start();
        p2_timer.start();

        p1_scoreText = (TextView) this.findViewById(R.id.p1_score);
        p2_scoreText = (TextView) this.findViewById(R.id.p2_score);
        p1_scoreText.setText("Player1 score: " + p1_score);
        p2_scoreText.setText("Player2 score: " + p2_score);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (mBluetoothManager == null)
                mBluetoothManager = new BluetoothManager(this, mHandler);
        }
    }




   /*
    @Override
    public void onShake() {
        //fully create a new Board
        try {
            newBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    //Sets up the board with a number of helper functions
    private boolean newBoard() throws IOException {
        while(true) {
            p1_score = 0;
            p2_score = 0;
            wordsFound = new ArrayList<String>();
            possibleWords = new String[0];
            possibleWords = dictionary.findPossibleWords(); //Runs a Boggle Solver
            System.out.println(possibleWords.length);
            return true;
        }
    }


    //Generates Random Dice for the board
    private boolean randomDice() {
        dice = new Die[16];
        for(int i = 0; i < dice.length ; i++) {
            dice[i] = new Die(i);
        }
        return true;
    }

    private void endGame(){
        Intent i = new Intent(MultiplayerBoard.this, Results.class);
        i.putExtra("possibleWords", possibleWords);
        i.putExtra("wordsFound", wordsFound);
        i.putExtra("userScore", p1_score);
        startActivity(i);
    }


    public class countDownTimer extends CountDownTimer {
        public countDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onFinish() {
            p1_timerText.setText("TIME'S UP!");
            endGame();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            long total_seconds = millisUntilFinished / 1000;
            long seconds = total_seconds % 60;
            long minutes = total_seconds / 60;
            p1_timerText.setText("Time Left: " + minutes + ":" + seconds);
            p2_timerText.setText("Time Left: " + minutes + ":" + seconds);
            if (minutes < 2 && seconds <= 15) {
                p1_timerText.setTextColor(Color.rgb(255,0,0));
                p2_timerText.setTextColor(Color.rgb(255,0,0));
            }
        }
    }

    //Update score, alternating colors
    public void updateTextView() {
        TextView p1_textView = (TextView) findViewById(R.id.p1_score);
        TextView p2_textView = (TextView) findViewById(R.id.p2_score);
        p1_textView.setText("Player1 score: " + p1_score);
        p2_textView.setText("Player2 score: " + p2_score);
        p1_textView.setTextColor( getRandomColor());
    }

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

    }


    /****************************** Button Logic ********************************/



    //Creates UI buttons and attaches them to the Game Board Dice
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


        Button cancelBtn = (Button)findViewById(R.id.cancel);

        cancelBtn.setOnTouchListener(this);
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


    //Fingerslide Selection Logic
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //TODO: Finger Sliding
        int dieNo;
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:   // start here
                dieNo = trackLocation((int)event.getRawX(),(int)event.getRawY());
                if(dieNo != -1)
                    ButtonHandler(dieNo);
                if(v.getId() == R.id.cancel){
                    //TODO cancel case create
                    Toast.makeText(getApplicationContext(), "PREVIOUS SELECTIONS HAVE BEEN CANCELLED!!!", Toast.LENGTH_SHORT).show();
                    resetBtnBackground();
                    selectingWord.delete(0, selectingWord.length());
                    flag = new boolean[16];
                }
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
                            correct_word = (TextView) this.findViewById(R.id.correctSubmission);                          

                            //TODO: Increment points and notifiy other player
                            p1_score += pts;
                            notifyWordFound(selectingWord.toString(), pts);


                            correct_word.setMovementMethod(new ScrollingMovementMethod());
                            String wordList = "";
                            for (int i = 0; i < wordsFound.size(); i++) {
                                wordList = wordList.concat(wordsFound.get(i) + '\n');
                            }
                            correct_word.setText(wordList);
                         
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



    /*
    *****HENRY ADDED******
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBluetoothManager != null) {
            mBluetoothManager.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mBluetoothManager != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mBluetoothManager.getState() == BluetoothManager.STATE_NONE) {
                // Start the Bluetooth chat services
                mBluetoothManager.start();
            }
        }
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mBluetoothManager.getState() != BluetoothManager.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mBluetoothManager.write(send);
        }
    }

    //Called when user finds a valid word
    private void notifyWordFound(String wordFound, int pointValue){
        String str = NOTIFY_WORD_FOUND + " " + wordFound + " " + String.valueOf(pointValue);
        sendMessage(str);
    }

    //Called when the Host has already created the board and needs to send to to the guest player
    private void sendBoardData(String diceAsString, String[] possibleWords, int myScore, int myTimer, int yourScore, int yourTimer) {
        String str = SEND_BOARD_DATA + diceAsString + myScore + myTimer + yourScore + yourTimer + possibleWords.toString();
        sendMessage(str);
    }

    //converts local dice to string
    String diceAsString() {
        String str = "";
        for(int i = 0; i < dice.length; i++){
            str += dice[i].topLetter;
        }
        return str;
    }

    //Guest sends the ending results of their round to the host for computations
    private void sendResults() {
        String str = END_GAME_GUEST + p1_score + p1_timer + wordsFound;
        sendMessage(str);
    }

    public void endGameGuest() {
        sendResults();
    }

    /**
    Receive a message from a paired device
     */

    //Message codes for Handler switch statement
    static final String NOTIFY_WORD_FOUND = "NOTIFY_WORD_FOUND";
    static final String SEND_BOARD_DATA = "SEND_BOARD_DATA";
    static final String END_GAME_GUEST = "END_GAME_GUEST";

    public void endGameHost(String[] argTokens) {
        //String str = END_GAME_GUEST + p1_score + p1_timer + wordsFound;

        int opponentScore = Integer.getInteger(argTokens[1]);
        int opponentTimer = Integer.getInteger(argTokens[2]);

        String[] opponentWordsFound = new String[argTokens.length - 2];
        for(int i = 3; i > argTokens.length; i ++){
            opponentWordsFound[i - 3] = argTokens[i];
        }

        int myRemainingTime = 0; //grab remaining time

        int myNextRoundTime = p1_score + myRemainingTime;

        /**

        //display results of round
        yourFoundWords = //parsed arg
                // Display(yourFoundWords, myWordsFound);

        yourPoints = //parsed arg
                yourRemainingTime = //parsed arg
                        yourNextRoundTime = yourPoints + yourRemainingTime;
        myNextRoundTime = p1_points + myRemainingTime;

        createNewBoard();

        sendBoardData(diceAsString, possibleWords, myScore, myNextRoundTime, yourScore, yourNextRoundTimer);

        //begin new round?

        //updates global points for the whole multiplayer session
        updateTotalPoints(myPoints, yourPoints);

         */
    }

    //Handler Helper Functions
    private void updateOpponentScore(String[] argTokens) {
        wordsFound.add(argTokens[1]);
        p2_score += Integer.getInteger(argTokens[2]);
        updateTextView();
        Toast.makeText(getApplicationContext(),"Player 2 Found a Word!", Toast.LENGTH_SHORT).show();
    }

    //used by guest to create a local board
    private void receiveBoardData(String[] argTokens) {

        String diceAsString = argTokens[1];
        int myScore = Integer.getInteger(argTokens[2]);
        int myTimer = Integer.getInteger(argTokens[3]);
        int yourScore = Integer.getInteger(argTokens[4]);
        int yourTimer = Integer.getInteger(argTokens[5]);

        String[] receivedPossibleWords = new String[argTokens.length - 5];
        for(int i = 6; i > argTokens.length; i ++){
            receivedPossibleWords[i - 6] = argTokens[i];
        }

        //Now create your board from this information


        //
        //
        // SEND_BOARD_DATA + diceAsString + myScore + myTimer + yourScore + yourTimer + possibleWords.toString()
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //report the state of connection between 2 devices
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothManager.STATE_CONNECTED:
                            Toast.makeText(getApplicationContext(), R.string.status_connected_to, Toast.LENGTH_SHORT).show();
                            if(flag_host)
                                Toast.makeText(getApplicationContext(), "You're host.", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), "You're guest.", Toast.LENGTH_SHORT).show();
                            //TODO: send init board to the guest
                            break;
                        case BluetoothManager.STATE_CONNECTING:
                            Toast.makeText(getApplicationContext(), R.string.status_connecting, Toast.LENGTH_SHORT).show();
                            flag_host = true;
                            break;
                        case BluetoothManager.STATE_LISTEN:
                        case BluetoothManager.STATE_NONE:
                            Toast.makeText(getApplicationContext(), R.string.status_not_connected, Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;

                // save the connected device's name
                case MESSAGE_DEVICE_NAME:
                    //do something
                    pairedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + pairedDeviceName, Toast.LENGTH_SHORT).show();
                    break;

                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;

                //read a message from a bluetooth device
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String rawMessage = new String(readBuf, 0, msg.arg1);
                    String[] argTokens = rawMessage.split(" ");
                    switch (argTokens[0]) {
                        case NOTIFY_WORD_FOUND:
                            updateOpponentScore(argTokens);
                            break;

                        case SEND_BOARD_DATA:
                            //receiveBoardData(argTokens);
                            break;

                        case END_GAME_GUEST:
                           // endGameHost(argTokens);
                            break;
                    }
                    //do something
                    Toast.makeText(getApplicationContext(), rawMessage, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void connect(View v) {
        Intent serverIntent = new Intent(this, BluetoothDeviceList.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    public void discoverable(View v) {
        ensureDiscoverable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mp_boggle, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(this, BluetoothDeviceList.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;
            }
            case R.id.discoverable: {
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
            }
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(BluetoothDeviceList.EXTRA_DEVICE_ADDRESS);
                    // Get the BluetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mBluetoothManager.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == this.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    mBluetoothManager = new BluetoothManager(this, mHandler);
                } else {
                    // User did not enable Bluetooth or an error occured
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

}


