package com.demo.in.demoprojacc;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Akshay on 10/30/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest  {

 @Rule public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

 @Test
    public void shouldBeAbleToLaunchMainScreen(){

     onView(withId(R.id.rv_album))
             .check(matches(hasDescendant(withText("Title"))));
 }
































}
