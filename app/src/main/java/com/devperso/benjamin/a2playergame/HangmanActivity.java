package com.devperso.benjamin.a2playergame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.Collator;
import java.text.Normalizer;
import java.util.Locale;
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
        createButtonLetters();

        // For each letter button we verif if the corresponding letter is in the word and we block this button after that
       for( int i=0 ; i < 26 ; i++ ) {
            final String letter = alphabet[i];

            String variableName = "b" + letter;
            final Button actual = findViewById( getResourceId(variableName, "id", getPackageName()) );
            actual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifLetter( letter );
                    // Block button
                    actual.setEnabled(false);
                }
            });
        }
    }

    public void createButtonLetters(){
        LinearLayout line1 = findViewById(R.id.line1);
        LinearLayout line2 = findViewById(R.id.line2);
        LinearLayout line3 = findViewById(R.id.line3);

        for( int i=0 ; i < 26 ; i++ ){
            Button buttonLetter = new Button( this.cont );
            buttonLetter.setText( alphabet[i] );
            String variableName = "b" + alphabet[i];
            buttonLetter.setId( getResourceId(variableName, "id", getPackageName()) );
            ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f );
            buttonLetter.setLayoutParams(lp);

            if( i < 10 ){
                line1.addView( buttonLetter );
            }
            else if( i < 20 ){
                line2.addView( buttonLetter );
            }
            else{
                line3.addView( buttonLetter );
            }
        }
    }

    public void verifLetter( String letter ){
        boolean present = false;

        for( int i=0 ; i < this.word.length() ; i++ ){
            // If the letter is in the word we'll show all the letters in the word
            if( sameChar( String.valueOf(this.word.charAt(i)), letter ) ){
                present = true;

                TextView viewLetter;
                // To put the correct id in function of the letter's number
                String variableName = "letter" + String.valueOf(i);
                viewLetter = findViewById( getResourceId( variableName, "id", getPackageName() ) );

                if( viewLetter != null ){
                    viewLetter.setText( String.valueOf(word.charAt(i)) );
                }
            }
        }

        if( !present ){
            showOneMoreMistake();
        }
    }

    public static boolean sameChar( String a, String b ){
        //Collator permet de faire des comparaison entre chaine de caracteres
        Collator compareChar = Collator.getInstance(Locale.FRENCH);
        //Primary annule accent et case (secondary annule que case et tertiary = .equal())
        compareChar.setStrength(Collator.PRIMARY);
        int res = compareChar.compare(a, b); //0--> identiques, -1 --> differents

        return (res == 0);
    }

    // Draw one more step of Hangman or defeat
    public void showOneMoreMistake(){
        this.nbMistakes += 1;

        ImageView hangman = findViewById(R.id.imageHangman);
        String variableName = "mistake" + String.valueOf(this.nbMistakes);
        hangman.setImageResource( getResourceId( variableName, "drawable", getPackageName() ) );

        if( this.nbMistakes == 11 ){
            // Print loose & show the word & disable all buttons
            new AlertDialog.Builder( this.cont ).setMessage( getString(R.string.looseMessage) ).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick( DialogInterface dialog, int which ) {
                    dialog.cancel();
                }
            }).setCancelable(false).show();

            for( int i=0 ; i < 26 ; i++ ) {
                final String letter = alphabet[i];

                String idButton = "b" + letter;
                final Button actual = findViewById( getResourceId( idButton, "id", getPackageName() ) );
                actual.setEnabled(false);
            }

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
