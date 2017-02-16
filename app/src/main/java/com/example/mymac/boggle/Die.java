package com.example.mymac.boggle;
import java.util.Random;
import android.widget.Button;
/**
 * Created by brettchafin on 2/9/17.
 */

public class Die {

    String topLetter;

    //posistion on the board
    int boardPosition;

    Die(int boardPosition){
        topLetter = String.valueOf(randomChar());
        this.boardPosition = boardPosition;
    }

    private char randomChar() {
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        return c;
    }


}
