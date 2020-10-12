package com.sokah.geometryx.comunnication;

import android.util.Log;

import com.sokah.geometryx.events.OnMessageListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class TCP_Singleton extends Thread{
    private static  TCP_Singleton tcp_singleton;
    private Socket socket;
    private String ip;
    private  int port;
    private BufferedWriter writer;
    private BufferedReader reader;
    private  InputStream is;
    private  OutputStream os;
    boolean infoConection;
    private OnMessageListener observer;

    private TCP_Singleton() {

    }

    public static TCP_Singleton getInstance(){

        if(tcp_singleton == null){

            tcp_singleton= new TCP_Singleton();
        }
       return tcp_singleton;
    }

    public void SetObserver(OnMessageListener observer){

        this.observer=observer;
    }

    public void SetIP_Port(String ip ,int port){
      this.ip=ip;
      this.port=port;
      infoConection=true;
        tcp_singleton.start();
       // Log.e("TAG", infoConection+"" );
    }

    @Override
    public void run() {

        if(infoConection) {
           // Log.e("TAG", "entró");
            try {
                socket = new Socket(this.ip, this.port);

                is = socket.getInputStream();
                os = socket.getOutputStream();
                writer = new BufferedWriter(new OutputStreamWriter(os));
                reader = new BufferedReader(new InputStreamReader(is));

                while (true) {
                    String line;
                    line = reader.readLine();
                    observer.OnMessage(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void SendMessage(String message){
        new Thread(

                ()->{

                    try {
                        writer.write(message+"\n");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }
        ).start();


    }

}
