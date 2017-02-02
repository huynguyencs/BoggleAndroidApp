package com.example.mymac.boggle;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;


public class MainBoard extends AppCompatActivity {
    public TextView timerText;
    public TextView user_score;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

        timerText = (TextView) this.findViewById(R.id.timer);
        timer = new countDownTimer(180 * 1000, 1 * 1000);
        timer.start();

        user_score = (TextView) this.findViewById(R.id.score);
        user_score.setText("Your score: " + 0);

        Button p1_button = (Button)findViewById(R.id.button1);
        p1_button.setText(Character.valueOf(rndChar()).toString());

        Button p2_button = (Button)findViewById(R.id.button2);
        p2_button.setText(Character.valueOf(rndChar()).toString());

        Button p3_button = (Button)findViewById(R.id.button3);
        p3_button.setText(Character.valueOf(rndChar()).toString());

        Button p4_button = (Button)findViewById(R.id.button4);
        p4_button.setText(Character.valueOf(rndChar()).toString());

        Button p5_button = (Button)findViewById(R.id.button5);
        p5_button.setText(Character.valueOf(rndChar()).toString());

        Button p6_button = (Button)findViewById(R.id.button6);
        p6_button.setText(Character.valueOf(rndChar()).toString());

        Button p7_button = (Button)findViewById(R.id.button7);
        p7_button.setText(Character.valueOf(rndChar()).toString());

        Button p8_button = (Button)findViewById(R.id.button8);
        p8_button.setText(Character.valueOf(rndChar()).toString());

        Button p9_button = (Button)findViewById(R.id.button9);
        p9_button.setText(Character.valueOf(rndChar()).toString());

        Button p10_button = (Button)findViewById(R.id.button10);
        p10_button.setText(Character.valueOf(rndChar()).toString());

        Button p11_button = (Button)findViewById(R.id.button11);
        p11_button.setText(Character.valueOf(rndChar()).toString());

        Button p12_button = (Button)findViewById(R.id.button12);
        p12_button.setText(Character.valueOf(rndChar()).toString());

        Button p13_button = (Button)findViewById(R.id.button13);
        p13_button.setText(Character.valueOf(rndChar()).toString());

        Button p14_button = (Button)findViewById(R.id.button14);
        p14_button.setText(Character.valueOf(rndChar()).toString());

        Button p15_button = (Button)findViewById(R.id.button15);
        p15_button.setText(Character.valueOf(rndChar()).toString());

        Button p16_button = (Button)findViewById(R.id.button16);
        p16_button.setText(Character.valueOf(rndChar()).toString());



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
}




