package com.capstone.puppy.PuppyInfo;


import net.daum.mf.map.api.MapPoint;

import java.util.Date;

public class GPSInfo {
    private MapPoint mapPoint;
    private Date date;

    public void setPosition(double lat, double lon){
        mapPoint = MapPoint.mapPointWithGeoCoord(lat, lon);
        date = new Date(System.currentTimeMillis());
    }

    public MapPoint getMapPoint(){
        return mapPoint;
    }
}
