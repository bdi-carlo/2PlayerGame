package com.devperso.benjamin.a2playergame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class HangmanActivity extends Activity {

    private String word;
    private Context cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        this.cont = this;

        Intent intent = getIntent();
        this.word = intent.getStringExtra("word");

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideWord();
            }
        });

    }

    public void hideWord(){
        LinearLayout wordLayout = findViewById(R.id.wordLayout);

        for( int i=0 ; i < this.word.length() ; i++ ){
            TextView viewLetter = new TextView( this.cont );
            viewLetter.setText( "_" );
            viewLetter.setTextSize( 1, 35 );
            viewLetter.setPadding( 0, 0, 4, 0 );

            // To put the correct id in function of the letter's number
            String variableName = "letter" + String.valueOf(i);
            viewLetter.setId(getResourceId( variableName, "id", getPackageName() ));

            wordLayout.addView( viewLetter );
        }
    }

    public void printWord(){
        for( int i=0 ; i < this.word.length() ; i++ ){
            TextView viewLetter = null;
            // To put the correct id in function of the letter's number
            String variableName = "letter" + String.valueOf(i);
            viewLetter = findViewById(getResourceId( variableName, "id", getPackageName() ));

            if( viewLetter != null ){
                viewLetter.setText(String.valueOf(word.charAt(i)));
            }
        }
    }

    // Function very very very usefull to have the correct id from String parameters !
    public int getResourceId(String pVariableName, String pResourcename, String pPackageName)
    {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
