package com.example.stocktracker;

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.stocktracker.model.Database.LocalDatabase;
import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)         //this can be used tos structure your run call for the functions

public class DbInstrumentTest {

    private static LocalDatabase localDatabase;


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);


    @BeforeClass
    public static void createDb() throws Exception {

        Context context = InstrumentationRegistry.getContext();
        localDatabase = Room.inMemoryDatabaseBuilder(context, LocalDatabase.class).build();


    }

    @AfterClass
    public static void closeDb() throws IOException {
        localDatabase.close();
    }


    @Before
    public void before() {


        Company company = new Company();

        company.setCompany_name("Tesla");
        company.setCompany_stockName("TSLA");
        company.setUrl("https://userscontent2.emaze.com/images/dc1bd939-2c82-4b65-9a4a-d30754860c2c/e90b0328-8342-4ab9-9661-3f83690e1d51image01.png");
        company.setRate(100.00);
        localDatabase.daoAccess().addNewCompany(company);

        Product product1 = new Product();
        product1.setProduct_name("Mini SuperCharger");
        product1.setProduct_url("https://www.etsy.com/listing/620777993/mini-tesla-supercharger-tesla?gpla=1&gao=1&&utm_source=google&utm_medium=cpc&utm_campaign=shopping_us_c-electronics_and_accessories-docking_and_stands-docking_stations&utm_custom1=b789475a-597c-4cdb-8798-f04c7dcf561b&utm_content=go_304502075_22746069635_78727298915_pla-106554179795_c__620777993&gclid=EAIaIQobChMI_PSj2dn_4QIVkVqGCh3hAwZoEAkYCSABEgIgwPD_BwE");
        product1.setProduct_image("https://i.etsystatic.com/17897391/r/il/bdb329/1860405860/il_794xN.1860405860_8uty.jpg");
        product1.setCompanyId(company.getId());
        localDatabase.daoAccess().addNewProduct(product1);


        Product product2 = new Product();
        product2.setProduct_name("Tesla T-shirt");
        product2.setProduct_url("https://www.cafepress.com/+,1293115580/?utm_medium=cpc&utm_source=pla-google&utm_campaign=653161166-d-c&utm_content=31173505377-adid-96354654250&utm_term=pla-585005665864-pid-1293115580&gclid=EAIaIQobChMI-Ivji6_B4QIVAUCGCh3hKARaEAYYBCABEgIQD_D_BwE");
        product2.setProduct_image("https://i3.cpcache.com/merchandise/497_550x550_Front_Color-WithCheckerPant.jpg?Size=Small&AttributeValue=NA&region={\\\"name\\\":\\\"FrontCenter\\\",\\\"width\\\":10,\\\"height\\\":10,\\\"alignment\\\":\\\"TopCenter\\\",\\\"orientation\\\":0,\\\"dpi\\\":100,\\\"crop_x\\\":0,\\\"crop_y\\\":0,\\\"crop_h\\\":1000,\\\"crop_w\\\":1000,\\\"scale\\\":0,\\\"template\\\":{\\\"id\\\":88800272,\\\"params\\\":{}}}");
        product2.setCompanyId(company.getId());
        localDatabase.daoAccess().addNewProduct(product2);

    }

    // Before each @Test @Before gets called and not a necessity the @Test gets called on the order you write
    @Test
    public void writeToAndReadFromDB() throws Exception {

        localDatabase.daoAccess().getAllCompanies().observeForever(new Observer<List<Company>>() {
            @Override
            public void onChanged(List<Company> companies) {
                assertThat(companies.get(0).getCompany_name(), equalTo("Tesla"));
            }

        });

        localDatabase.daoAccess().getProductsForCompany(Long.valueOf(1)).observeForever(new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

                //   assertThat(company.get(0).getModel(), equalTo("Red Bull"));
                if (products.size() < 2) {

                    Assert.assertSame(products.size(), 2);
                }

            }
        });


        Company company1 = new Company();
        company1.setCompany_name("Tesla CAR");
        company1.setId((long) 1);

        localDatabase.daoAccess().updateCompany(company1);
        Assert.assertEquals(company1.getCompany_name(), "Tesla CAR");

        company1.setId((long) 1);
        localDatabase.daoAccess().deleteCompany(company1);

        Product product = new Product();
        product.setProduct_name("T-Shirt");
        product.setId((long) 2);
        product.setCompanyId((long) 1);
        localDatabase.daoAccess().updateProduct(product);
        Assert.assertEquals(product.getProduct_name(), "T-shirt");

        localDatabase.daoAccess().deleteProduct(product);

    }

  /*  @Test
    public void oneMoreTest() {

    }*/


}


