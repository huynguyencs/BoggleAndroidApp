package com.example.mymac.boggle;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Leaderboard {
    private List<Highscore> highscoreList;
    BufferedReader filescan = null;
    BufferedWriter filewrite = null;

    // constructor
    Leaderboard(Context context, String difficulty) {
        highscoreList = new ArrayList<Highscore>();
        highscoreReader(context, difficulty);
        highscoreSort();
    }

    private void highscoreReader(Context context, String difficulty) {

        try {
            filescan = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("highScores" + difficulty + ".txt"))
            );

            String line = filescan.readLine();
            while (line != null) {
                String player = "";
                Integer score = null;

                String[] parsedLine = line.split(",");
                if (parsedLine != null && parsedLine.length >= 2) {
                    score = Integer.parseInt(parsedLine[0]);
                    player = parsedLine[1];
                    Highscore hs = new Highscore(score, player);
                    highscoreList.add(hs);
                } else {
                    Log.e("Parsing scores", "Wrong input in text file: " + parsedLine);
                    continue;
                }
                line = filescan.readLine();
            }
        } catch (IOException e) {
            Log.e("error reading file", "" + e);
        } finally {
            if(filescan != null) {
                try {
                    filescan.close();
                } catch (IOException e) {
                    Log.e("error reading file", "" + e);
                }
            }
        }
        return;
    }

    private void highscoreWriter(Context context, String difficulty) {

        try {
            filewrite = new BufferedWriter(
                    new OutputStreamWriter(context.openFileOutput("highScores" + difficulty + ".txt",
                            context.MODE_PRIVATE))
            );

            if (highscoreList == null || highscoreList.isEmpty()) {
                return;
            }

            for (Highscore h : highscoreList) {
                String output = h.score() + "," + h.player();
                Log.i("output", output);
                filewrite.write(output);
            }
            filewrite.close();
        } catch (IOException e) {
            Log.e("error writing to file", "" + e);
        } finally {
            if(filewrite != null) {
                try {
                    filewrite.close();
                } catch (IOException e) {
                    Log.e("error writing to file", "" + e);
                }
            }
        }
        return;
    }

    private void highscoreSort() {
        Collections.sort(highscoreList, Collections.<Highscore>reverseOrder());

        while (highscoreList != null && highscoreList.size() > 5 ) {
            highscoreList.remove(highscoreList.size()-1);
        }
    }

    public boolean isHighscore(int score) {
        highscoreSort();
        if (highscoreList == null || highscoreList.size() < 5) {
            return true;
        } else {
            Highscore lastHS = highscoreList.get(highscoreList.size() - 1);
            if (score > lastHS.score()) {
                return true;
            }
        }
        return false;
    }

    public boolean updateHighscore(Context context, Highscore hs, String diff) {
        if (!isHighscore(hs.score())) {
            return false;
        }
        highscoreList.add(hs);
        highscoreWriter(context, diff);
        return true;
    }

        public List<Highscore> asList() {
            return highscoreList;
    }
}