package com.capstone.puppy;

import android.util.Log;
import android.view.View;

import com.capstone.puppy.PuppyInfo.GPSInfo;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapMarker {
    private static final String TAG = "GPSServer";

    MapView mapview;
    MapPOIItem mCustomMarker;


    public MapMarker(View mapview){
        this.mapview = (MapView)mapview;

        mCustomMarker = new MapPOIItem();
    }

    public void createCustomMarker(GPSInfo gps) {

        double x = gps.getPointX();
        double y = gps.getPointY();
        if(x ==0 || y == 0)
            return;

        Log.i(TAG, "위도 : " + x + " 경도 : " + y);
        MapPoint point =  MapPoint.mapPointWithGeoCoord(x, y);

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
