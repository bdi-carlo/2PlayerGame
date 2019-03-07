package com.devperso.benjamin.a2playergame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ConfigHangmanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_hangman);

        final android.content.Context cont = this;

        findViewById(R.id.bStartHang).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                EditText eWord = findViewById(R.id.word);

                //Removing spaces
                String word = eWord.getText().toString().replaceAll("\\s", "" );

                if( word.length() < 3 || word.length() > 16 ){
                    new AlertDialog.Builder( cont ).setTitle( getString(R.string.warningTitle) ).setMessage( getString(R.string.warningMessage) ).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialog, int which ) {
                            dialog.cancel();
                        }
                    }).setCancelable(false).show();
                }
                else{
                    Intent intent = new Intent(cont, HangmanActivity.class);
                    intent.putExtra("word", word.toUpperCase());
                    startActivity(intent);

                    finish();
                }
            }
        });
    }
}
