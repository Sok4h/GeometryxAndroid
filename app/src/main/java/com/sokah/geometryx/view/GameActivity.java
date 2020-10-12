package com.sokah.geometryx.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sokah.geometryx.R;
import com.sokah.geometryx.comunnication.TCP_Singleton;
import com.sokah.geometryx.events.OnMessageListener;
import com.sokah.geometryx.model.Direction;
import com.sokah.geometryx.model.Shoot;

public class GameActivity extends AppCompatActivity implements OnMessageListener, SensorEventListener {

    private Button shoot, superShoot;
    private Gson gson = new Gson();
    private TCP_Singleton tcp;
    private SensorManager sensorManager;
    private Sensor acelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Terminar de configurar modo inmersivo
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tcp = TCP_Singleton.getInstance();
        tcp.SetObserver(this);
        acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        if (acelerometer == null) {

            Log.e("TAG", "paila el sensor");
        }
        setContentView(R.layout.activity_game);
        tcp = TCP_Singleton.getInstance();
        tcp.SetObserver(this);
        shoot = findViewById(R.id.buttonShoot);
        superShoot = findViewById(R.id.buttonSpecial);

        shoot.setOnClickListener(

                (v) -> {

                    Shoot tempShoot = new Shoot();
                    String message = gson.toJson(tempShoot);
                    tcp.SendMessage(message);
                    //Log.e("TAG", "Disparé");

                }
        );

        superShoot.setOnClickListener(

                (v) -> {

                    Shoot tempShoot = new Shoot();
                    tempShoot.setSuperShoot(true);
                    String message = gson.toJson(tempShoot);
                    tcp.SendMessage(message);
                    //Log.e("TAG", "Disparé super" );

                }
        );
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void OnMessage(String msg) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //.e("X", String.valueOf(event.values[0]));
        //Log.e("Y", String.valueOf(event.values[1]));
        //Log.e("Z", String.valueOf(event.values[2]));


        new Thread(
                () -> {
                    boolean moveX = true;

                    if (event.values[1] > -3) {


                        Log.e("TAG", "Me muevo derecha");
                        Direction dir = new Direction(1);
                        String msgDir = gson.toJson(dir);
                        tcp.SendMessage(msgDir);
                    } else if (event.values[1] < 3) {

                        Log.e("TAG", "Me muevo izquierda");
                        Direction dir = new Direction(-1);
                        String msgDir = gson.toJson(dir);
                        tcp.SendMessage(msgDir);
                    }else{
                        moveX = false;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        ).start();

        new Thread(
                () -> {
                    boolean movey = true;

                    while(movey)

                    if (event.values[0] > -3) {

                        Log.e("TAG", "Me muevo Arriba");
                        Direction dir = new Direction(2);
                        String msgDir = gson.toJson(dir);
                        tcp.SendMessage(msgDir);
                    } else if (event.values[0] < 3) {

                        Log.e("TAG", "Me muevo Abajo");
                        Direction dir = new Direction(-2);
                        String msgDir = gson.toJson(dir);
                        tcp.SendMessage(msgDir);
                    }else{
                        movey = false;
                    }

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        ).start();
        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}