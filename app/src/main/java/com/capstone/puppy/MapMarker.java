package com.capstone.puppy;

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

    public void changeCustomMarker(GPSInfo gpsInfo){
        Log.i(TAG, "Marker Update");
        double lat = gpsInfo.getLat();
        double lon = gpsInfo.getLon();

        if(this.lat == lat && this.lon == lon)
            return;

        if(lat == 0 || lon == 0)
            return;

        Log.i(TAG, "위도 : " + lat + " 경도 : " + lon);

        if(lat ==0 || lon == 0)
            return;

        mapview.removePOIItem(mCustomMarker);
        MapPoint point =  MapPoint.mapPointWithGeoCoord(gpsInfo.getLat(), gpsInfo.getLon());
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

    public void addPolyLine(GPSInfo gpsInfo){
        Log.i(TAG, "PolyLine Update");
        double lat = gpsInfo.getLat();
        double lon = gpsInfo.getLon();

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
