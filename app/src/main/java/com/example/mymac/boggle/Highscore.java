package com.example.mymac.boggle;

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
