package com.example.mymac.boggle;

/**
 * Created by MyMac on 2/1/17.
 */

public class User {
    private String name;
    private int best_score;

    public User(String name, int best_score){
        this.name = name;
        this.best_score = best_score;
    }

    public String getName() {
        return name;
    }

    public int getBest_score() {
        return best_score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBest_score(int best_score) {
        this.best_score = best_score;
    }
}
