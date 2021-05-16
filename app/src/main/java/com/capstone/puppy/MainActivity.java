package com.capstone.puppy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.puppy.PuppyInfo.MainPuppyAdapter;
import com.capstone.puppy.PuppyInfo.PuppyInfo;
import com.capstone.puppy.Socket.GPSServer;
import com.capstone.puppy.util.DogeDB;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, net.daum.mf.map.api.MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener, MapViewEventListener {
    private Button btn_menu;
    private MapView mMapView;
    private ListView lv_puppys;

    ArrayList<PuppyInfo> puppys;
    GPSServer server;
    MapMarker mapMarker;

    ProcessThread processThread;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        puppyInit(); //임시 리스트 추가
        serverInit();
        viewInit();
        mapAPIInit();
        DBInit();

        processThread = new ProcessThread();
        processThread.start();
        // getAppKeyHash();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_menu:
                Intent intent = new Intent(this, SelectPuppyActivity.class);
                intent.putExtra("puppys", puppys);
                startActivity(intent);
                break;
        }
    }

    // 카카오 맵 api를 사용하려면 해쉬키를 https://developers.kakao.com/에 등록해야 한다. 해쉬키를 로그로 얻기위한 1회용 함수
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }

    private void DBInit(){
       DogeDB.setDogeContext(this);
       DogeDB.makeTable();

    }


    private void serverInit(){
        server = new GPSServer();
        server.start();
    }

    private void viewInit() {
        lv_puppys = findViewById(R.id.lv_puppys);
        MainPuppyAdapter puppyAdapter = new MainPuppyAdapter(puppys);
        lv_puppys.setAdapter(puppyAdapter);

        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this);

        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setMapViewEventListener(this);
    }

    private void mapAPIInit(){
        mapMarker = new MapMarker(mMapView);
    }

    private void puppyInit(){
        puppys = new ArrayList<PuppyInfo>();
        puppys.add(new PuppyInfo("", "1", "아람"));
        puppys.add(new PuppyInfo("", "2", "해피"));
        puppys.add(new PuppyInfo("", "3", "초코"));
    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {

    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    @Override
    public void onCurrentLocationUpdate(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(net.daum.mf.map.api.MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(net.daum.mf.map.api.MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(net.daum.mf.map.api.MapView mapView) {

    }

    @Override
    public void onLoadMapView() {

    }

    class ProcessThread extends Thread{
        private final String TAG = "ProcessThread";

        @Override
        public void run() {
            Log.i(TAG, "ProcessThread is Start");

            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PuppyInfo puppy = server.getDogPoints().get(0);
                //double gps_x_info = new Double(puppy.getPointX());
                //double gps_y_info = new Double(puppy.getPointY());
                DogeDB.insertGps(puppy.getPointX(), puppy.getPointY());
                mapMarker.createCustomMarker(puppy);

            }
        }
    };
}