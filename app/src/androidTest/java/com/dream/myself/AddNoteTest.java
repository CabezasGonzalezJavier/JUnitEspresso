package com.dream.myself;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.dream.myself.addnote.AddNoteActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by javierg on 03/10/2017.
 */

public class AddNoteTest {

    @Rule
    public IntentsTestRule<BaseActivity> mAddNoteIntentsTestRule =
            new IntentsTestRule<BaseActivity>(BaseActivity.class);

//    @Before
//    public void registerIdlingResource() {
//        Espresso.registerIdlingResources(
//                mAddNoteIntentsTestRule.getActivity().getCountingIdlingResource());
//    }
//
//    @Test
//    public void errorShownOnEmptyMessage() {
//
//        onView(withId(R.id.fab_add_notes)).perform(click());
//        // Add note title and description
//        onView(withId(R.id.add_note_title)).perform(typeText(""));
//        onView(withId(R.id.add_note_description)).perform(typeText(""),
//                closeSoftKeyboard());
//        // Save the note
//        onView(withId(R.id.fab_add_notes)).perform(click());
//    }
//
//    @After
//    public void unregisterIdlingResource() {
//        Espresso.unregisterIdlingResources(
//                mAddNoteIntentsTestRule.getActivity().getCountingIdlingResource());
//    }
}
