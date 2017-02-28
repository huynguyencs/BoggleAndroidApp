package com.example.mymac.boggle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GameDifficulty extends AppCompatActivity {
    private static String difficulty = "";
    private static String playerName = "";
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_difficulty);

        final Button easyDifficulty = (Button) findViewById(R.id.easyDifficulty);
        easyDifficulty.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Easy Difficulty.");
                difficulty = "easy";
                Intent i = new Intent(GameDifficulty.this, MainBoard.class);
                startActivity(i);
            }
        });

        final Button mediumDifficulty = (Button) findViewById(R.id.mediumDifficulty);
        mediumDifficulty.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Medium Difficulty.");
                difficulty = "medium";
                Intent i = new Intent(GameDifficulty.this, MainBoard.class);
                startActivity(i);
            }
        });

        final Button hardDifficulty = (Button) findViewById(R.id.hardDifficulty);
        hardDifficulty.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Hard Difficulty.");
                difficulty = "hard";
                Intent i = new Intent(GameDifficulty.this, MainBoard.class);
                startActivity(i);
            }
        });

        easyDifficulty.setEnabled(false);
        mediumDifficulty.setEnabled(false);
        hardDifficulty.setEnabled(false);

        toast = Toast.makeText(this, "Enter Name First", Toast.LENGTH_LONG);
        toast.show();

        EditText textPlayerName = (EditText) findViewById(R.id.editTextUserName);
        textPlayerName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                playerName = s.toString().trim();
                if (playerName.length() > 0) {
                    easyDifficulty.setEnabled(true);
                    mediumDifficulty.setEnabled(true);
                    hardDifficulty.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public static String getDifficulty() {
        return difficulty;
    }
    public static String getPlayerName() { return playerName; }

}
