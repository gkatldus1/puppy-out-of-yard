package com.capstone.puppy;

import android.location.Location;
import android.util.Log;
import android.view.View;

import com.capstone.puppy.PuppyInfo.GPSInfo;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

public class MapMarker {
    private static final String TAG = "GPSServer";
    double lat = 0;
    double lon = 0;
    MapView mapview;
    MapPOIItem mCustomMarker;
    MapPolyline polyline;


    public MapMarker(View mapview){
        this.mapview = (MapView)mapview;

        mCustomMarker = new MapPOIItem();
    }

    public boolean comparePosition(double lat, double lon){
        if(lat == 0 || lon == 0) {
            Log.i(TAG,"position is zero");
            return false;
        }

        float[] result = new float[1];
        Location.distanceBetween(this.lat, this.lon, lat, lon, result);
        if(result[0] < 0.3){
            Log.i(TAG,"moving distance is too short: " + result[0] + "m");
            return false;
        }else{
            Log.i(TAG,"moving distance: " + result[0] + "m");
            return true;
        }
    }
    public void changeCustomMarker(double lat, double lon){
        Log.i(TAG, "Marker Update");
        Log.i(TAG, "위도 : " + lat + " 경도 : " + lon);
        this.lat = lat;
        this.lon = lon;

        mapview.removePOIItem(mCustomMarker);
        MapPoint point =  MapPoint.mapPointWithGeoCoord(lat, lon);
        String name = "lat:" + lat + " lon:" + lon;
        mCustomMarker.setItemName(name);
        mCustomMarker.setTag(1);
        mCustomMarker.setMapPoint(point);

        mCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        mCustomMarker.setCustomImageResourceId(R.drawable.pawprint_icon);
        mCustomMarker.setCustomImageAutoscale(true);
        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapview.addPOIItem(mCustomMarker);
        mapview.selectPOIItem(mCustomMarker, true);
        mapview.setMapCenterPoint(point, false);

    }

    public void addPolyLine(double lat, double lon){
        Log.i(TAG, "PolyLine Update");

        if(lat ==0 || lon == 0)
            return;

        if (polyline == null)
            polyline = new MapPolyline();
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
        mapview.removeAllPolylines();
        mapview.addPolyline(polyline);
    }

    public void resetAll(){
        mapview.removeAllPOIItems();
        mapview.removeAllPolylines();
        polyline = new MapPolyline();
    }
}
