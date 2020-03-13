package com.example.missiond;

import android.location.Location;

public class Rider extends User {
    public Rider(String userName, String phoneNumber, String emailAddress, Location location) {
        super(userName, phoneNumber, emailAddress, location);
    }
}
