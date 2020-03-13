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
    public void getDriver() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");

        String expectedDriver = driver.getUserName();
        String output;

        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        output = order1.getDriver();

        assertEquals(expectedDriver,output);



    }
    /*
   This method tests whether the setDriver() sets the Driver's name.
    */
    @Test
    public void setDriver() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
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
    This method tests whether the getDriver() returns the cost of the trip.
    */
    @Test
    public void getCost() {

        String startLocation ="UofA";
        String endLocation = "CityCenter";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        Float output;


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        output = order1.getCost();

        assertEquals(cost,output);

    }


    /*
  This method tests whether the setDriver() sets the cost.
   */
    @Test
    public void setCost() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
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
  This method tests whether the getDistance() returns a float obj represents the cost.
   */
    @Test
    public void getDistance() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        Float output;


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        output = order1.getDistance();

        assertEquals(distance,output);

    }

    /*
  This method tests whether the setDistance() sets the distance.
   */

    @Test
    public void setDistance() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
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
 This method tests whether the getEndLocation() returns a String obj represents the EndLocation.
  */
    @Test
    public void getEndLocation() {

        String startLocation ="UofA";
        String endLocation = "CityCenter";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        String output;


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        output = order1.getEndLocation();

        assertEquals(endLocation,output);

    }


    /*
    This method tests whether the setEndLocation() sets the EndLocation.
    */
    @Test
    public void setEndLocation() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
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
    This method tests whether the getStartLocation() returns a String obj represents the StartLocation.
    */
    @Test
    public void getStartLocation() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        String output;


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        output = order1.getStartLocation();

        assertEquals(startLocation,output);
    }
    /*
    This method tests whether the setStartLocation() sets the StartLocation.
    */
    @Test
    public void setStartLocation() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
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
    This method tests whether the getOrderStatus() returns an Integer obj represents the OrderStatus.
    */
    @Test
    public void getOrderStatus() {

        String startLocation ="UofA";
        String endLocation = "CityCenter";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        Integer output;


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        output = order1.getOrderStatus();

        assertEquals(orderStatus,output);
    }
    /*
   This method tests whether the setOrderStatus() sets the OrderStatus.
   */
    @Test
    public void setOrderStatus() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
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
    This method tests whether the getRider() returns a String obj represents rider's name.
    */

    @Test
    public void getRider() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
        Float distance = 37.25f;
        Float cost = 89.3f;
        Integer orderStatus = 1;
        Rider rider = new Rider("Brian","123","Brian@cmput301.com");
        Driver driver = new Driver("Elena","456","Elena@cmput301.com");
        String expected;


        Order order1 = new Order(startLocation,endLocation,distance,cost,orderStatus,rider.getUserName(),driver.getUserName());
        expected = rider.getUserName();

        assertEquals(order1.getRider(),expected);
    }

    /*
   This method tests whether the setRider() sets who the driver is in this order.
   */
    @Test
    public void setRider() {
        String startLocation ="UofA";
        String endLocation = "CityCenter";
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