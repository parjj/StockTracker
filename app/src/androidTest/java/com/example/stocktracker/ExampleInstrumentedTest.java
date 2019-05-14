package com.example.stocktracker;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;

import com.example.stocktracker.model.Database.LocalDatabase;
import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/*import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.model.Atoms.getCurrentUrl;

import static androidx.test.espresso.web.sugar.Web.onWebView;*/
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.model.Atoms.getCurrentUrl;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.containsString;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private LocalDatabase localDatabase;
    private Company company;
    private Product product;


  /*  @Test
    public void useAppContext() {    // an initial code present in this class
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        // commented ui test
        assertEquals("com.example.stocktracker", appContext.getPackageName());
    }*/


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void createDb() throws Exception {

        Context context = InstrumentationRegistry.getContext();
       localDatabase = Room.databaseBuilder(context,
                LocalDatabase.class, "company_database.db").build();
       //localDatabase= Room.inMemoryDatabaseBuilder(context,localDatabase.getClass()).build();
       //mActivityRule.getActivity().getSupportFragmentManager().beginTransaction();

    }

    @After
    public void closeDb() throws Exception {
        localDatabase.close();
    }


    @Test
    public void addNewCompanies() throws Exception {

        onView(withId(R.id.addMenu)).perform(click());

        onView(withId(R.id.adCName)).perform(typeText("Google"), closeSoftKeyboard());
        onView(withId(R.id.adCCode)).perform(typeText("GOOGL"), closeSoftKeyboard());
        onView(withId(R.id.adCImage)).perform(typeText("https://image.flaticon.com/teams/slug/google.jpg"), closeSoftKeyboard());
        onView(withText("save")).perform(click());
        Thread.sleep(1000);
        onView(withText("EDIT")).perform(click());
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());

        onView(withId(R.id.editCName)).perform(replaceText("WWE Champs"), closeSoftKeyboard());
        onView(withText("Save")).perform(click());
        onView(withText("DONE")).perform(click());

       // onView(withText("EDIT")).perform(click());
      /*  onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());

        onView(withText("Delete")).perform(click());
        onView(withText("DONE")).perform(click());*/

        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(1).perform(click());

        // product fragment
        onView( withIndex (withId(R.id.addMenu), 1 ) ).perform(click());
        onView((withId(R.id.adPName))).perform(typeText("Google Home"), closeSoftKeyboard());
        onView(withId(R.id.adPUrl)).perform(typeText("https://www.bestbuy.com/site/google-home-smart-speaker-with-google-assistant-white-slate/5578849.p?skuId=5578849"), closeSoftKeyboard());
        onView(withId(R.id.adPImage)).perform(typeText("https://icdn4.digitaltrends.com/image/google-home-security-thumb-2-1500x1000.jpg"), closeSoftKeyboard());

        onView(withText("SAVE")).perform(click());

        onData(anything()).inAdapterView(withId(R.id.plist)).atPosition(0).perform(click());

        onWebView().check(webMatches(getCurrentUrl(), containsString("https://www.bestbuy.com/site/google-home-smart-speaker-with-google-assistant-white-slate/5578849.p?skuId=5578849")));
        Thread.sleep(2000);

        onView(withId(R.id.editM)).perform(click());
        Thread.sleep(2000);

        onView(withId(R.id.editPName)).perform(replaceText("Google Home Buy"), closeSoftKeyboard());
        onView(withText("Save")).perform(click());

        onView(withText("Back")).perform(click());



        onView( withIndex (withId(R.id.addMenu), 1 ) ).perform(click());
        onView((withId(R.id.adPName))).perform(typeText("Google xxx"), closeSoftKeyboard());
        onView(withId(R.id.adPUrl)).perform(typeText("https://www.bestbuy.com/site/google-home-smart-speaker-with-google-assistant-white-slate/5578849.p?skuId=5578849"), closeSoftKeyboard());
        onView(withId(R.id.adPImage)).perform(typeText("https://icdn4.digitaltrends.com/image/google-home-security-thumb-2-1500x1000.jpg"), closeSoftKeyboard());

        onView(withText("SAVE")).perform(click());

        onData(anything()).inAdapterView(withId(R.id.plist)).atPosition(0).perform(click());

        onWebView().check(webMatches(getCurrentUrl(), containsString("https://www.bestbuy.com/site/google-home-smart-speaker-with-google-assistant-white-slate/5578849.p?skuId=5578849")));
        Thread.sleep(1000);

        onView(withId(R.id.editM)).perform(click());
        Thread.sleep(1000);
        onView(withText("DELETE")).perform(click());

        onView(withText("BACK")).perform(click());

        onView(withText("EDIT")).perform(click());
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(1).perform(click());
        onView(withText("DELETE")).perform(click());


    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }


}

