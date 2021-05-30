package com.capstone.puppy.PuppyInfo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class PuppyInfo implements Parcelable {
    private int id;
    private String imageUrl;
    private String age;
    private String name;
    private String distance;
    private boolean isChecked;
    ArrayList<GPSInfo> gps;

    protected PuppyInfo(Parcel in) {
        id = in.readInt();
        imageUrl = in.readString();
        age = in.readString();
        name = in.readString();
        distance = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<PuppyInfo> CREATOR = new Creator<PuppyInfo>() {
        @Override
        public PuppyInfo createFromParcel(Parcel in) {
            return new PuppyInfo(in);
        }

        @Override
        public PuppyInfo[] newArray(int size) {
            return new PuppyInfo[size];
        }
    };

    public void GpsAdd(GPSInfo gps) {
        this.gps.add(gps);
    }

    public PuppyInfo(int id, String name, String age, String ImageUrl) {
        this.id = id;
        this.imageUrl = ImageUrl;
        this.name = name;
        this.age = age;
        isChecked = false;
        if(gps == null)
            gps = new ArrayList<GPSInfo>();
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void updateInfo(String name, String age, String url){
        this.name = name;
        this.age = age;
        this.imageUrl = url;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(String age){
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setImageUrl(String url){
        this.imageUrl = url;
    }

    public String getImageUrl(){
        return imageUrl;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(imageUrl);
        parcel.writeString(age);
        parcel.writeString(name);
        parcel.writeString(distance);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
    }
}
