package com.capstone.puppy.Socket;

import android.util.Log;
import com.capstone.puppy.PuppyInfo.PuppyInfo;

import java.io.IOException;
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
    List<PuppyInfo> puppys;

    List<ReceiveThread> receiveThreads;
    public GPSServer(List<PuppyInfo> puppys){
        this.puppys = puppys;
    }

    @Override
    public void run() {
        Log.i(TAG, "GPS Server Start");

        try {
            ServerSocket serverSocket = new ServerSocket(1818);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Log.i(TAG, "Socket Accepted");

                ReceiveThread receiveThread  = new ReceiveThread(clientSocket);
                receiveThread.start();
                receiveThreads.add(receiveThread);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
