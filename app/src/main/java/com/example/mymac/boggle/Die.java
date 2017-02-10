package com.example.mymac.boggle;
import java.util.Random;
/**
 * Created by brettchafin on 2/9/17.
 */

public class Die {

    char topLetter;

    //posistion on the board
    int[] boardCoordinates;

    Die(){
        topLetter = randomChar();
    }

    private char randomChar() {
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        return c;
    }

    // TODO - Finish
    public boolean isAdjacent(Die die) {
        return true;
    }

}
