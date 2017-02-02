package com.example.mymac.boggle;

import android.os.Bundle;
import android.app.ListActivity;


public class Leaderboard extends ListActivity {

    private CustomAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CustomAdapter(this);

        mAdapter.addSectionHeaderItem("Easy Mode");
        mAdapter.addItem("Henry: " + 150 + "pts");
        mAdapter.addItem("Brett: " + 100 + "pts");
        mAdapter.addItem("Gabi: " + 75 + "pts");

        mAdapter.addSectionHeaderItem("Medium Mode");
        mAdapter.addItem("Simeng: " + 50 + "pts");

        mAdapter.addSectionHeaderItem("Hard Mode");

        setListAdapter(mAdapter);
    }

}
