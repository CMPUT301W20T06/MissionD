package com.example.missiond;

import android.location.Address;

public class Order {
    private String startLocation;
    private String endLocation;
    private Float distance;
    private Float cost;
    private Integer orderStatus;
    private String rider;
    private String driver;


    public Order(String startLocation, String endLocation, Float distance, Float cost,Integer orderStatus,String rider,String driver) {

        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.cost = cost;
        this.orderStatus = orderStatus;
        this.rider = rider;
        this.driver = driver;
    }
    public Order(){
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
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

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRider() {
        return rider;
    }

    public void setRider(String rider) {
        this.rider = rider;
    }

}
