package com.capstone.puppy.Socket;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


/*
header(byte)
    0 : cmd
    1 : gps data
*/

//안드로이드 메인스레드에서 Socket을 사용할 수 없어서 AsyncTask로 변경
public class GPSClient extends AsyncTask<Void, byte[], Boolean> {
    private static final String TAG = "GPSClient";

    Socket nsocket; //Network Socket
    InputStream nis; //Network Input Stream
    OutputStream nos; //Network Output Stream

    String ip;
    int port;

    public GPSClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public

    @Override
    protected Boolean doInBackground(Void... params) { //This runs on a different thread
        boolean result = false;
        try {
            Log.i(TAG, "doInBackground: Creating socket");
            SocketAddress sockaddr = new InetSocketAddress(ip, port);
            nsocket = new Socket();
            nsocket.connect(sockaddr, 5000); //5 second connection timeout
            if (nsocket.isConnected()) {
                nis = nsocket.getInputStream();
                nos = nsocket.getOutputStream();
                Log.i(TAG, "doInBackground: Socket created, streams assigned");
                Log.i(TAG, "doInBackground: Waiting for initial data...");
                byte[] buffer = new byte[4096];
                int read = nis.read(buffer, 0, 4096); //This is blocking
                while(read != -1){
                    byte[] tempdata = new byte[read];
                    System.arraycopy(buffer, 0, tempdata, 0, read);
                    publishProgress(tempdata);
                    Log.i(TAG, "doInBackground: Got some data");
                    read = nis.read(buffer, 0, 4096); //This is blocking
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "doInBackground: IOException");
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "doInBackground: Exception");
            result = true;
        } finally {
            try {
                nis.close();
                nos.close();
                nsocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: Finished");
        }
        return result;
    }

    public void SendDataToNetwork(String cmd) { //You run this from the main thread.
        try {
            if (nsocket.isConnected()) {
                Log.i(TAG, "SendDataToNetwork: Writing received message to socket");
                nos.write(cmd.getBytes());
            } else {
                Log.i(TAG, "SendDataToNetwork: Cannot send message. Socket is closed");
            }
        } catch (Exception e) {
            Log.i(TAG, "SendDataToNetwork: Message send failed. Caught an exception");
        }
    }
}