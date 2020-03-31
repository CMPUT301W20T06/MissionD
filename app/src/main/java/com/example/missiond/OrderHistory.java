package com.example.missiond;

public class OrderHistory {
    private String pickUp, dest, driver, fare;

    OrderHistory(String pickUp, String dest, String driver, String fare){
        this.pickUp = pickUp;
        this.dest = dest;
        this.driver = driver;
        this.fare = fare;
    }

    //getter
    String getPickUp() { return this.pickUp; }
    String getDest() { return this.dest; }
    String getDriver() { return this.driver; }
    String getFare() { return this.fare;}

    //setter
    public void setOrderHistory(OrderHistory newOrderHistory){
        this.pickUp = newOrderHistory.getPickUp();
        this.dest = newOrderHistory.getDest();
        this.driver = newOrderHistory.getDriver();
        this.fare = newOrderHistory.getFare();
    }
}
