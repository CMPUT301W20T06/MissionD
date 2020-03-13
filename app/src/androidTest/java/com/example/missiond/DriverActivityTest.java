package com.example.missiond;

import android.app.Activity;
import android.app.Instrumentation;

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

public class DriverActivityTest {

    /*
    This sets up an ActivityTestRule.
     */

    @Rule
    public ActivityTestRule<DriverActivity> mActivityTestRule = new ActivityTestRule<DriverActivity>(DriverActivity.class);

    private DriverActivity mActivity=null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(DriverSearchRequestActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor_2 = getInstrumentation().addMonitor(UserProfileActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }
/*
This tests whether the DriverSearchRequestActivity is launched when the button is clicked.
 */
    @Test
    public void testLaunchOfDriverSearchRequestActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.driverSeeTrip));

        onView(withId(R.id.driverSeeTrip)).perform(click());

        Activity searchRequestActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(searchRequestActivity);

        searchRequestActivity.finish();

    }

    /*
    This tests whether the UserProfileActivity is launched when the button is clicked.
     */
    @Test
    public void testLaunchOfUserProfileActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.profile_driver));

        onView(withId(R.id.profile_driver)).perform(click());

        Activity UserProfileActivity = getInstrumentation().waitForMonitorWithTimeout(monitor_2,5000);

        assertNotNull(UserProfileActivity);

        UserProfileActivity.finish();

    }

    @After
    public void tearDown() throws Exception {

    }
}