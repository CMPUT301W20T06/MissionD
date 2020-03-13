package com.example.missiond;

import java.io.Serializable;

public class Trip implements Serializable {
    private String location;
    private String destination;

    Trip(String location, String destination){
        this.location = location;
        this.destination = destination;
    }

    String getLocationName(){return this.location;}

    String getDestination(){return  this.destination;}


    void setTrip(String location, String destination){
        this.location = location;
        this.destination = destination;
    }
}
