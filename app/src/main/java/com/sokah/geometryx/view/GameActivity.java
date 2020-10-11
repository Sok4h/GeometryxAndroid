package com.sokah.geometryx.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;
import com.sokah.geometryx.events.OnMessageListener;
import com.sokah.geometryx.R;
import com.sokah.geometryx.comunnication.TCP_Singleton;
import com.sokah.geometryx.model.Shoot;

public class GameActivity extends AppCompatActivity  implements OnMessageListener {

    Button shoot,superShoot;
    Gson gson = new Gson();
    TCP_Singleton tcp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Terminar de configurar modo inmersivo
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


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
    public void OnMessage(String msg) {

    }
}