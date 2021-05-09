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
    private List<GPSInfo> gpsInfos;
    private boolean isChecked;

    public PuppyInfo(int id, String ImageUrl, String name, String age){
        this.id = id;
        this.ImageUrl = ImageUrl;
        this.name = name;
        this.age = age;
        isChecked = false;
        gpsInfos = new ArrayList<GPSInfo>();
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }
    public String getAge(){ return age; }

    public String getDistance(){
        return distance;
    }

    public boolean getChecked(){
        return isChecked;
    }
    public void setChecked(boolean isChecked){
        this.isChecked = isChecked;
    }

    public void addGPSPoint(double x, double y){
        GPSInfo gpsInfo = new GPSInfo();
        gpsInfo.setPosition(x, y);
        gpsInfos.add(gpsInfo);
    }

    public List<GPSInfo> getGpsInfos(){
        return gpsInfos;
    }
}
