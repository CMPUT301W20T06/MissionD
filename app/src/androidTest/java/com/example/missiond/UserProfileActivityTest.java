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

public class UserProfileActivityTest {

    @Rule
    public ActivityTestRule<UserProfileActivity> mActivityTestRule = new ActivityTestRule<UserProfileActivity>(UserProfileActivity.class);

    private Activity mActivity=null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(DriverSearchRequestActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfSearchRequestActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.profileBack));

        onView(withId(R.id.profileBack)).perform(click());



        assertNotNull(mActivity);



    }

    @After
    public void tearDown() throws Exception {

    }
}