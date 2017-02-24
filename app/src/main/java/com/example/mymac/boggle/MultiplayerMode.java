package com.example.mymac.boggle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MultiplayerMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_mode);


        final Button basicModeBtn = (Button) findViewById(R.id.basicMode);
        basicModeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Basic Mode.");
                Intent i = new Intent(MultiplayerMode.this, MultiplayerBoard.class);
                startActivity(i);
            }
        });

        final Button cutThroatBtn = (Button) findViewById(R.id.cutThroatMode);
        cutThroatBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("clicks","You Clicked Cut Throat Mode.");
                Intent i = new Intent(MultiplayerMode.this, MultiplayerBoard.class);
                startActivity(i);
            }
        });
    }
}
