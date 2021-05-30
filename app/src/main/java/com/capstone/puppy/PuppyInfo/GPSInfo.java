package com.capstone.puppy.PuppyInfo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class GPSInfo implements Parcelable {
    private double lat = 0;
    private double lon = 0;
    private String date = "";

    public GPSInfo(double lat, double lon, String date){
        this.lat = lat;
        this.lon = lon;
        this.date = date;
    }

    protected GPSInfo(Parcel in) {
        lat = in.readDouble();
        lon = in.readDouble();
        date = in.readString();
    }

    public static final Creator<GPSInfo> CREATOR = new Creator<GPSInfo>() {
        @Override
        public GPSInfo createFromParcel(Parcel in) {
            return new GPSInfo(in);
        }

        @Override
        public GPSInfo[] newArray(int size) {
            return new GPSInfo[size];
        }
    };

    public double getLat(){
        return lat;
    }

    public double getLon(){
        return lon;
    }

    public String getDate(){
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
        parcel.writeString(date);
    }
}
