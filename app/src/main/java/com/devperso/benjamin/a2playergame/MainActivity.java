package com.devperso.benjamin.a2playergame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Context cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.cont = this;

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
                Intent intent = new Intent( cont, ConfigSpeedBalloonActivity.class );
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

    // Ask to the player if he really wants to leave
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder( this.cont ).setMessage( getString(R.string.leaveMessage) ).setPositiveButton( getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                dialog.cancel();

                finish();
            }
        }).setNegativeButton( getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setCancelable(false).show();
    }
}
