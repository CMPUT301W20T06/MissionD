package com.example.missiond;

import android.location.Address;

public class User {
    private String userName;
    private String phoneNumber;
    private String emailAddress;
    private Address location;

    public User(String userName, String phoneNumber,String emailAddress){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
    public User(){

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

//    public Address getLocation(){
//        return location;
//    }

//    public void setLocation(Address location) {
//        this.location = location;
//
//    }

    }