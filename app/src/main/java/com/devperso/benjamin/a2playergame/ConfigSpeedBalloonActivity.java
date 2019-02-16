package com.devperso.benjamin.a2playergame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ConfigSpeedBalloonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_speed_balloon);

        final android.content.Context cont = this;

        findViewById(R.id.bStart).setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                EditText player1 = findViewById(R.id.editText);
                EditText player2 = findViewById(R.id.editText2);

                Intent intent = new Intent(cont, SpeedBalloonActivity.class);
                intent.putExtra("player1", player1.getText().toString());
                intent.putExtra("player2", player2.getText().toString());
                startActivity(intent);

                finish();
            }
        });
    }
}
