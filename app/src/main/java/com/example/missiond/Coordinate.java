package com.example.missiond;

/**
 * This class stores longitude and latitude of a location
 * @author
 * Yifei Ma
 */
public class Coordinate {
    private double longitude;
    private double latitude;

    /**
     * This is the constructor method that create this class
     */
    public Coordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * This is the constructor method that let firebase be able to convert into this class
     */
    public Coordinate () {
    }

    /**
     * This is the method get Latitude of the location
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * This is the method get Longitude of the location
     */
    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
