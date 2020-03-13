package com.example.missiond;

import android.location.Location;

public class Driver extends User {
    private Boolean availability;
    private Integer thumbUp;
    private Integer thumbDown;
    public Driver(String userName, String phoneNumber, String emailAddress, Location location) {
        super(userName, phoneNumber, emailAddress, location);
        this.availability = true;
        this.thumbDown = 0;
        this.thumbUp = 0;
    }

    public void setAvailabilty(Boolean availability) {
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

    public float getRatin() {
        return this.thumbUp / (this.thumbDown + thumbUp) * 5;
    }
}
