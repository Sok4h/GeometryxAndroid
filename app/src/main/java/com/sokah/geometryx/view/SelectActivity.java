package com.sokah.geometryx.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sokah.geometryx.events.OnMessageListener;
import com.sokah.geometryx.R;
import com.sokah.geometryx.comunnication.TCP_Singleton;
import com.sokah.geometryx.model.Confirmation;
import com.sokah.geometryx.model.User;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener, OnMessageListener {

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

                //Log.e("TAG", "onClick: izquierda" );

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

                //Log.e("TAG", String.valueOf(currentSpaceShip));

                SetSpaceship();

               //Log.e("TAG", "onClick: derecha" );
                break;

                //boton continuar



            case R.id.buttonReady:

                Gson gson= new Gson();
                if(inputName.getText().toString().isEmpty()){

                    Toast.makeText(this, "Por favor ingrese un nombre", Toast.LENGTH_SHORT).show();
                    break;
                }

                    switch (currentSpaceShip) {

                        case 0:

                            name = inputName.getText().toString();

                            user = new User(name, 0);
                            message = gson.toJson(user);
                            tcp.SendMessage(message);
                            Log.e("TAG", "Azul");

                            break;
                        case 1:
                            name = inputName.getText().toString();
                            user = new User(name, 1);
                            Log.e("TAG", "NARANJA");
                            message = gson.toJson(user);
                            tcp.SendMessage(message);
                            break;
                        case 2:
                            name = inputName.getText().toString();
                            user = new User(name, 2);
                            Log.e("TAG", "morado");
                            message = gson.toJson(user);
                            tcp.SendMessage(message);
                            break;
                        case 3:
                            name = inputName.getText().toString();
                            Log.e("TAG", String.valueOf(currentSpaceShip));
                            user = new User(name, 3);
                            message = gson.toJson(user);
                            tcp.SendMessage(message);
                            break;
                    }

                Intent intent= new Intent(this, LoadingActivity.class);
                startActivity(intent);
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
    public void OnImpact() {

    }

    @Override
    public void OnConfirmation() {

    }
}
