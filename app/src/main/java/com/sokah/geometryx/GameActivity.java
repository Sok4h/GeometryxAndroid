package com.sokah.geometryx;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.gson.Gson;

public class GameActivity extends AppCompatActivity  implements OnMessageListener , SensorEventListener {

    private Button shoot,superShoot;
    private Gson gson = new Gson();
    private TCP_Singleton tcp;
    private SensorManager sensorManager;
    private Sensor acelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Terminar de configurar modo inmersivo
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tcp= TCP_Singleton.getInstance();
        tcp.SetObserver(this);
        acelerometer= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this,acelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        if(acelerometer==null){

            Log.e("TAG", "paila el sensor" );
        }
        setContentView(R.layout.activity_game);
        tcp=TCP_Singleton.getInstance();
        tcp.SetObserver(this);
        shoot=findViewById(R.id.buttonShoot);
        superShoot=findViewById(R.id.buttonSpecial);

        shoot.setOnClickListener(

                (v)->{

                    Shoot tempShoot = new Shoot();
                    String message = gson.toJson(tempShoot);
                    tcp.SendMessage(message);
                    //Log.e("TAG", "Disparé");

                }
        );

        superShoot.setOnClickListener(

                (v)->{

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
        sensorManager.registerListener(this,acelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void OnMessage(String msg) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //.e("X", String.valueOf(event.values[0]));
        //Log.e("Y", String.valueOf(event.values[1]));
        //Log.e("Z", String.valueOf(event.values[2]));

        if(event.values[1]>0.3f){

            Log.e("TAG", "Me muevo Arriba" );
        }

        else if(event.values[1]<0.3f){

            Log.e("TAG", "Me muevo Abajo" );
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}