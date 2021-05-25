package com.capstone.puppy.PuppyInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PuppyInfo implements Serializable {
    private int id;
    private String ImageUrl;
    private String age;
    private String name;
    private String distance;
    private boolean isChecked;
    ArrayList<GPSInfo> gps;

    public void GpsAdd(GPSInfo gps) {
        this.gps.add(gps);
    }

    public PuppyInfo(int id, String ImageUrl, String name, String age) {
        this.id = id;
        this.ImageUrl = ImageUrl;
        this.name = name;
        this.age = age;
        isChecked = false;
        if(gps == null)
            gps = new ArrayList<GPSInfo>();
    }

    public PuppyInfo(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getDistance() {
        return distance;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean addGPSInfo(GPSInfo gpsInfo){
        GPSInfo lastInfo;
        if(gps.size() <= 0) {
            gps.add(gpsInfo);
            return true;
        }

        lastInfo = gps.get(gps.size()-1);
        if(lastInfo.getLat() == gpsInfo.getLat() && lastInfo.getLon() == gpsInfo.getLon())
            return false;
        else {
            gps.add(gpsInfo);
            return true;
        }
    }

    public ArrayList<GPSInfo> getGpsInfos(){
        return gps;
    }

    public void setGpsInfos(){

    }
}
