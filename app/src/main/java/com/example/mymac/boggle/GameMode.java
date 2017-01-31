package com.example.mymac.boggle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GameMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        Button singleMode = (Button) findViewById(R.id.singleMode);
        singleMode.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Single Player Mode.");
                Intent i = new Intent(GameMode.this, GameDifficulty.class);
                startActivity(i);
            }
        });
    }
}

