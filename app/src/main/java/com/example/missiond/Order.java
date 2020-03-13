package com.example.missiond;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;

public class Order {
    private Location startLocation;
    private Location endLocation;
    private Float distance;
    private Float cost;
    private Integer orderStatus;
    private Rider rider;
    private Driver driver;


    public Order(Location startLocation, Location endLocation, Float distance, Float cost,Integer orderStatus,Rider rider,Driver driver) {

        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.cost = cost;
        this.orderStatus = orderStatus;
        this.rider = rider;
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getDistance() {

        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

}
