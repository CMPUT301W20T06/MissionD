package com.example.missiond;

/**
 * This is Driver class that stores Driver information
 * @author
 * Yifei Ma
 */
public class Driver extends User {
    private Boolean availability;
    private int thumbUp = 1;
    private int thumbDown = 0;
    public Driver(String userName, String phoneNumber, String emailAddress) {
        super(userName, phoneNumber, emailAddress);
        this.availability = true;
    }
    /**
     * This is a constructor for firebase to convert database data to Driver class
     */
    public Driver(){
    }

    /**
     * This set availability of driver
     */
    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    /**
     * This get the availability of driver
     * @return
     *  Return Boolean
     */
    public Boolean getAvailability() {
        return this.availability;
    }

    /**
     * This increase thumbUp by i
     */
    public void increaseThumbUp() {
        this.thumbUp += 1;
    }

    /**
     * This increase thumbDown by i
     */
    public void increaseThumbDown() {
        this.thumbDown += 1;
    }

    /**
     * This calculate rating of the driver
     * @return
     *  Return float
     */
    public float getRating() {
//        return (float) this.thumbUp;
        return (float) this.thumbUp / (this.thumbDown + this.thumbUp) * 5;
    }
}
