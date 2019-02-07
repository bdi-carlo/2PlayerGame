package com.devperso.benjamin.a2playergame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TicTacToeActivity extends AppCompatActivity {

    public int statut; //0: playing / 1: victory / 2: tie
    private Case[] cases;
    private int tour;
    private TextView text;
    public Context cont;
    private String player1;
    private String player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        Intent intent = getIntent();
        player1 = intent.getStringExtra("player1");
        player2 = intent.getStringExtra("player2");

        this.tour = 1;
        this.text = findViewById( R.id.textView);
        this.statut = 0;
        this.cont = getBaseContext();
        setText();

        cases = new Case[9];
        cases[0] = new Case( this, getBaseContext(), (ImageView) findViewById(R.id.imageView1) );
        cases[1] = new Case( this, getBaseContext(), (ImageView) findViewById(R.id.imageView2) );
        cases[2] = new Case( this, getBaseContext(), (ImageView) findViewById(R.id.imageView3) );
        cases[3] = new Case( this, getBaseContext(), (ImageView) findViewById(R.id.imageView4) );
        cases[4] = new Case( this, getBaseContext(), (ImageView) findViewById(R.id.imageView5) );
        cases[5] = new Case( this, getBaseContext(), (ImageView) findViewById(R.id.imageView6) );
        cases[6] = new Case( this, getBaseContext(), (ImageView) findViewById(R.id.imageView7) );
        cases[7] = new Case( this, getBaseContext(), (ImageView) findViewById(R.id.imageView8) );
        cases[8] = new Case( this, getBaseContext(), (ImageView) findViewById(R.id.imageView9) );

        for ( int i=0 ; i < 9 ; i++ ){
            cases[i].setClickListener();
        }

        //Bouton reset
        findViewById(R.id.bReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for( int i=0 ; i < 9 ; i++ ){
                    cases[i].reset();
                    statut = 0;
                    tour = 1;
                    setText();
                }
            }
        });

        //Bouton retour
        findViewById(R.id.bRetour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public int getTour(){
        return this.tour;
    }

    public void changeTour(){
        if( this.tour == 1 )
            this.tour = 2;
        else
            this.tour = 1;
    }

    public void checkWin( int joueur ){
        int win = 0;

        //Vérif horizontal
        if( (cases[0].getJoueur() == joueur && cases[1].getJoueur() == joueur && cases[2].getJoueur() == joueur) ||
                (cases[3].getJoueur() == joueur && cases[4].getJoueur() == joueur && cases[5].getJoueur() == joueur) ||
                (cases[6].getJoueur() == joueur && cases[7].getJoueur() == joueur && cases[8].getJoueur() == joueur) ){
            win = 1;
        }
        //Vérif vertical
        else if( (cases[0].getJoueur() == joueur && cases[3].getJoueur() == joueur && cases[6].getJoueur() == joueur) ||
                (cases[1].getJoueur() == joueur && cases[4].getJoueur() == joueur && cases[7].getJoueur() == joueur) ||
                (cases[2].getJoueur() == joueur && cases[5].getJoueur() == joueur && cases[8].getJoueur() == joueur) ){
            win = 1;
        }
        //Vérif diagonale
        else if( (cases[0].getJoueur() == joueur && cases[4].getJoueur() == joueur && cases[8].getJoueur() == joueur) ||
                (cases[2].getJoueur() == joueur && cases[4].getJoueur() == joueur && cases[6].getJoueur() == joueur) ){
            win = 1;
        }

        boolean tie = true;
        for( int i=0 ; i < 9 ; i++ ){
            if( cases[i].getJoueur() == 0 )
                tie = false;
        }

        if( tie ){
            this.statut = 2;
            this.text.setText( R.string.tie );
        }

        if( win == 1 ){
            this.statut = 1;
            if( joueur == 1 )
                this.text.setText( this.player1 + " " + getString(R.string.win) );
            else
                this.text.setText( this.player2 + " " + getString(R.string.win) );
        }
    }

    public void setText(){
        if( this.tour == 1 )
            this.text.setText( this.player1 );
        else
            this.text.setText( this.player2 );
    }

}
