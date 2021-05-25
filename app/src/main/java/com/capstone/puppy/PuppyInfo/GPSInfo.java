package com.capstone.puppy.PuppyInfo;

public class GPSInfo{
    private double lat = 0;
    private double lon = 0;
    private String date = "";

    public GPSInfo(double lat, double lon, String date){
        this.lat = lat;
        this.lon = lon;
        this.date = date;
    }

    public double getLat(){
        return lat;
    }

    public double getLon(){
        return lon;
    }

    public String getDate(){
        return date;
    }
}
