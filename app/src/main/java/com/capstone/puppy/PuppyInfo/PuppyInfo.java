package com.capstone.puppy.PuppyInfo;

import java.io.Serializable;

public class PuppyInfo implements Serializable {
    private String ImageUrl;
    private String age;
    private String name;
    private String distance;

    public PuppyInfo(String ImageUrl, String name, String age){
        this.ImageUrl = ImageUrl;
        this.name = name;
        this.age = age;

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
}
