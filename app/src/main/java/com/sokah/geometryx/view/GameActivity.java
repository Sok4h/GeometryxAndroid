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
import com.sokah.geometryx.model.Shoot;

public class GameActivity extends AppCompatActivity implements OnMessageListener, SensorEventListener {

    private Button shoot, superShoot;
    private Gson gson = new Gson();
    private TCP_Singleton tcp;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Vibrator vibrator;
    private float[] mRotationMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Terminar de configurar modo inmersivo
        /*
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        if (acelerometer == null) {

            Log.e("TAG", "paila el sensor");
        }*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        tcp = TCP_Singleton.getInstance();
        tcp.SetObserver(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        mRotationMatrix = new float[16];
        mRotationMatrix[ 0] = 1;
        mRotationMatrix[ 4] = 1;
        mRotationMatrix[ 8] = 1;
        mRotationMatrix[12] = 1;



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
    public void onSensorChanged(SensorEvent event) {

        float axisX = event.values[0];
        float axisY = event.values[1];
        float axisZ = event.values[2];
        double angleXY = Math.toDegrees(Math.atan(axisY/axisX));
        int anguloX = (int)angleXY;

        float[] results = new float[3];
        SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values);
        SensorManager.getOrientation(mRotationMatrix, results);
        double angleZ = Math.toDegrees(results[2]);
       int anguloZ = (int)angleZ;

        new Thread(
                () -> {
                    boolean moveX = true;

                    while (moveX) {

                        if (anguloX > 50) {


                            Log.e("TAG", "Me muevo derecha");
                            Direction dir = new Direction(-1);
                            String msgDir = gson.toJson(dir);
                            tcp.SendMessage(msgDir);
                        } else if (anguloX < 40) {

                            Log.e("TAG", "Me muevo izquierda");
                            Direction dir = new Direction(1);
                            String msgDir = gson.toJson(dir);
                            tcp.SendMessage(msgDir);
                        } else {
                            moveX = false;
                        }
                        try {
                            Thread.sleep(450);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

        new Thread(
                () -> {
                    boolean movey = true;

                    while (movey) {

                        if ( anguloZ> -65) {

                            Log.e("TAG", "Me muevo Arriba");
                            Direction dir = new Direction(2);
                            String msgDir = gson.toJson(dir);
                            tcp.SendMessage(msgDir);
                        } else if (anguloZ < -85) {

                            Log.e("TAG", "Me muevo Abajo");
                            Direction dir = new Direction(-2);
                            String msgDir = gson.toJson(dir);
                            tcp.SendMessage(msgDir);
                        } else {
                            movey = false;
                        }

                        try {
                            Thread.sleep(450);
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