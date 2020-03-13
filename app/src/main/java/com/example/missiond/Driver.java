package com.example.missiond;

import android.location.Address;

public class Driver extends User {
    private Boolean availability;
    private int thumbUp = 1;
    private int thumbDown = 0;
    public Driver(String userName, String phoneNumber, String emailAddress) {
        super(userName, phoneNumber, emailAddress);
        this.availability = true;
    }
    public Driver(){
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Boolean getAvailability() {
        return this.availability;
    }

    public void increaseThumbUp() {
        this.thumbUp += 1;
    }

    public void increaseThumbDown() {
        this.thumbDown += 1;
    }

    public float getRating() {
//        return (float) this.thumbUp;
        return (float) this.thumbUp / (this.thumbDown + this.thumbUp) * 5;
    }
}
