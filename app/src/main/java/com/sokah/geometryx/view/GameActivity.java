package com.sokah.geometryx.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
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
import com.sokah.geometryx.model.Vibration;

public class GameActivity extends AppCompatActivity implements OnMessageListener, SensorEventListener {

    private Button shoot, superShoot;
    private Gson gson = new Gson();
    private TCP_Singleton tcp;
    private SensorManager sensorManager;
    private Sensor acelerometer;
    private Vibrator vibrator;

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
        acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_GAME);
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
                }
        );

        superShoot.setOnClickListener(

                (v) -> {
                    Shoot tempShoot = new Shoot();
                    tempShoot.setSuperShoot(true);
                    String message = gson.toJson(tempShoot);
                    tcp.SendMessage(message);
                }
        );
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_GAME);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        float x= event.values[1];
        float y= event.values[2];

            new Thread(
                    () -> {
                        if (x < -2) {

                            Direction dir = new Direction(-1);
                            String msgDir = gson.toJson(dir);
                            tcp.SendMessage(msgDir);

                        } else if (x > 2) {


                            Direction dir = new Direction(1);
                            String msgDir = gson.toJson(dir);
                            tcp.SendMessage(msgDir);
                        } else {

                            Direction dir = new Direction(0);
                            String msgDir = gson.toJson(dir);
                            tcp.SendMessage(msgDir);

                        }
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

            ).start();

                    new Thread(

                            ()->{

                                try {
                                    Thread.sleep(40);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (y < -2) {

                                    Direction dir = new Direction(-2);
                                    String msgDir = gson.toJson(dir);
                                    tcp.SendMessage(msgDir);

                                } else if (y >2 ) {
                                    Direction dir = new Direction(2);
                                    String msgDir = gson.toJson(dir);
                                    tcp.SendMessage(msgDir);


                                }
                                else {
                                    Direction dir = new Direction(0);
                                    String msgDir = gson.toJson(dir);
                                    tcp.SendMessage(msgDir);

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

    @Override
    public void OnConfirmation() {

    }
}