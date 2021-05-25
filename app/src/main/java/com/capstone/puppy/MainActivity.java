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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.puppy.PuppyInfo.GPSInfo;
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
    private final String TAG = "MainActivity";
    private Button btn_menu;
    private MapView mMapView;
    private ListView lv_puppys;

    ArrayList<PuppyInfo> puppys;
    GPSServer server;
    MapMarker mapMarker;

    MainPuppyAdapter puppyAdapter;

    ProcessThread processThread;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBInit();
        serverInit();
        viewInit();
        mapAPIInit();

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
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");
        Log.i(TAG, "RESULT:" + resultCode);
        Log.i(TAG, "REQUEST:" + requestCode);
        if(data == null)
            return;

        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                ArrayList<PuppyInfo> new_puppys_temp = (ArrayList<PuppyInfo>) data.getSerializableExtra("added_puppys");
                for(PuppyInfo puppy : new_puppys_temp) {
                    DogeDB.insertRecord("DOG_INFO", puppy.getName(), puppy.getAge());
                    puppys.add(puppy);
                    Log.i(TAG, "add puppy complete");
                }
                puppyAdapter.notifyDataSetChanged();
                Log.i(TAG, "puppy ui update");
            }
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
       //puppys = DogeDB.getPuppysInfo();
        puppys = puppyInit(); //임시로 넣음
    }

    

    private void serverInit(){
        server = new GPSServer(puppys);
        server.start();
    }

    private void viewInit() {
        lv_puppys = findViewById(R.id.lv_puppys);
        puppyAdapter = new MainPuppyAdapter(puppys);
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

    private ArrayList<PuppyInfo> puppyInit(){
        ArrayList<PuppyInfo> temp_puppys = new ArrayList<PuppyInfo>();

        temp_puppys.add(new PuppyInfo(1, "image/1.png", "테슬라", "589달러"));
        temp_puppys.add(new PuppyInfo(2, "image/1.png", "일론", "49세"));
        temp_puppys.add(new PuppyInfo(3, "image/2.png", "머스크", "49세"));
        temp_puppys.add(new PuppyInfo(4, "image/3.png", "도지", "550층"));
        temp_puppys.add(new PuppyInfo(5, "image/3.png", "화성", "4603000000년"));
        temp_puppys.add(new PuppyInfo(6, "image/3.png", "가즈아", "700층"));

        return temp_puppys;
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
        PuppyInfo puppyInfo;
        @Override
        public void run() {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "ProcessThread is Start");

            puppyInfo = puppys.get(0);

            while (true) {
                GPSInfo gpsInfo = server.getGPSInfo();
                boolean isAddSuccess = puppyInfo.addGPSInfo(gpsInfo);
                if(isAddSuccess) {
                    DogeDB.insertGps(gpsInfo.getLat(), gpsInfo.getLon());
                    mapMarker.createCustomMarker(gpsInfo);
                }
//                for(GPSInfo one : gps) {
//                    mapMarker.createCustomMarker(one);
//                    try {
//                        sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }
    }
}
