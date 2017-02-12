package com.example.mymac.boggle;

import android.graphics.Typeface;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
      }

    public void createTable(String[] possibleWords, String[] wordsFound){
        TableLayout resultTable = (TableLayout) (findViewById(R.id.resultTable));
        Arrays.sort(wordsFound);
            for (int i = 0; i < possibleWords.length; i++) {
                TableRow row = new TableRow(this);
                TextView tv = new TextView(this);
                tv.setText(possibleWords[i]);
                if (wordsFound != null && wordsFound.length > 0){
                    for (int j = 0; j < wordsFound.length; j++) {
                        if (possibleWords[i].equals(wordsFound[j])) {
                            tv.setTypeface(null, Typeface.BOLD);
                        }
                    }
                }
                row.addView(tv);
                resultTable.addView(row);
            }
    }
}
