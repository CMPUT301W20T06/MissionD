package com.example.missiond;

import android.icu.util.ULocale;
import android.location.Address;
import android.location.Location;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class DriverTest {

/*
This method tests whether the setAvailability() sets the Driver's Availability to 0,1 or 2.
 */

    @Test
    public void setAvailability() {

        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";




        Driver driver = new Driver(userName,phoneNumber,emailAddress);
        Driver driver2 = new Driver(userName,phoneNumber,emailAddress);
        driver.setAvailability(false);
        driver2.setAvailability(true);
        assertNotEquals(driver.getAvailability(),driver2.getAvailability());
    }
    /*
    This method tests whether the getAvailability() returns the Driver's Availability with an int of 0,1,or 2.
     */
    @Test
    public void getAvailability() {
        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";




        Driver driver = new Driver(userName,phoneNumber,emailAddress);
        Driver driver2 = new Driver(userName,phoneNumber,emailAddress);
        driver.setAvailability(false);
        driver2.setAvailability(false);
        assertEquals(driver.getAvailability(),driver2.getAvailability());
    }

    /*
   This method tests whether the increaseThumbUp() increases the driver.thumbup by 1.
    */
    @Test
    public void increaseThumbUp() {
        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";




        Driver driver = new Driver(userName,phoneNumber,emailAddress);
        Driver driver2 = new Driver(userName,phoneNumber,emailAddress);

        driver.increaseThumbUp();
        assertNotSame(driver,driver2);
    }

    /*
   This method tests whether the increaseThumbUp() increases the driver.thumbdown by 1.
    */
    @Test
    public void increaseThumbDown() {
        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";




        Driver driver = new Driver(userName,phoneNumber,emailAddress);
        Driver driver2 = new Driver(userName,phoneNumber,emailAddress);

        driver.increaseThumbUp();
        driver2.increaseThumbUp();
        driver2.increaseThumbDown();
        assertNotSame(driver,driver2);
    }
    /*
   This method tests whether the getRating() returns a float obj with the actual rating of the driver.
    */
    @Test
    public void getRating() {
        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";


        Float expected = 2.5f;
        Float output;

        Driver driver = new Driver(userName,phoneNumber,emailAddress);


        driver.increaseThumbDown();//The initial value of driver.thumbdown is 0,so now it is 1.
        output = driver.getRating();// 1/(1 + 1) times 5 is 2.5

        assertEquals(output,expected);


    }
}