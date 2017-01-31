package com.example.mymac.boggle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Random;


public class MainBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

        System.out.println("Random char: " + rndChar());

        Button p1_button = (Button)findViewById(R.id.button1);
        p1_button.setText(Character.valueOf(rndChar()).toString());

        Button p2_button = (Button)findViewById(R.id.button2);
        p2_button.setText(Character.valueOf(rndChar()).toString());

        Button p3_button = (Button)findViewById(R.id.button3);
        p3_button.setText(Character.valueOf(rndChar()).toString());

        Button p4_button = (Button)findViewById(R.id.button4);
        p4_button.setText(Character.valueOf(rndChar()).toString());
    }

    protected char rndChar () {
        Random rnd = new Random();
        return (char) (65 + rnd.nextInt(26));
    }
}
