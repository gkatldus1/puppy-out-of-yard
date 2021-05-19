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

    public void GpsAdd(GPSInfo gps){
        this.gps.add(gps);
    }

    public PuppyInfo(int id, String ImageUrl, String name, String age) {
        this.id = id;
        this.ImageUrl = ImageUrl;
        this.name = name;
        this.age = age;
        isChecked = false;
    }

    public PuppyInfo(String name, String age) {
        this.name = name;
        this.age = age;
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

    public void addGPSPoint(double x, double y){
        GPSInfo gpsInfo = new GPSInfo(x, y);
        gps.add(gpsInfo);
    }

    public List<GPSInfo> getGpsInfos(){
        return gps;
    }
}
