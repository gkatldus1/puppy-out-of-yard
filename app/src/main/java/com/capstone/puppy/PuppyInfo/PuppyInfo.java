package com.capstone.puppy.PuppyInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class PuppyInfo implements Serializable {
    private String ImageUrl;
    private String age;
    private String name;
    private String distance;
    private boolean isChecked;
    ArrayList<GpsInfo> Gps;

    public void GpsAdd(GpsInfo gps){
        Gps.add(gps);
    }

    public PuppyInfo(String ImageUrl, String name, String age) {
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


}