package com.example.missiond;

import android.location.Address;
import android.location.Location;

/**
 * This is Order class that stores Order information
 * @author
 *  Yifei Ma
 */
public class Order {
    private String startLocation;
    private String endLocation;
    private Float distance;
    private Float cost;
    public Integer orderStatus;
    private String rider;
    private String driver;
    private Coordinate startLoc;
    private Coordinate endLoc;
    private String userName;
    private String id = null;


    public Order(String startLocation, String endLocation, Float distance, Float cost,Integer orderStatus,String rider,String driver, Coordinate startLoc, Coordinate endLoc, String userName) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.cost = cost;
        this.orderStatus = orderStatus;
        this.rider = rider;
        this.driver = driver;
        this.startLoc = startLoc;
        this.endLoc = endLoc;
        this.userName = userName;
    }

    /**
     * This is a constructor for firebase to convert database data to Order class
     */
    public Order(){
    }

    /**
     * This get the driver of this order
     * @return
     *  Return String of the driver's username
     */
    public String getDriver() {
        return driver;
    }

    /**
     * This set driver of the order
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * This get the cost of order
     * @return
     *  Return Float
     */
    public Float getCost() {
        return cost;
    }

    /**
     * This set the cost of order
     */
    public void setCost(Float cost) {
        this.cost = cost;
    }

    /**
     * This get the distance of route
     * @return
     *  Return Float
     */
    public Float getDistance() {

        return distance;
    }

    /**
     * This set distance of a order
     */
    public void setDistance(Float distance) {
        this.distance = distance;
    }

    /**
     * This get the destination address
     * @return
     *  Return String
     */
    public String getEndLocation() {
        return endLocation;
    }

    /**
     * This set destination
     */
    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    /**
     * This get the Start location
     * @return
     *  Return String
     */
    public String getStartLocation() {
        return startLocation;
    }

    /**
     * This set start location
     */
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * This get the OrderStatus, 1 stand for current
     * @return
     *  Return Integer
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * This set order status
     * //0:cancel
     * //1:send request
     * //2:driver accept
     * //3:driver and rider accept(have not picked up yet)
     * //4:pick up but not finish
     * //5:request is over(history)
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * This get the rider
     * //0:cancel
     * //1:send request
     * //2:driver accept
     * //3:driver and rider accept(have not picked up yet)
     * //4:pick up but not finish
     * //5:request is over(history)
     * @return
     *  Return String of Rider's username
     */

    public String getRider() {
        return rider;
    }

    /**
     * This set rider's username
     */
    public void setRider(String rider) {
        this.rider = rider;
    }

    public Coordinate getStartLoc() {
        return this.startLoc;
    }

    public void setStartLoc(Coordinate startLoc){
        this.startLoc = startLoc;
    }

    public Coordinate getEndLoc() {
        return this.endLoc;
    }

    public void setEndLoc(Coordinate endLoc) {
        this.endLoc = endLoc;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
