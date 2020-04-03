package com.example.missiond;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity=null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(DriverActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor_2= getInstrumentation().addMonitor(RiderActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @Test
    public void testLauch(){
        View view = mActivity.findViewById(R.id.login_driver);
        assertNotNull(view);
    }

    @Test
    public void testLaunchOfDriverActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.login_driver));

        onView(withId(R.id.login_driver)).perform(click());

        Activity driver_login = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(driver_login);

        driver_login.finish();

    }

    @Test
    public void testLaunchOfRiderActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.login_rider));

        onView(withId(R.id.login_rider)).perform(click());

        Activity rider_login = getInstrumentation().waitForMonitorWithTimeout(monitor_2,5000);

        assertNotNull(rider_login);

        rider_login.finish();

    }

    @After
    public void tearDown() throws Exception {
    }
}