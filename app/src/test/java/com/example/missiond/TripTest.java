package com.example.missiond;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class TripTest {

    @Test
    void getLocationName() throws Exception{
        String input = "CityCenter";
        String input_2 = "HUB";
        String output;
        String expected = "CityCenter";

        Trip trip = new Trip(input,input_2);
        output = trip.getLocationName();

        assertEquals(expected,output);
    }

    @Test
    void getDestination() throws Exception{
        String input = "CityCenter";
        String input_2 = "HUB";
        String output;
        String expected = "HUB";

        Trip trip = new Trip(input,input_2);
        output = trip.getDestination();

        assertEquals(expected,output);
    }

/**    @Test
    void setTrip(String location, String destination) throws Exception{
        String input = "CityCenter";
        String input_2 = "HUB";
        Trip expected = new Trip("CSC","BusinessBuilding");

        Trip trip = new Trip(input,input_2);
        trip.setTrip( "CSC","BusinessBuilding");
        assertEquals(expected.getDestination(),trip.getDestination());
    }
*/
}