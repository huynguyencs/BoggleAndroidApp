package com.example.mymac.boggle;

import android.graphics.Typeface;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;

public class Results extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent i = getIntent();
        String[] possibleWords = i.getStringArrayExtra("possibleWords");
        String[] wordsFound = i.getStringArrayExtra("wordsFound");

        createTable(possibleWords, wordsFound);

        final Button buttonDone = (Button) findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Results.this, HomeScreen.class);
                startActivity(i);
            }
        });

        final Button buttonReplay = (Button) findViewById(R.id.buttonReplay);
        buttonReplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Results.this, MainBoard.class);
                startActivity(i);
            }
        });
      }

    public int get_score(String word) {
        /* The score of each valid word is counted based
        on its length, 1 point for 3 or 4 letter words, 2 points for 5 letter words, 3
        points for 6 letter words, 5 points for 7 letter words, and 10 points for words of
        8 or more letters */
        if (word.length() == 3 || word.length() == 4) return 1;
        else if (word.length() == 5) return 2;
        else if (word.length() == 6) return 3;
        else if (word.length() == 7) return 5;
        else if (word.length() >= 8) return 10;
        else return 0; // invalid length. This should never be reached.
    }

    public void createTable(String[] possibleWords, String[] wordsFound){
        int playerScore = 0;
        int possScore = 0;
        int wordScore = 0;
        TableLayout resultTable = (TableLayout) (findViewById(R.id.resultTable));
        Arrays.sort(wordsFound);
            for (int i = 0; i < possibleWords.length; i++) {
                wordScore = get_score(possibleWords[i]);
                possScore += wordScore;

                TableRow row = new TableRow(this);
                TextView tv = new TextView(this);
                tv.setText(possibleWords[i] + " " + wordScore);
                if (wordsFound != null && wordsFound.length > 0){
                    for (int j = 0; j < wordsFound.length; j++) {
                        if (possibleWords[i].equals(wordsFound[j])) {
                            tv.setTypeface(null, Typeface.BOLD);
                            playerScore += wordScore;
                        }
                    }
                }
                row.addView(tv);
                resultTable.addView(row);
            }
        TextView tvScore = (TextView) findViewById(R.id.tvScore);
        tvScore.setText(String.valueOf(playerScore));

        TextView tvPossScore = (TextView) findViewById(R.id.tvPossScore);
        tvPossScore.setText(String.valueOf(possScore));


    }

}
