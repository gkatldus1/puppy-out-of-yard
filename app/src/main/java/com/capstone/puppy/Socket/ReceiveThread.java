package com.capstone.puppy.Socket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread{
    private static final String TAG = "ReceiveThread";

    private Socket socket;
    BufferedReader br = null;
    String receiveData = null;
    String sendData = null;
    int id = 0;
    double lat = 0;//위도
    double lon = 0;//경도

    public ReceiveThread(Socket sock){
        this.socket = sock;
    }

    public int getPuppyId(){
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon(){
        return lon;
    }

    @Override
    public void run() {


        while(!isInterrupted()) {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                receiveData = br.readLine();

                Log.i(TAG, "receive data : " + receiveData);
                parseReceiveData(receiveData);

            } catch (IOException e) {
                e.printStackTrace();
                interrupt(); // 문제가 있는경우 스레드 탈출
            }
        }
    }

    public void parseReceiveData(String data){
        String[] msg = data.split("/");
        int header = Integer.parseInt(msg[0]);

        switch (header){
            case 1:
                id = Integer.parseInt(msg[1]);
                lat = Double.parseDouble(msg[2]);
                lon = Double.parseDouble(msg[3]);
                break;
            case 2:
                break;
        }

    }
}
