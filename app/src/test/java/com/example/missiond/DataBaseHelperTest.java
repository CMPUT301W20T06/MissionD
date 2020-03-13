package com.example.missiond;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class DataBaseHelperTest {
    private DataBaseHelper db = DataBaseHelper.getInstance();

    @Test
    public void testSingleton() {
        DataBaseHelper db_1 = DataBaseHelper.getInstance();
        assertSame(db, db_1);
    }

    @Test
    public void getUser() {
    }

    @Test
    public void getUserOrders() {
    }

    @Test
    public void getAllOrders() {
    }
}