package com.devperso.benjamin.a2playergame;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Case {

    private int joueur;
    private TicTacToeActivity game;
    private ImageView imageView;
    private Context cont;

    public Case( TicTacToeActivity aGame, Context cont, ImageView aButton ){
        this.joueur = 0;
        this.imageView = aButton;
        this.cont = cont;
        this.game = aGame;
    }

    public void popUp( String message ) {
        Toast toast = Toast.makeText( cont, message, Toast.LENGTH_LONG );
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void changeImage(){
        if( this.game.statut != 0 )
            popUp( this.game.cont.getString(R.string.finish) );
        else {
            if (this.joueur != 0)
                popUp( this.game.cont.getString(R.string.full) );
            else {
                this.joueur = this.game.getTour();
                if (this.joueur == 1)
                    this.imageView.setImageResource(R.drawable.trump);
                else
                    this.imageView.setImageResource(R.drawable.poutine);

                this.game.changeTour();
                this.game.setText();
                this.game.checkWin(this.joueur);
            }
        }
    }

    public void setClickListener(){
        this.imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });
    }

    public void reset(){
        this.joueur = 0;
        this.imageView.setImageResource(R.drawable.empty);
    }

    public int getJoueur(){
        return this.joueur;
    }

}
