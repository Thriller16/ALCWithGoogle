package ng.com.africasupport.alcwithgoogle;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.edit_fab),
                        childAtPosition(
                                allOf(withId(R.id.home_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_text),
                        childAtPosition(
                                allOf(withId(R.id.home_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edit_text),
                        childAtPosition(
                                allOf(withId(R.id.home_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("My first day at school"), closeSoftKeyboard());

        pressBack();

        ViewInteraction editText = onView(
                allOf(withId(R.id.edit_text), withText("My first day at school"),
                        childAtPosition(
                                allOf(withId(R.id.home_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("My first day at school")));

        ViewInteraction textView = onView(
                allOf(withId(R.id.delete), withContentDescription("delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("")));

        ViewInteraction textView2 = onView(
                allOf(withText("21/6/2018"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("21/6/2018")));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.edit_fab),
                        childAtPosition(
                                allOf(withId(R.id.home_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.delete), withContentDescription("delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

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
