package com.sokah.geometryx.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.sokah.geometryx.events.OnMessageListener;
import com.sokah.geometryx.R;
import com.sokah.geometryx.comunnication.TCP_Singleton;

public class MainActivity extends AppCompatActivity implements OnMessageListener {

    private Button start;
    EditText ip,port;
    private TCP_Singleton tcp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start =findViewById(R.id.buttonStart);
        tcp = TCP_Singleton.getInstance();
        tcp.SetObserver(this);
        ip= findViewById(R.id.inputIp);
        port = findViewById(R.id.inputPort);

        start.setOnClickListener(

                (v)->{

                    Intent intent = new Intent(this, SelectActivity.class);
                   //tcp.SetIP_Port(ip.getText().toString(),Integer.parseInt(port.getText().toString()));
                    tcp.SetIP_Port("192.168.0.11",5000);
                    startActivity(intent);

                }
        );
    }

    @Override
    public void OnMessage(String msg) {

    }
}