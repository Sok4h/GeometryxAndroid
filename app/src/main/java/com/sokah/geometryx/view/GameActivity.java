package com.sokah.geometryx.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
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
import com.sokah.geometryx.model.GameManager;
import com.sokah.geometryx.model.Shoot;

public class GameActivity extends AppCompatActivity implements OnMessageListener, SensorEventListener {

    private Button shoot, superShoot;
    private Gson gson = new Gson();
    private TCP_Singleton tcp;
    private SensorManager sensorManager;
    private Sensor acelerometer;
    private GameManager gameManager;
    private Vibrator vibrator;
    private float posX,posY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Terminar de configurar modo inmersivo
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        tcp = TCP_Singleton.getInstance();
        tcp.SetObserver(this);
        gameManager= new GameManager();
        gameManager.setGameActivity(this);
        //gameManager.start();
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

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

       /* Log.e("X", String.valueOf(event.values[0]));
        Log.e("Y", String.valueOf(event.values[1]));
        Log.e("Z", String.valueOf(event.values[2]));
*/
         float x= event.values[1];
        float y= event.values[2];


        new Thread(

                ()->{



                    boolean senx=true;
                    while (senx) {
                        if (x < -2) {
                            gameManager.VerifySensor(-1);
                            Direction dir = new Direction(-1);
                            String msgDir = gson.toJson(dir);

                            //tcp.SendMessage(msgDir);


                        } else if (x > 2) {

                            gameManager.VerifySensor(1);

                            //Log.e("TAG", "Me muevo derecha");
                            Direction dir = new Direction(1);
                            String msgDir = gson.toJson(dir);
                            //tcp.SendMessage(msgDir);

                        } else {
                            // shoot.setVisibility(View.VISIBLE);
                            //superShoot.setVisibility(View.VISIBLE);
                            senx=false;
                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();


        //sensor arriba y abajo
        new Thread(

                ()->{

                    boolean seny=true;

                    while (seny) {

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (y < -2) {

                            gameManager.VerifySensor(-2);

                            // Log.e("TAG", "Menor a 2");
                            Direction dir = new Direction(-2);
                            String msgDir = gson.toJson(dir);
                            //Log.e("TAG", String.valueOf(y));
                            //tcp.SendMessage(msgDir);
                        } else if (y >2 ) {

                            gameManager.VerifySensor(2);
                            //Log.e("TAG", "Me muevo arriba");
                            Direction dir = new Direction(2);
                            String msgDir = gson.toJson(dir);
                            //tcp.SendMessage(msgDir);
                            //Log.e("TAG"," mayor a 2");


                        }
                        else {
                            seny=false;
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();


    }






    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void OnImpact() {

        vibrator.vibrate(200);

    }
}