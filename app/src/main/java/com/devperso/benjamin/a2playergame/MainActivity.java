package com.devperso.benjamin.a2playergame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context cont = this;

        final ImageView TicTac = findViewById(R.id.bTicTac);
        final ImageView SpeedBalloon = findViewById(R.id.bSpeedBalloon);
        final ImageView Hangman = findViewById(R.id.bHangman);

        TicTac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( cont, ConfigTicTacToeActivity.class );
                startActivity( intent );
            }
        });

        SpeedBalloon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( cont, SpeedBalloonActivity.class );
                startActivity( intent );
            }
        });

        Hangman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( cont, ConfigHangmanActivity.class );
                startActivity( intent );
            }
        });
    }
}
