package kmitl.projectfinal.se.kmitlbackyard;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import kmitl.projectfinal.se.kmitlbackyard.activity.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        SystemClock.sleep(500);
    }

    @Test
    public void testLaunch() {
        onView(withId(R.id.seach_subject)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner_type)).check(matches(isDisplayed()));
        onView(withId(R.id.engineering_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.architecture_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.education_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.agricultural_techno_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.science_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.agriculture_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.it_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.international_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.nano_techno_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.production_inno_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.management_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.inter_flight_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.liberal_arts_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void testSpinner() {
        onView(withId(R.id.spinner_type)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("ทุกหมวดวิชา"))).perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.spinner_type)).check(matches(withSpinnerText(containsString("ทุกหมวดวิชา"))));

        onView(withId(R.id.engineering_btn)).perform(click());
        onView(withText("01006003 ENGINEERING MATHEMATICS 3")).perform(click());
    }

    @Test
    public void testPost() {
        onView(withId(R.id.spinner_type)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("ทุกหมวดวิชา"))).perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.spinner_type)).check(matches(withSpinnerText(containsString("ทุกหมวดวิชา"))));

        onView(withId(R.id.engineering_btn)).perform(click());
        onView(withText("01006003 ENGINEERING MATHEMATICS 3")).perform(click());

        onView(withId(R.id.write_review_btn)).perform(click());
        onView(withId(R.id.comment)).perform(replaceText("hello world three time"));

        onView(withId(R.id.item_menu_next)).perform(click());
        onView(withId(R.id.post_title)).perform(replaceText("hello world"));

        onView(withId(R.id.item_menu_post)).perform(click());
    }

    @Test
    public void testComment() {
        onView(withId(R.id.navigation_news)).perform(click());
        SystemClock.sleep(2500);

        onView(withText("กันเอง นักเลงพอ")).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {

    }

}