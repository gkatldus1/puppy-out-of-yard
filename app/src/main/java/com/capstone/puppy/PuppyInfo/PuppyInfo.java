package com.capstone.puppy.PuppyInfo;

import java.io.Serializable;

public class PuppyInfo implements Serializable {
    private String ImageUrl;
    private int age;
    private String name;

    public PuppyInfo(String ImageUrl, int age, String name){
        this.ImageUrl = ImageUrl;
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
