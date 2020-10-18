package com.sokah.geometryx.model;

import android.util.Log;

import com.google.gson.Gson;
import com.sokah.geometryx.comunnication.TCP_Singleton;
import com.sokah.geometryx.view.GameActivity;

public class GameManager {

    private GameActivity gameActivity;
    private float posX;
    private float posY;
    private TCP_Singleton tcp;
    private Gson gson;




    public void setGameActivity(GameActivity gameActivity){

        this.gameActivity= gameActivity;
        tcp = TCP_Singleton.getInstance();
        gson= new Gson();
    }

    public GameManager(){


    }

    public void VerifySensor(int dir){

        new Thread(

                ()->{
                    Direction direction = new Direction(dir);
                    Log.e("TAG", "dato enviado" );
                    String msgDir = gson.toJson(direction);
                    tcp.SendMessage(msgDir);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

        ).start();





    }


}
