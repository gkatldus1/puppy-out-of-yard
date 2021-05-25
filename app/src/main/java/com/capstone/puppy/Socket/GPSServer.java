package com.capstone.puppy.Socket;

import android.util.Log;

import com.capstone.puppy.PuppyInfo.GPSInfo;
import com.capstone.puppy.PuppyInfo.PuppyInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
header(byte)
    0 : cmd
    1 : gps data
*/
public class GPSServer extends Thread{
    private static final String TAG = "GPSServer";
    List<PuppyInfo> puppys;
    BufferedReader br = null;
    String receiveData = null;
    private int id = 0;
    private double lat = 0;//위도
    private double lon = 0;//경도
    private String date;

    public GPSServer(List<PuppyInfo> puppys){
        this.puppys = puppys;
    }

    public GPSInfo getGPSInfo(){
        return new GPSInfo(lat, lon, date);
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
        Log.i(TAG, "GPS Server Start");

        try {
            ServerSocket serverSocket = new ServerSocket(1818);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Log.i(TAG, "Socket Accepted");

                try {
                    while (true) {
                        sleep(100);
                        br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        receiveData = br.readLine();

                        Log.i(TAG, "receive data : " + receiveData);
                        parseReceiveData(receiveData);
                    }
                }catch (Exception e){
                    if(e instanceof IOException)
                        Log.i(TAG, "IOException");
                    if(e instanceof InterruptedException)
                        Log.i(TAG, "InterruptedException");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                date = sdf.format(new Date(System.currentTimeMillis()));
                break;
            case 2:
                break;
        }
    }
}