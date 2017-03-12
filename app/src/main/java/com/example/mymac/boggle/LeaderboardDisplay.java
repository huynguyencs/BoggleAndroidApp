package com.example.mymac.boggle;

import android.os.Bundle;
import android.app.ListActivity;

public class LeaderboardDisplay extends ListActivity {
    private CustomAdapter mAdapter;
    private Leaderboard highScoresEasy;
    private Leaderboard highScoresMedium;
    private Leaderboard highScoreshard;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CustomAdapter(this);

        highScoresEasy = new Leaderboard(this, "easy");
        highScoresMedium = new Leaderboard(this, "medium");
        highScoreshard = new Leaderboard(this, "hard");

        mAdapter.addSectionHeaderItem("Easy Mode");
        for (Highscore hsE : highScoresEasy.asList()) {
            mAdapter.addItem(String.format("%s: %d", hsE.player(), hsE.score()));
        }

        mAdapter.addSectionHeaderItem("Medium Mode");
        for (Highscore hsM : highScoresMedium.asList()) {
                mAdapter.addItem(String.format("%s: %d", hsM.player(), hsM.score()));
        }
        mAdapter.addSectionHeaderItem("Hard Mode");
            for (Highscore hsH : highScoreshard.asList()) {
                mAdapter.addItem(String.format("%s: %d", hsH.player(), hsH.score()));
        }
        setListAdapter(mAdapter);
    }
}
