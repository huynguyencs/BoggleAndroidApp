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
        if(boardPosition == 1 || boardPosition == 14)
            topLetter = String.valueOf(randomVowel());
        else
            topLetter = String.valueOf(randomChar());
        this.boardPosition = boardPosition;
    }

    private char randomChar() {
        while(true) {
            Random r = new Random();
            char c = (char) (r.nextInt(26) + 'a');

            //eliminates low frequency letters
            if(c == 'x' || c == 'q' || c == 'z' || c == 'v' || c == 'j' || c == 'k' || c == 'y')
                continue;
            return c;
        }
    }

    private char randomVowel() {
        char[] vowels = {'a','e','i','o','u','y'};
        Random r=new Random();
        return vowels[r.nextInt(vowels.length)];
    }

    private char randomVowel() {
        char[] vowels = {'a','e','i','o','u','y'};
        Random r=new Random();
        return vowels[r.nextInt(vowels.length)];
    }

}
