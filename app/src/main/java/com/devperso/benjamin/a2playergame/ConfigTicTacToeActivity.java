package com.devperso.benjamin.a2playergame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ConfigTicTacToeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_tic_tac_toe);

        findViewById(R.id.bStart).setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                EditText player1 = findViewById(R.id.editText);
                EditText player2 = findViewById(R.id.editText2);

                Intent intent = new Intent(cont, DisplayMessageActivity.class);
                intent.putExtra("prenom", prenom.getText().toString());
                intent.putExtra("nom", nom.getText().toString());
                startActivity(intent);
            }
        });
    }
}
