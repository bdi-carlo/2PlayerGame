package com.devperso.benjamin.a2playergame;

import android.widget.ImageView;

public class Balloon {

    private int score;
    private String color;
    private ImageView iv;
    private float x;
    private float y;

    //Constructeur
    public Balloon(ImageView iv, String col){

        this.iv = iv;       //image
        this.iv.setY(-200.0f);       //hors ecran
        this.y = -200;
        this.color = col;       //couleur ballon
        if(col.equals("red")) this.score=10;        //score quand éclater
        else this.score=-30;
    }

    //Getter
    public ImageView getIv() {
        return iv;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public int getScore(){return score;}

    //Setter
    public void setX(float x2){
        this.x = x2;
    }

    public void setY(float y2){
        this.y = y2;
    }

}