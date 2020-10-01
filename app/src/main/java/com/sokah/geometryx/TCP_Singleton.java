package com.sokah.geometryx;

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
    private BufferedWriter writer;
    private BufferedReader reader;
    private  InputStream is;
    private  OutputStream os;
    private  OnMessageListener observer;
    //private OnMessageListener observer;


    public TCP_Singleton() {


    }

    public static TCP_Singleton getInstance(){

        if(tcp_singleton == null){

            tcp_singleton= new TCP_Singleton();
            tcp_singleton.start();
        }
       return tcp_singleton;
    }

    public void SetObserver(OnMessageListener observer){

        this.observer=observer;
    }


    public void run (String ip,int port){

        try {
            socket = new Socket(ip,port);
             is = socket.getInputStream();
             os = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(is));

            while (true){
                String line;
                line =reader.readLine();
                observer.OnMessage(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  void SendMessage(String message){
        new Thread(

                ()->{

                    try {
                        writer.write(message);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }
        ).start();


    }

}
