package com.devperso.benjamin.a2playergame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeedBalloonActivity extends AppCompatActivity {

    //Taille écran
    private int screenWidth;
    private int screenHeight;

    //Ballons
    private Balloon br1;
    /*private Balloon br2;
    private Balloon br3;
    private Balloon br4;*/
    private Balloon bn1;

    //Handler
    private Handler hand = new Handler();

    //Score
    private int score=0;
    private TextView scoreprint;

    //Timer
    private TextView timertxt;
    private CountDownTimer cdtimer;
    private long timeleft = 60000;  //en millisecondes

    //Dialogue
    AlertDialog.Builder builder;

    //Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_balloon);

        //Connection avec les objets XML
        br1 = new Balloon((ImageView)findViewById(R.id.balloonred1),"red");
        bn1 = new Balloon((ImageView)findViewById(R.id.balloonblack1),"black");
        scoreprint = (TextView) findViewById(R.id.scoretotal);
        timertxt = (TextView) findViewById(R.id.minuteur);
        updatescore();

        //Taille ecran
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //Explication des règles et lancement
        builder= new AlertDialog.Builder(this);
        builder.setMessage("## Tour du Joueur 1 ##\nRègles : Eclater les ballons rouges mais éviter les ballons noirs, vous avez 1 minute !");
        builder.setPositiveButton("C'est parti mon kiki !", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                //Minuteur
                cdtimer = new CountDownTimer(timeleft, 1000) {
                    @Override
                    public void onTick(long l) {    //chaque seconde
                        timeleft=l;
                        updateTimer();
                    }

                    @Override
                    public void onFinish() {        //a la fin on affiche le score

                        String s = "Ton score : " + score;
                        builder.setMessage(s);
                        AlertDialog alert2 =builder.create();
                        alert2.show();
                    }
                }.start();

                //actions ballons
                br1.getIv().setVisibility(View.VISIBLE);
                bn1.getIv().setVisibility(View.VISIBLE);
                hand.postDelayed(animatedBalloon, 1);
            }
        });

        AlertDialog alert =builder.create();
        alert.show();
    }

    //thread pour deplacer les ballons
    private Runnable animatedBalloon = new Runnable() {
        @Override
        public void run() {
            changePos(br1);
            changePos(bn1);
            hand.postDelayed(this, 1);
        }
    };

    //Listener lorsque l on touche un ballon
    public void burstballoon(View v){

        v.setX(-200.0f);       //hors ecran
        v.setY(-200.0f);       //hors ecran

        switch (v.getId()) {        //en fonction du ballon éclaté
            case  R.id.balloonred1: {

                br1.setX(-200.0f);
                br1.setY(-200.0f);
                score += br1.getScore();
                break;
            }

            case R.id.balloonblack1: {
                bn1.setX(-200.0f);
                bn1.setY(-200.0f);
                score += bn1.getScore();
                break;
            }
        }
        updatescore();
    }

    //Mise à jour de l'affichage du score
    public void updatescore(){
        scoreprint.setText(Integer.toString(score));
    }

    //Mise à jour de l'affichage du minuteur
    public void updateTimer(){

        int minutes = (int) timeleft/60000;
        int secondes = (int) timeleft % 60000 / 1000;
        String temps;
        temps = ""+ minutes;
        temps += ":";
        if(secondes<10) temps += "0";
        temps += secondes;
        timertxt.setText(temps);
    }

    //Deplacement de sballons
    public void changePos(Balloon b){

        //Avancée du ballon
        b.setY(b.getY()-20);
        //Si ballon hors écran, on le fait repop en bas à un X aleatoire
        if(b.getIv().getY()+b.getIv().getHeight()<0){
            b.setX((float)Math.floor(Math.random()*(screenWidth - b.getIv().getWidth())));
            b.setY(screenHeight + 100.0f);
        }
        //Deplacement ballon
        b.getIv().setX(b.getX());
        b.getIv().setY(b.getY());
    }
}
