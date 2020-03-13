package com.example.missiond;

import android.location.Address;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    /*
    This method tests whether the getUserName() returns the User's name.
     */
    @Test
    void getUserName() {
        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";

        String expected = "Brian";
        String output;

        User user = new User(userName,phoneNumber,emailAddress);
        output = user.getUserName();
        assertEquals(output,expected);

    }

    /*
    This method tests whether the setUserName() sets the User's name.
     */
    @Test
    void setUserName() {
        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";

        String expected = "Elena";
        String output;

        User user = new User(userName,phoneNumber,emailAddress);
        user.setUserName("Elena");
        output = user.getUserName();
        assertEquals(output,expected);
    }

    /*
    This method tests whether the getPhoneNumber() returns a String with User's phone number.
     */
    @Test
    void getPhoneNumber() {
        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";

        String expected = "1234567890";
        String output;

        User user = new User(userName,phoneNumber,emailAddress);
        output = user.getPhoneNumber();
        assertEquals(output,expected);
    }

    /*
    This method tests whether the setPhoneNumber() sets User's phonenumber.
     */
    @Test
    void setPhoneNumber() {
        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";

        String expected = "911";
        String output;

        User user = new User(userName,phoneNumber,emailAddress);
        user.setUserName("911");
        output = user.getUserName();
        assertEquals(output,expected);
    }
    /*
    This method tests whether the getPhoneNumber() returns a String with User's Email Address.
     */
    @Test
    void getEmailAddress() {
        String userName = "Brian";
        String phoneNumber = "1234567890";
        String emailAddress = "Brian@cmput301.com";

        String expected = "Brian@cmput301.com";
        String output;

        User user = new User(userName,phoneNumber,emailAddress);
        output = user.getEmailAddress();
        assertEquals(output,expected);
    }
}