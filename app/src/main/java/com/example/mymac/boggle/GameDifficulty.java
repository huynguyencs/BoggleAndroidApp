package com.example.mymac.boggle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GameDifficulty extends AppCompatActivity {
    String gameLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_difficulty);

        final Button easyDifficulty = (Button) findViewById(R.id.easyDifficulty);
        easyDifficulty.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Easy Difficulty.");
                gameLevel = "Easy";
                Intent in = ShakeActivity.newIntent(GameDifficulty.this, gameLevel);
                startActivity(in);
            }
        });

        final Button mediumDifficulty = (Button) findViewById(R.id.mediumDifficulty);
        mediumDifficulty.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Medium Difficulty.");
                gameLevel = "Medium";
                Intent in = ShakeActivity.newIntent(GameDifficulty.this, gameLevel);
                startActivity(in);
            }
        });

        final Button hardDifficulty = (Button) findViewById(R.id.hardDifficulty);
        hardDifficulty.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Hard Difficulty.");
                gameLevel = "Hard";
                Intent in = ShakeActivity.newIntent(GameDifficulty.this, gameLevel);
                startActivity(in);
            }
        });
    }

}
