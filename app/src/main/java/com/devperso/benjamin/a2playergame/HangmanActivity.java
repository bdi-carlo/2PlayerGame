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
    private int nbMistakes;
    // Dictionary of alphabet
    String[] alphabet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        this.cont = this;
        this.nbMistakes = 0;
        this.alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

        Intent intent = getIntent();
        this.word = intent.getStringExtra("word");

        // Hide the word with "_"
        hideWord();

        // ---- Création dynamique des bouttons pour chaque lettre avec comme identifiant bA, bB, bC, bD, ect ...

        // For each letter button we verif if the corresponding letter is in the word and we block this button after that
        for( int i=0 ; i < 26 ; i++ ) {
            final String letter = alphabet[i];

            String variableName = "b" + letter;
            findViewById( getResourceId(variableName, "id", getPackageName()) ).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifLetter( letter );
                    // Block button
                }
            });
        }
    }

    public void verifLetter( String letter ){
        boolean present = false;

        for( int i=0 ; i < this.word.length() ; i++ ){
            // If the letter is in the word we'll show all the letters in the word
            if( String.valueOf( this.word.charAt(i) ).equals(letter) ){
                present = true;

                TextView viewLetter;
                // To put the correct id in function of the letter's number
                String variableName = "letter" + String.valueOf(i);
                viewLetter = findViewById(getResourceId( variableName, "id", getPackageName() ));

                if( viewLetter != null ){
                    viewLetter.setText( String.valueOf(word.charAt(i)) );
                }
            }
        }

        if( !present ){
            showOneMoreMistake();
        }
    }

    // Draw one more step of Hangman or defeat
    public void showOneMoreMistake(){
        this.nbMistakes += 1;

        if( this.nbMistakes == 13 ){
            // Print loose & show the word
            // ---- Print loose
            showWord();
        }
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

    public void showWord(){
        for( int i=0 ; i < this.word.length() ; i++ ){
            TextView viewLetter;
            // To put the correct id in function of the letter's number
            String variableName = "letter" + String.valueOf(i);
            viewLetter = findViewById(getResourceId( variableName, "id", getPackageName() ));

            if( viewLetter != null ){
                viewLetter.setText( String.valueOf(word.charAt(i)) );
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
