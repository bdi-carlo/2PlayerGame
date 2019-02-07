package com.devperso.benjamin.a2playergame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context cont = this;

        findViewById(R.id.bTicTac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( cont, ConfigTicTacToeActivity.class );
                startActivity( intent );
            }
        });

        findViewById(R.id.bSpeedBalloon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( cont, SpeedBalloonActivity.class );
                startActivity( intent );
            }
        });
    }
}
