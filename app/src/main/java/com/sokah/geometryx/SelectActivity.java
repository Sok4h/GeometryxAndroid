package com.sokah.geometryx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView arrowL, arrowR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        arrowL = findViewById(R.id.leftArrow);
        arrowR = findViewById(R.id.rightArrow);
        arrowR.setClickable(true);
        arrowL.setClickable(true);
        arrowL.setOnClickListener(this);
        arrowR.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.leftArrow:

                Log.e("TAG", "onClick: izquierda" );
                break;

            case R.id.rightArrow:

                Log.e("TAG", "onClick: derecha" );
                break;
            default:

                break;
        }
    }
}
