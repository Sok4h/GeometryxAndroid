package com.sokah.geometryx.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.sokah.geometryx.R;
import com.sokah.geometryx.comunnication.TCP_Singleton;
import com.sokah.geometryx.events.OnMessageListener;

public class LoadingActivity extends AppCompatActivity implements OnMessageListener {
     TCP_Singleton tcp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tcp = TCP_Singleton.getInstance();
        tcp.SetObserver(this);
    }

    @Override
    public void OnImpact() {

    }

    @Override
    public void OnConfirmation() {

        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);

    }
}