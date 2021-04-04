package com.capstone.puppy.PuppyInfo;

import java.io.Serializable;

public class PuppyInfo implements Serializable {
    private String ImageUrl;
    private int age;
    private String name;
    private String distance;

    public PuppyInfo(String ImageUrl, int age, String name){
        this.ImageUrl = ImageUrl;
        this.age = age;
        this.name = name;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getDistance(){
        return distance;
    }
}
