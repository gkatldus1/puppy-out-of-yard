package com.capstone.puppy;

import android.util.Log;
import android.view.View;

import com.capstone.puppy.PuppyInfo.GPSInfo;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapMarker {
    private static final String TAG = "GPSServer";
    double lat = 0;
    double lon = 0;
    MapView mapview;
    MapPOIItem mCustomMarker;


    public MapMarker(View mapview){
        this.mapview = (MapView)mapview;

        mCustomMarker = new MapPOIItem();
    }

    public void createCustomMarker(GPSInfo gps) {
        double lat = gps.getLat();
        double lon = gps.getLon();

        if(this.lat == lat && this.lon == lon)
            return;

        Log.i(TAG, "위도 : " + lat + " 경도 : " + lon);

        if(lat ==0 || lon == 0)
            return;

        MapPoint point =  MapPoint.mapPointWithGeoCoord(lat, lon);

        mCustomMarker = new MapPOIItem();
        String name = "Custom Marker";
        mCustomMarker.setItemName(name);
        mCustomMarker.setTag(1);
        mCustomMarker.setMapPoint(point);

        mCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);

        mCustomMarker.setCustomImageResourceId(R.drawable.pawprint);
        mCustomMarker.setCustomImageAutoscale(true);
        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapview.addPOIItem(mCustomMarker);
        mapview.selectPOIItem(mCustomMarker, true);
        mapview.setMapCenterPoint(point, false);
    }
}
