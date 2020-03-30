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

public class SearchRequestActivityTest {

    @Rule
    public ActivityTestRule<DriverSearchRequestActivity> mActivityTestRule = new ActivityTestRule<DriverSearchRequestActivity>(DriverSearchRequestActivity.class);

    private DriverSearchRequestActivity mActivity=null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor_2 = getInstrumentation().addMonitor(DriverMakeOfferActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOf_Back_OnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.DriverDestBack));

        onView(withId(R.id.DriverDestBack)).perform(click());

        Activity mainActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(mainActivity);

        mainActivity.finish();

    }
    @Test
    public void testLaunchOfMakeOfferActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.trip_list));

        onView(withId(R.id.trip_list)).perform(click());

        Activity makeOfferActivity = getInstrumentation().waitForMonitorWithTimeout(monitor_2,5000);

        assertNotNull(makeOfferActivity);

        makeOfferActivity.finish();

    }

    @After
    public void tearDown() throws Exception {
    }
}