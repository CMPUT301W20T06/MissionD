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

public class DriverPickeupRiderActivityTest {


    @Rule
    public ActivityTestRule<DriverPickeupRiderActivity> mActivityTestRule = new ActivityTestRule<DriverPickeupRiderActivity>(DriverPickeupRiderActivity.class);

    private DriverPickeupRiderActivity mActivity=null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(DriverCompleteTripActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }


    /*
This tests whether the CompleteTripActivity is launched when the button is clicked.
 */
    @Test
    public void testLaunchOfCompleteTripActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.pickup_button));

        onView(withId(R.id.pickup_button)).perform(click());

        Activity CompleteTripActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(CompleteTripActivity);

        CompleteTripActivity.finish();

    }

    @After
    public void tearDown() throws Exception {

    }
}