package com.capstone.puppy.Socket;

import android.util.Log;

import com.capstone.puppy.PuppyInfo.GpsInfo;
import com.capstone.puppy.PuppyInfo.PuppyInfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/*
header(byte)
    0 : cmd
    1 : gps data

*/
public class GPSServer extends Thread{
    private static final String TAG = "GPSServer";

    double x, y;
    ArrayList<PuppyInfo> puppys;

    @Override
    public void run() {
        Log.i(TAG, "GPS Server Start");

        try {
            String receive_data;
            String send_data;
            ServerSocket welcomeSocket = new ServerSocket(1818);


            while (true) {
                Socket cs = welcomeSocket.accept();
                Log.i(TAG, "Socket Accepted");

                BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                DataOutputStream ops = new DataOutputStream(cs.getOutputStream());
                receive_data = br.readLine();
                Log.i(TAG, "receive data : " + receive_data);
                String point = new String(receive_data);
                String[] msg = point.split("/");
                x = Double.parseDouble(msg[1]);
                y = Double.parseDouble(msg[2]);
                send_data = receive_data.toUpperCase() + '\n';
                ops.write(send_data.getBytes());

                Log.i(TAG, "send data : " + send_data);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void receiveDataParsing(Byte data[]){
        switch (data[0]){
        }
    }

    public List<PuppyInfo> getDogPoints(){
        puppys = new ArrayList<PuppyInfo>();
        PuppyInfo puppy = new PuppyInfo("", "1", "함시연");
        puppy.GpsAdd(new GpsInfo(x, y));

        puppys.add(puppy);
        return puppys;
    }
}
