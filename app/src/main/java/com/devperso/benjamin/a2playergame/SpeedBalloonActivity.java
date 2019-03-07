package com.devperso.benjamin.a2playergame;

import android.app.AlertDialog;
import android.content.Context;
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

    private Context cont;

    //Taille écran
    private int screenWidth;
    private int screenHeight;

    //Ballons
    private Balloon br1;
    private Balloon bp1;
    private Balloon bg1;
    private Balloon by1;
    private Balloon bn1;
    private Balloon bn2;

    private float vitesse=1;

    //nuages
    private ImageView nuage1;
    private ImageView nuage2;

    //Handler
    private Handler hand = new Handler();

    //Score
    private int player = 1;
    private int score=0;
    private int scorej1;
    private TextView scoreprint;

    //Timer
    private TextView timertxt;
    private CountDownTimer cdtimer;
    private long timeleft = 60000;  //en millisecondes

    //Dialogue
    AlertDialog.Builder builder;
    AlertDialog.Builder resultat;


    //Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_balloon);

        this.cont = this;

        //Connection avec les objets XML
        br1 = new Balloon((ImageView)findViewById(R.id.balloonred1),"red");
        bg1 = new Balloon((ImageView)findViewById(R.id.balloongreen1),"green");
        by1 = new Balloon((ImageView)findViewById(R.id.balloonyellow1),"yellow");
        bp1 = new Balloon((ImageView)findViewById(R.id.balloonpurple1),"purple");
        bn1 = new Balloon((ImageView)findViewById(R.id.balloonblack1),"black");
        bn2 = new Balloon((ImageView)findViewById(R.id.balloonblack2),"black");
        nuage1 = (ImageView) findViewById(R.id.nuage1);
        nuage2 = (ImageView) findViewById(R.id.nuage2);
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
        System.out.println(screenHeight);

        //Explication des règles et lancement
        builder= new AlertDialog.Builder(this).setCancelable(false);
        resultat = new AlertDialog.Builder(this).setCancelable(false);

        //Creation des boites de dialogue
        builder.setMessage("## Tour du Joueur 1 ##\nRègles : Eclater les ballons rouges mais éviter les ballons noirs, vous avez 1 minute !");
        builder.setPositiveButton("Go !", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                play();
            }
        });
        resultat.setPositiveButton("Menu", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        AlertDialog alert =builder.create();
        alert.show();
    }

    public void play(){

        //Minuteur
        cdtimer = new CountDownTimer(timeleft, 1000) {
            @Override
            public void onTick(long l) {    //chaque seconde
                timeleft=l;
                updateTimer();
                //Augmentation de la difficulté
                if(l<=50000 && vitesse==1) vitesse = (float)1.25;
                if(l<=40000 && vitesse==1.25) vitesse = (float)1.5;
                if(l<=30000 && l>15000 && nuage1.getVisibility()!=View.VISIBLE) nuage1.setVisibility(View.VISIBLE);
                if(l<=15000 && nuage2.getVisibility()!=View.VISIBLE){
                    nuage1.setVisibility(View.GONE);
                    nuage2.setVisibility(View.VISIBLE);
                }
            }

            //Action quand le minuteur atteint 0
            @Override
            public void onFinish() {        //a la fin on affiche le score

                timeleft=0;
                updateTimer();
                String s = getString(R.string.score) + score;

                hand.removeCallbacks(animatedBalloon);
                br1.setY(-200.0f);
                bn1.setY(-200.0f);
                bn2.setY(-200.0f);
                by1.setY(-200.0f);
                bg1.setY(-200.0f);
                bp1.setY(-200.0f);
                AlertDialog alert3;

                if(player==1) { //le joueur 1 viens de jouer

                    s += "\n## Tour du Joueur 2 ##\nLe joueur 2 " + getString(R.string.better);
                    player=2;
                    timeleft=60000;
                    nuage1.setVisibility(View.GONE);
                    nuage2.setVisibility(View.GONE);
                    vitesse=1;
                    scorej1=score;
                    score=0;
                    builder.setMessage(s);
                    alert3 = builder.create();
                }
                else{       //le joueur 2 viens de jouer

                    s += "\n\n##" + scorej1 + " VS " + score  + "##\n";
                    if(score>scorej1) s+= "Le joueur 2 " + getString(R.string.win);
                    else s+= "Le joueur 1 " + getString(R.string.win);
                    resultat.setMessage(s);
                    alert3 = resultat.create();
                }
                alert3.show();
            }
        }.start();

        //actions ballons
        br1.getIv().setVisibility(View.VISIBLE);
        bn1.getIv().setVisibility(View.VISIBLE);
        bg1.getIv().setVisibility(View.VISIBLE);
        by1.getIv().setVisibility(View.VISIBLE);
        bp1.getIv().setVisibility(View.VISIBLE);
        bn2.getIv().setVisibility(View.VISIBLE);
        hand.postDelayed(animatedBalloon, 1);
    }



    //thread pour deplacer les ballons
    private Runnable animatedBalloon = new Runnable() {
        @Override
        public void run() {
            changePos(bn1);
            changePos(bn2);
            changePos(br1);
            changePos(bg1);
            changePos(by1);
            changePos(bp1);
            hand.postDelayed(this, 1);
        }
    };

    //Listener lorsque l on touche un ballon
    public void burstballoon(View v){

        v.setY(-300.0f);       //hors ecran
        switch (v.getId()) {        //en fonction du ballon éclaté
            case  R.id.balloonred1: {

                br1.setY(-300.0f);
                score += br1.getScore();
                break;
            }
            case R.id.balloonblack1: {

                bn1.setY(-300.0f);
                score += bn1.getScore();
                break;
            }
            case  R.id.balloonblack2: {

                bn2.setY(-300.0f);
                score += bn2.getScore();
                break;
            }
            case  R.id.balloongreen1: {

                bg1.setY(-300.0f);
                score += bg1.getScore();
                break;
            }
            case  R.id.balloonyellow1: {

                by1.setY(-300.0f);
                score += by1.getScore();
                break;
            }
            case  R.id.balloonpurple1: {

                bp1.setY(-300.0f);
                score += bp1.getScore();
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

    //Deplacement des ballons
    public void changePos(Balloon b){

        int alea;
        float x = 0;
        //Avancée du ballon
        b.setY(b.getY()-((screenHeight/170)*vitesse));
        //Si ballon hors écran, on le fait repop en bas à un X aleatoire
        if(b.getIv().getY()+b.getIv().getHeight()<0){
            x = (float)Math.floor(Math.random()*(screenWidth - b.getIv().getWidth()));
            b.setX(x);
            alea = (int)(100 + (Math.random() * ((screenHeight/2)-100)));
            b.setY(screenHeight + alea);
        }
        //Deplacement de l'imageview du ballon
        b.getIv().setX(b.getX());
        b.getIv().setY(b.getY());
    }

    // Ask to the player if he really wants to leave
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder( this.cont ).setMessage( getString(R.string.leaveMessage) ).setPositiveButton( getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                hand.removeCallbacks(animatedBalloon);
                cdtimer.cancel();
                dialog.cancel();
                finish();
            }
        }).setNegativeButton( getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setCancelable(false).show();
    }
}
