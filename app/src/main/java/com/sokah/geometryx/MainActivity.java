package com.sokah.geometryx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start =findViewById(R.id.buttonStart);
        start.setOnClickListener(

                (v)->{

                    Intent intent = new Intent(this,SelectActivity.class);
                    startActivity(intent);

                }
        );
    }
}
