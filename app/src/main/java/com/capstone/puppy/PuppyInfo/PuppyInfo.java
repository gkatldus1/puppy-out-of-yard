package com.capstone.puppy.PuppyInfo;

import java.io.Serializable;

public class PuppyInfo implements Serializable {
    private String ImageUrl;
    private String age;
    private String name;
    private String distance;
    private double x = 0;
    private double y = 0;
    private boolean isChecked;

    public PuppyInfo(String ImageUrl, String name, String age){
        this.ImageUrl = ImageUrl;
        this.name = name;
        this.age = age;
        isChecked = false;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public void setPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getPointX(){
        return x;
    }

    public double getPointY(){
        return y;
    }
}
