package com.capstone.puppy.Socket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/*
header(byte)
    0 : cmd
    1 : gps data

 */
public class GPSServer extends Thread{
    private static final String TAG = "GPSServer";


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
}
