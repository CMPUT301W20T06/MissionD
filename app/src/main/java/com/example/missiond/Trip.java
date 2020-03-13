package com.example.missiond;

import java.io.Serializable;


/**
 * This is a class that keeps track of Trip objects
 */
public class Trip implements Serializable {
    private String location;
    private String destination;

    Trip(String location, String destination){
        this.location = location;
        this.destination = destination;
    }

    /**
     * This get the name of location
     * @return
     *  Return the location name
     */
    String getLocationName(){return this.location;}

    /**
     * This get the name of destination
     * @return
     *  Return the destination name
     */
    String getDestination(){return  this.destination;}



    void setTrip(String location, String destination){
        this.location = location;
        this.destination = destination;
    }
}
