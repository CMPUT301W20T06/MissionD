package com.example.missiond;

import android.location.Address;

/**
 * This class is a super class for driver and rider that stores user date
 */
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

    /**
     * This is a constructor for firebase to convert database data to User class
     */
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


    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
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