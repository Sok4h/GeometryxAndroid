package com.sokah.geometryx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener,OnMessageListener {

    ImageView arrowL, arrowR,spacheship;
    Button btnReady;
    EditText inputName;
    TCP_Singleton tcp;
    String name,message;
    User user;
    int currentSpaceShip = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        arrowL = findViewById(R.id.leftArrow);
        arrowR = findViewById(R.id.rightArrow);
        btnReady = findViewById(R.id.buttonReady);
        spacheship = findViewById(R.id.ivNave);
        inputName = findViewById(R.id.inputName);
        arrowR.setClickable(true);
        arrowL.setClickable(true);
        tcp= TCP_Singleton.getInstance();
        tcp.SetObserver(this);
        btnReady.setOnClickListener(this);
        arrowL.setOnClickListener(this);
        arrowR.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.leftArrow:

                //codigo flecha izquierda

                Log.e("TAG", "onClick: izquierda" );

               if(currentSpaceShip==0){

                   currentSpaceShip=3;
               }

               else {

                   currentSpaceShip--;
               }

                SetSpaceship();
                break;

            case R.id.rightArrow:

                //codigo flecha derecha

                if(currentSpaceShip==3){

                    currentSpaceShip=0;
                }

                else {

                    currentSpaceShip++;
                }

                Log.e("TAG", String.valueOf(currentSpaceShip));

                SetSpaceship();

                Log.e("TAG", "onClick: derecha" );
                break;

                //boton continuar

            case R.id.buttonReady:

                Gson gson= new Gson();

                switch (currentSpaceShip){

                    case 0:

                         name = inputName.getText().toString();

                        Triangletrix nave = new Triangletrix(100,100);

                        user = new User(name,nave);
                        message = gson.toJson(user);
                        tcp.SendMessage(message);

                        break;
                    case 1:
                         name = inputName.getText().toString();

                        Diamondrox diamondrox = new Diamondrox(100,100);

                         user = new User(name,diamondrox);
                        message = gson.toJson(user);
                        tcp.SendMessage(message);
                        break;
                    case 2:
                        name = inputName.getText().toString();

                        Circletlex circletlex = new Circletlex(100,100);

                        user = new User(name,circletlex);
                        message = gson.toJson(user);
                        tcp.SendMessage(message);
                        break;
                    case 3:
                        name = inputName.getText().toString();
                        Squarlux squarlux = new Squarlux(100,100);
                        user = new User(name,squarlux);
                        message = gson.toJson(user);
                        tcp.SendMessage(message);
                        break;
                }

                Intent intent = new Intent(this,GameActivity.class);
                startActivity(intent);
               // Log.e("TAG", "onClick:  button" );
                break;
            default:

                break;

        }
    }

    public void SetSpaceship(){


        switch (currentSpaceShip){

            case 0:

                spacheship.setImageResource(R.drawable.ic_nave_1);
                break;

            case 1:
                spacheship.setImageResource(R.drawable.ic_nave_2);
                break;

            case 2:

                spacheship.setImageResource(R.drawable.ic_nave_3);
                break;

            case 3:
                spacheship.setImageResource(R.drawable.ic_nave_4);
                break;
            default:

                break;


        }

    }

    @Override
    public void OnMessage(String msg) {

    }
}
