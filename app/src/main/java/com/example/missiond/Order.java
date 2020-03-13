package com.example.missiond;

import android.location.Address;


/**
 * This is a class that keeps track of Order objects
 */
public class Order {
    private Address startLocation;
    private Address endLocation;
    private Float distance;
    private Float cost;
    private Integer orderStatus;
    private Rider rider;
    private Driver driver;


    public Order(Address startLocation, Address endLocation, Float distance, Float cost,Integer orderStatus,Rider rider,Driver driver) {

        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.cost = cost;
        this.orderStatus = orderStatus;
        this.rider = rider;
        this.driver = driver;
    }

    /**
     * This get the name of Driver
     * @return
     *  Return the Driver name
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * set the driver name
     * @param driver
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * return the cost
     * @return cost
     */
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

    public Address getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Address endLocation) {
        this.endLocation = endLocation;
    }

    public Address getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Address startLocation) {
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
