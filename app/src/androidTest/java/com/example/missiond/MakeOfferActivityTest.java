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

public class MakeOfferActivityTest {

    @Rule
    public ActivityTestRule<DriverMakeOfferActivity> mActivityTestRule = new ActivityTestRule<DriverMakeOfferActivity>(DriverMakeOfferActivity.class);

    private DriverMakeOfferActivity mActivity=null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(DriverSearchRequestActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfSearchRequestActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.DriverBack));

        onView(withId(R.id.DriverBack)).perform(click());

        Activity searchRequestActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(searchRequestActivity.findViewById(R.id.DriverBack));

        searchRequestActivity.finish();

    }

    @After
    public void tearDown() throws Exception {
    }
}