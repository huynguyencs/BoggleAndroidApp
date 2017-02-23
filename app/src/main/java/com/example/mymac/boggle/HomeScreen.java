package com.example.mymac.boggle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button SPlayerBtn = (Button) findViewById(R.id.onePlayer);
        SPlayerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Single Player Game.");
                Intent i = new Intent(HomeScreen.this, GameDifficulty.class);
                startActivity(i);
            }
        });

        final Button MPlayerBtn = (Button) findViewById(R.id.twoPlayer);
        MPlayerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Multiplayer Game");
                Intent i = new Intent(HomeScreen.this, MultiplayerMode.class);
                startActivity(i);
            }
        });

        final Button leaderBoardBtn = (Button) findViewById(R.id.leaderBoard);
        leaderBoardBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked LeaderboardDisplay");
                Intent i = new Intent(HomeScreen.this, LeaderboardDisplay.class);
                startActivity(i);
            }
        });

        final Button InstructionBtn = (Button) findViewById(R.id.instruction);
        InstructionBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Instruction.");
                Intent i = new Intent(HomeScreen.this, Instruction.class);
                startActivity(i);
            }
        });

        final Button exitGameBtn = (Button) findViewById(R.id.exitGameButton);
        exitGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


    }
}
