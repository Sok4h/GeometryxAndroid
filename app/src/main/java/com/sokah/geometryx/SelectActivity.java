package com.sokah.geometryx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView arrowL, arrowR,spacheship;
    Button btnReady;
    int currentSpaceShip = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        arrowL = findViewById(R.id.leftArrow);
        arrowR = findViewById(R.id.rightArrow);
        btnReady = findViewById(R.id.buttonReady);
        spacheship = findViewById(R.id.ivNave);
        arrowR.setClickable(true);
        arrowL.setClickable(true);
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

            case R.id.buttonReady:



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
}
