package com.example.stocktracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;

import com.example.stocktracker.fragments.product.ProductFragment;
import com.example.stocktracker.model.Database.LocalDatabase;
import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xml.sax.Locator;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.model.Atoms.getCurrentUrl;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

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
        localDatabase = Room.inMemoryDatabaseBuilder(context, LocalDatabase.class).build();

        mActivityRule.getActivity().getSupportFragmentManager().beginTransaction();


        Company company = new Company();

        company.setCompany_name("Tesla");
        company.setCompany_stockName("TSLA");
        company.setUrl("https://userscontent2.emaze.com/images/dc1bd939-2c82-4b65-9a4a-d30754860c2c/e90b0328-8342-4ab9-9661-3f83690e1d51image01.png");
        company.setRate(100.00);
        localDatabase.daoAccess().addNewCompany(company);

        Product product2 = new Product();
        product2.setProduct_name("Tesla T-shirt");
        product2.setProduct_url("https://www.cafepress.com/+,1293115580/?utm_medium=cpc&utm_source=pla-google&utm_campaign=653161166-d-c&utm_content=31173505377-adid-96354654250&utm_term=pla-585005665864-pid-1293115580&gclid=EAIaIQobChMI-Ivji6_B4QIVAUCGCh3hKARaEAYYBCABEgIQD_D_BwE");
        product2.setProduct_image("https://i3.cpcache.com/merchandise/497_550x550_Front_Color-WithCheckerPant.jpg?Size=Small&AttributeValue=NA&region={\\\"name\\\":\\\"FrontCenter\\\",\\\"width\\\":10,\\\"height\\\":10,\\\"alignment\\\":\\\"TopCenter\\\",\\\"orientation\\\":0,\\\"dpi\\\":100,\\\"crop_x\\\":0,\\\"crop_y\\\":0,\\\"crop_h\\\":1000,\\\"crop_w\\\":1000,\\\"scale\\\":0,\\\"template\\\":{\\\"id\\\":88800272,\\\"params\\\":{}}}");
        product2.setCompanyId(company.getId());
        localDatabase.daoAccess().addNewProduct(product2);

    }

    @After
    public void closeDb() throws Exception {
        localDatabase.close();
    }


    @Test
    public void addNewCompanies() {

        onView(withId(R.id.addMenu)).perform(click());

        onView(withId(R.id.adCName)).perform(typeText("Google"), closeSoftKeyboard());
        onView(withId(R.id.adCCode)).perform(typeText("GOOGL"), closeSoftKeyboard());
        onView(withId(R.id.adCImage)).perform(typeText("https://image.flaticon.com/teams/slug/google.jpg"), closeSoftKeyboard());
        onView(withText("save")).perform(click());

        onView(withText("EDIT")).perform(click());
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());

        onView(withId(R.id.editCName)).perform(replaceText("WWE Championship"), closeSoftKeyboard());
     //   onView(withId(R.id.adCName)).perform(typeText("WWE Championship"), closeSoftKeyboard());


        onView(withText("Save")).perform(click());


        onView(withText("DONE")).perform(click());

      //  onView(withId(R.id.editCName)).check(matches(hasValueEqualTo("WWE Championship")));

        // Assert.assertTrue(withId(R.id.editCName).equals(withId(R.id.adCName)));
       // Assert.assertTrue(withText("WWE").equals("WWE Championship"));


        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(1).perform(click());


        //ProductFragment fragment = new ProductFragment();  // need to ask kaushik for this issue

      //  mActivityRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();


        onView(withId(R.id.addMenuP)).perform(click());
        onView((withId(R.id.adPName))).perform(typeText("Google Home"), closeSoftKeyboard());
        onView(withId(R.id.adPUrl)).perform(typeText("https://www.bestbuy.com/site/google-home-smart-speaker-with-google-assistant-white-slate/5578849.p?skuId=5578849"), closeSoftKeyboard());
        onView(withId(R.id.adPImage)).perform(typeText("https://icdn4.digitaltrends.com/image/google-home-security-thumb-2-1500x1000.jpg"), closeSoftKeyboard());

        onView(withText("SAVE")).perform(click());

        onData(anything()).inAdapterView(withId(R.id.plist)).atPosition(0).perform(click());

      //  onView(withId(R.id.webProdName)).perform(typeText("Google Home"), closeSoftKeyboard());
    //   onWebView().check(webMatches(getCurrentUrl(), containsString("https://www.bestbuy.com/site/google-home-smart-speaker-with-google-assistant-white-slate/5578849.p?skuId=5578849"))).perform(webClick());

        onView(withText("EDIT")).perform(click());

        onView(withId(R.id.editPName)).perform(typeText("Google Home White"), closeSoftKeyboard());
        onView(withText("save")).perform(click());

 }




}

