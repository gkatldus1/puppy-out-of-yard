package com.capstone.puppy.Socket;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;


/*
header(byte)
    0 : cmd
    1 : gps data
*/

//안드로이드 메인스레드에서 Socket을 사용할 수 없어서 AsyncTask로 변경
public class GPSClient {
    private static final String TAG = "GPSClient";

    Socket nsocket; //Network Socket
    InputStream nis; //Network Input Stream
    OutputStream nos; //Network Output Stream

    String ip;
    int port;

    String data = null;

    boolean isUser;

    public GPSClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public String getData() {
        if(data == null)
            return null;
        else {
            String temp = data;
            data = null;
            return temp;
        }

    }

    public void sendData(String data){
        GPSSender gpsSender = new GPSSender(data);
        gpsSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public void start(boolean isUser){
        this.isUser = isUser;
        GPSReceiver gpsReceiver = new GPSReceiver(isUser);
        gpsReceiver.execute();
    }

    class GPSReceiver extends AsyncTask<Void, byte[], Boolean>{
        boolean isUser;
        GPSReceiver(boolean isUser){
            this.isUser = isUser;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean result = false;
            try {
                Log.i(TAG, "doInBackground: Creating socket");
                SocketAddress sockaddr = new InetSocketAddress(ip, port);
                nsocket = new Socket();
                nsocket.connect(sockaddr, 5000); //5 second connection timeout

                byte[] buffer = new byte[1024];
                if (nsocket.isConnected()) {
                    nis = nsocket.getInputStream();
                    nos = nsocket.getOutputStream();
                    Log.i(TAG, "Socket created, streams assigned");
                    Log.i(TAG, "Waiting for initial data...");

                    if(isUser) {
                        GPSSender gpsSender = new GPSSender("U:");
                        gpsSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    int read = nis.read(buffer, 0, 1024); //This is blocking
                    while(read != -1){
                        byte[] tempdata = new byte[read];

                        System.arraycopy(buffer, 0, tempdata, 0, read);
                        data = new String(tempdata);
                        Log.i(TAG, "getData:" + data);
                        publishProgress(tempdata);
                        read = nis.read(buffer, 0, 1024); //This is blocking
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
                    data = null;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "doInBackground: Finished");
            }
            return result;
        }
    }

    class GPSSender extends AsyncTask<Void, byte[], Boolean>{
        String data;
        GPSSender(String data){
            this.data = data + "\r\n";
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if (nsocket.isConnected()) {
                    Log.i(TAG, "Send Data:"+data);
                    nos.write(data.getBytes());
                } else {
                    Log.i(TAG, "Socket is closed");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "Caught an IOException");
                start(isUser);
            } catch (NullPointerException e)
            {
                e.printStackTrace();
                Log.i(TAG, "Caught an NullPointerException");
            }
            return true;
        }
    }
}