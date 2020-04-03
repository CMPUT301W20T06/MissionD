package com.example.missiond;

import android.location.Address;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class OrderTest {

    /*
This method tests whether the getDriver() gets the Driver's name.

 */

    @Test
    public void SetAndGetDriver() {
        String startLocation ="University of Alberta";
        String endLocation = "Southgate Centre";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");

        String expectedDriver = driver.getUserName();
        String output;

        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        order1.setDriver(expectedDriver);

        assertEquals(expectedDriver,order1.getDriver());

    }




    /*
  This method tests whether the setDriver() sets the cost.
   */
    @Test
    public void SetAndGetCost() {
        String startLocation ="University of Alberta";
        String endLocation = "Southgate Centre";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        Float expected = 90.3f;


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        order1.setCost(90.3f);

        assertEquals(expected,order1.getCost());

    }



    /*
  This method tests whether the setDistance() sets the distance.
   */

    @Test
    public void SetAndGetDistance() {
        String startLocation ="University of Alberta";
        String endLocation = "Southgate Centre";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        Float expected = 38.5f;


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        order1.setDistance(38.5f);

        assertEquals(expected,order1.getDistance());

    }


    /*
    This method tests whether the setEndLocation() sets the EndLocation.
    */
    @Test
    public void SetAndGetEndLocation() {
        String startLocation ="University of Alberta";
        String endLocation = "Southgate Centre";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        String expected = "UofE";


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        order1.setEndLocation("UofE");

        assertEquals(expected,order1.getEndLocation());
    }




    /*
    This method tests whether the setStartLocation() sets the StartLocation.
    */
    @Test
    public void SetAndGetStartLocation() {
        String startLocation ="University of Alberta";
        String endLocation = "Southgate Centre";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        String expected = "UofT";


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        order1.setStartLocation("UofT");

        assertEquals(expected,order1.getStartLocation());

    }

    /*
   This method tests whether the setOrderStatus() sets the OrderStatus.
   */
    @Test
    public void SetAndGetOrderStatus() {
        String startLocation ="University of Alberta";
        String endLocation = "Southgate Centre";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        Integer expected = 2;


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        order1.setOrderStatus(2);

        assertEquals(expected,order1.getOrderStatus());


    }


    /*
   This method tests whether the setRider() sets who the driver is in this order.
   */
    @Test
    public void SetAndGetRider() {
        String startLocation ="University of Alberta";
        String endLocation = "Southgate Centre";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        String expect = "Cori";



        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        order1.setRider("Cori");

        assertEquals(order1.getRider(),expect);

    }
}