package com.capstone.puppy.PuppyInfo;

public class GpsInfo {
    private double x = 0;
    private double y = 0;

    public GpsInfo(double x, double y){
        this.x = x;
        this.y = y;
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
