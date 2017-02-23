package com.example.mymac.boggle;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.collect.ArrayListMultimap;
import android.support.test.espresso.core.deps.guava.collect.Multimap;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


public class Highscore implements Comparable<Highscore> {
    private Integer score;
    private String player;

    public Highscore(int score, String player){
        this.score = score;
        this.player = player;
    }

    public Integer score() { return score; }
    public String player() { return player; }

    public int compareTo(Highscore hs) {
        return this.score.compareTo(hs.score);
    }

}
