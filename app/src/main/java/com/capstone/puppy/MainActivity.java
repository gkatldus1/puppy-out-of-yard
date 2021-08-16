package com.capstone.puppy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.puppy.PuppyInfo.GPSInfo;
import com.capstone.puppy.PuppyInfo.MainPuppyAdapter;
import com.capstone.puppy.PuppyInfo.PuppyInfo;
import com.capstone.puppy.Socket.GPSClient;
import com.capstone.puppy.util.DogeDB;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, net.daum.mf.map.api.MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener, MapViewEventListener {
    private final String TAG = "MainActivity";
    private Button btn_menu;
    private Button btn_search;
    private Button btn_start;
    private Button btn_reset;
    private MapView mMapView;
    private ListView lv_puppys;

    ArrayList<PuppyInfo> puppys;
    GPSClient client;
    MapMarker mapMarker;

    MainPuppyAdapter puppyAdapter;

    ProcessThread processThread;

    private boolean isRecording = false;
    private boolean isSearching = false;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewInit();
        DBInit();
        networkInit();

        mapAPIInit();


        processThread = new ProcessThread(getBaseContext());
        processThread.start();
        // getAppKeyHash();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu:
                Intent intent = new Intent(this, SelectPuppyActivity.class);
                intent.putParcelableArrayListExtra("puppys", puppys);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_search:
                if(isRecording)
                    Toast.makeText(getBaseContext(), "Please Stop Recording First", Toast.LENGTH_SHORT).show();
                else {
                    isSearching = !isSearching;
                    mapMarker.resetAll();
                    DateTimePicker dateTimePicker = new DateTimePicker(MainActivity.this, new DateTimePicker.ReturnEventListener() {
                        @Override
                        public void customDialogEvent(Date start_date, Time start_time, Date end_date, Time end_time) {
                            //Select DB
                            List<GPSInfo> gpsInfos = DogeDB.selectGpsRecord(start_date, start_time, end_date, end_time);
                            for(GPSInfo gpsInfo : gpsInfos){
                                mapMarker.changeCustomMarker(gpsInfo.getLat(),gpsInfo.getLon());
                                mapMarker.addPolyLine(gpsInfo.getLat(),gpsInfo.getLon());
                            }
                        }
                    });

                    dateTimePicker.show();
                }
                break;
            case R.id.btn_start:
                if(isSearching)
                    isSearching = false;
                isRecording = !isRecording;
                if(isRecording)
                    btn_start.setText("Recording...");
                else
                    btn_start.setText("Record");
                break;
            case R.id.btn_reset:
                mapMarker.resetAll();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");
        Log.i(TAG, "RESULT:" + resultCode);
        Log.i(TAG, "REQUEST:" + requestCode);
        if (data == null)
            return;

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                puppys = data.getParcelableArrayListExtra("puppys");
                puppyAdapter = new MainPuppyAdapter(puppys);
                lv_puppys.setAdapter(puppyAdapter);
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

    private void DBInit() {
        DogeDB.setDogeContext(this);
        DogeDB.makeTable();
        puppys = DogeDB.selectDogRecord();
        /*
        ArrayList<double[]> templist = new ArrayList<>();
        templist.add(new double[]{37.59385, 126.90616});
        templist.add(new double[]{37.59379, 126.90587});
        templist.add(new double[]{37.59349, 126.90606});
        templist.add(new double[]{37.59337, 126.90639});
        templist.add(new double[]{37.59342, 126.90671});
        templist.add(new double[]{37.59340, 126.90704});
        templist.add(new double[]{37.59337, 126.90748});
        templist.add(new double[]{37.59356, 126.90768});
        templist.add(new double[]{37.59381, 126.90784});
        templist.add(new double[]{37.59375, 126.90824});
        templist.add(new double[]{37.59355, 126.90830});
        templist.add(new double[]{37.59336, 126.90852});
        templist.add(new double[]{37.59320, 126.90866});
        templist.add(new double[]{37.59310, 126.90900});
        templist.add(new double[]{37.59320, 126.90926});
        templist.add(new double[]{37.59318, 126.90928});
        templist.add(new double[]{37.59300, 126.90922});
        templist.add(new double[]{37.59279, 126.90912});
        templist.add(new double[]{37.59264, 126.90893});
        templist.add(new double[]{37.59262, 126.90916});
        templist.add(new double[]{37.59243, 126.90932});
        templist.add(new double[]{37.59222, 126.90959});
        templist.add(new double[]{37.59197, 126.90987});
        templist.add(new double[]{37.59176, 126.91015});
        templist.add(new double[]{37.59162, 126.91045});
        templist.add(new double[]{37.59139, 126.91072});
        templist.add(new double[]{37.59119, 126.91094});
        templist.add(new double[]{37.59119, 126.91125});
        templist.add(new double[]{37.59158, 126.91137});
        templist.add(new double[]{37.59188, 126.91134});
        templist.add(new double[]{37.59194, 126.91172});
        templist.add(new double[]{37.59184, 126.91220});
        templist.add(new double[]{37.59177, 126.91250});
        templist.add(new double[]{37.59168, 126.91286});
        templist.add(new double[]{37.59162, 126.91320});
        templist.add(new double[]{37.59176, 126.91361});
        templist.add(new double[]{37.59218, 126.91287});
        templist.add(new double[]{37.59247, 126.91289});
        templist.add(new double[]{37.59276, 126.91296});
        templist.add(new double[]{37.59305, 126.91304});
        templist.add(new double[]{37.59330, 126.91295});
        templist.add(new double[]{37.59360, 126.91264});
        templist.add(new double[]{37.59398, 126.91218});
        templist.add(new double[]{37.59429, 126.91170});
        templist.add(new double[]{37.59460, 126.91141});
        templist.add(new double[]{37.59482, 126.91108});
        templist.add(new double[]{37.59505, 126.91082});
        templist.add(new double[]{37.59503, 126.91030});
        templist.add(new double[]{37.59505, 126.90966});
        templist.add(new double[]{37.59509, 126.90899});
        templist.add(new double[]{37.59492, 126.90858});
        templist.add(new double[]{37.59463, 126.90847});
        templist.add(new double[]{37.59442, 126.90819});
        templist.add(new double[]{37.59410, 126.90790});
        templist.add(new double[]{37.59393, 126.90768});
        templist.add(new double[]{37.59362, 126.90768});
        templist.add(new double[]{37.59342, 126.90739});
        templist.add(new double[]{37.59339, 126.90693});
        templist.add(new double[]{37.59345, 126.90662});
        templist.add(new double[]{37.59353, 126.90622});
        templist.add(new double[]{37.59371, 126.90629});

        for (double[] doubles : templist) {
            DogeDB.insertGps(doubles[0], doubles[1]);
        }
        */
//        puppys = puppyInit(); //임시로 넣음
    }


    private void networkInit() {
        client = new GPSClient("172.30.1.14", 1238);
        client.start();
    }

    private void viewInit() {
        lv_puppys = findViewById(R.id.lv_puppys);
        puppyAdapter = new MainPuppyAdapter(puppys);
        lv_puppys.setAdapter(puppyAdapter);

        btn_menu = findViewById(R.id.btn_menu);
        btn_search = findViewById(R.id.btn_search);
        btn_start = findViewById(R.id.btn_start);
        btn_reset = findViewById(R.id.btn_reset);
        btn_menu.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        mMapView = (MapView) findViewById(R.id.map_view);
    }

    private void mapAPIInit() {
        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setMapViewEventListener(this);
        mMapView.setCurrentLocationEventListener(this);

        //지도회전 안함, 좌표로 화면 이동
        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        //지도회전 안함, 화면이동 안함
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving);

        //지도회전, 좌표로 화면 이동
        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);

        //지도회전, 화면이동 안함
        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeadingWithoutMapMoving);

        //지도회전 안함, 마커특수효과?, 맵이동 안함
        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithMarkerHeadingWithoutMapMoving);
        //mMapView.setCurrentLocationRadius(0);

        //전부 끄기
        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        //mMapView.setShowCurrentLocationMarker(false);

        //커스텀 마커
        //mMapView.setCurrentLocationRadius(100); // meter
        //mMapView.setCurrentLocationRadiusFillColor(Color.argb(77, 255, 255, 0));
        //mMapView.setCurrentLocationRadiusStrokeColor(Color.argb(77, 255, 165, 0));

        MapPOIItem.ImageOffset trackingImageAnchorPointOffset = new MapPOIItem.ImageOffset(28, 28); // 좌하단(0,0) 기준 앵커포인트 오프셋
        //MapPOIItem.ImageOffset directionImageAnchorPointOffset = new MapPOIItem.ImageOffset(65, 65);
        //MapPOIItem.ImageOffset offImageAnchorPointOffset = new MapPOIItem.ImageOffset(15, 15);
        mMapView.setCustomCurrentLocationMarkerTrackingImage(R.drawable.custom_arrow_map_present_tracking, trackingImageAnchorPointOffset);
        mMapView.setCustomCurrentLocationMarkerTrackingImage(R.drawable.glass, trackingImageAnchorPointOffset);
        //mMapView.setCustomCurrentLocationMarkerDirectionImage(R.drawable.custom_map_present_direction, directionImageAnchorPointOffset);
        //mMapView.setCustomCurrentLocationMarkerImage(R.drawable.custom_map_present, offImageAnchorPointOffset);

        mapMarker = new MapMarker(mMapView);
    }

    private ArrayList<PuppyInfo> puppyInit() {
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
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, v));
        String data = mapPointGeo.latitude + "," + mapPointGeo.longitude + ",";
        client.sendData(data);
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

    class ProcessThread extends Thread {
        private final String TAG = "ProcessThread";
        ArrayList<GPSInfo> gpsInfos;
        Context context;

        public ProcessThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            try {
                gpsInfos = new ArrayList<>();
//                gpsInfos = DogeDB.selectGpsRecord();

                sleep(100);

                Log.i(TAG, "ProcessThread is Start");


                while (true) {
                    //Log.i(TAG, "ProcessThread Loop");

                    //mapMarker.changeCustomMarker(location.getLatitude(), location.getLongitude());
                    //서버로부터 데이터 수신
                    String data = client.getData();
                    try{
                        if(data != null && !isSearching) {
                            String[] spl_data = data.split(",");

                            double lat = Double.parseDouble(spl_data[0]);
                            double lon = Double.parseDouble(spl_data[1]);


                            if(mapMarker.comparePosition(lat, lon)) {
                                mapMarker.changeCustomMarker(lat, lon);
                                mapMarker.addPolyLine(lat, lon);
                                DogeDB.insertGps(lat, lon);
                            }
                        }
                    }catch (Exception e){
                        if(e instanceof NullPointerException)
                            e.printStackTrace();
                        if(e instanceof NumberFormatException)
                            e.printStackTrace();
                    }
                    sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
