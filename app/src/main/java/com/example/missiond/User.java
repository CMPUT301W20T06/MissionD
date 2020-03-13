package com.example.missiond;

import android.location.Location;

public class User {
    private String userName;
    private String phoneNumber;
    private final String emailAddress;
    private Location location;

    public User(String userName, String phoneNumber,String emailAddress,Location location){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.location = location;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }

    public String getPhoneNumber(){
        return phoneNumber;
    }


    public void setPhoneNumber(){
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;

    }

    }