package com.example.mymac.boggle;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MultiroundResults extends AppCompatActivity {
    public TextView playerOneScore;
    public TextView playerTwoScore;
    public TextView timerTitle;
    public TextView timerText;
    public CountDownTimer nextRoundTimer;
    public Button doneButton;

    @Override
    protected void onStart(){
        super.onStart();
        System.out.print("Multiround onStart");
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiround_results);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);

        Intent intent = getIntent();
        String p1Time = intent.getStringExtra("p1_timer");
        String p2Time = intent.getStringExtra("p2_timer");
        Boolean isMidGame = intent.getBooleanExtra("isMidGame", true);
        boolean isLoser = intent.getBooleanExtra("isLoser", false);
        boolean modeCutThroat = intent.getBooleanExtra("modeCutThroat", false);
        playerOneScore.setText(p1Time);
        playerTwoScore.setText(p2Time);

        timerText = (TextView) findViewById(R.id.timerNextRound);
        doneButton = (Button) findViewById(R.id.buttonEndGameResults);

        if (isMidGame && !modeCutThroat) {
            timerTitle = (TextView) findViewById(R.id.nextRoundTimerTitle);
            timerTitle.setText("Time until next round:");

            doneButton.setVisibility(View.INVISIBLE);
            nextRoundTimer = new CountDownTimer(10 * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    long total_seconds = l / 1000;
                    long seconds = total_seconds % 60;
                    timerText.setText("" + seconds);
                }

                @Override
                public void onFinish() {
                    finish ();
                }
            };
            nextRoundTimer.start();
        } else {
            String winner;
            if(modeCutThroat){
                if(Integer.valueOf(p1Time) > Integer.valueOf(p2Time)) {
                    winner = "You Won";
                }else {
                    winner = "You Lost";
                }
            } else {
                if (isLoser) {
                    winner = "You Lost";
                } else {
                    winner = "You Won";
                }
            }
            timerText.setText(winner);
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MultiroundResults.this, HomeScreen.class);
                    startActivity(intent);
                }
            });

        }

    }

}
