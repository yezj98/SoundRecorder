package com.danielkim.soundrecorder.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.danielkim.soundrecorder.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PauseButton {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.RECORD_AUDIO",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void pauseButton() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btnRecord),
                        childAtPosition(
                                allOf(withId(R.id.fragment_record),
                                        withParent(withId(R.id.pager))),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btnPause), withText("Pause"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_record),
                                        withParent(withId(R.id.pager))),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnPause), withText("Pause"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_record),
                                        withParent(withId(R.id.pager))),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.btnRecord),
                        childAtPosition(
                                allOf(withId(R.id.fragment_record),
                                        withParent(withId(R.id.pager))),
                                0),
                        isDisplayed()));
        floatingActionButton2.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
