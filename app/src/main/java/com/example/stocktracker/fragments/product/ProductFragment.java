package com.example.stocktracker.fragments.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.adapter.ProductListAdapter;
import com.example.stocktracker.fragments.company.AddCompanyFragment;
import com.example.stocktracker.model.Company;
import com.example.stocktracker.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {


    // this class has the list view by getting datas from add product fragment and the list view is represented by productlistadapter FYR(4 ur ref)
    ImageView logo;
    TextView textView;
    ListView listView;
    List<Product> productNames;

    FloatingActionButton actionButton;
    Bundle bundle;

    public static final String COMPANY_VALUE = "company value";
    public static final String PRODUCT_CLASS = "product value";
    public static final String PRODUCT_FRAGMENT = "product_fragment";
    public static final String ADD_PRODUCT_TAG = "add_product_fragment";

    ProductListAdapter productListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_page_layout, container, false);

        productNames = new ArrayList<Product>();
        bundle = getArguments();
        Company company = (Company) bundle.getSerializable(COMPANY_VALUE);


        //Toolbar            //not owrking the old values of toolbar with new ones
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        Button toolbar_button = view.findViewById(R.id.toolbarButton);
        TextView toolbar_textview = view.findViewById(R.id.toolbarText);

        toolbar.setBackgroundColor(getResources().getColor(R.color.reddishPink));

        toolbar_button.setText("BACK");
        toolbar_button.setTextColor(getResources().getColor(R.color.darkBrown));

        toolbar_textview.setText(company.getCompany_name());
        toolbar_textview.setTextSize(20);
        toolbar_textview.setTextColor(getResources().getColor(R.color.darkBrown));

        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.inflateMenu(R.menu.menu_padd);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                AddProductFragment addProductFragment = new AddProductFragment();
                fm.getFragments().add(addProductFragment);
                fragmentTransaction.add(R.id.fragment_container, addProductFragment);
                fragmentTransaction.addToBackStack("product_fragmentlist");
                fragmentTransaction.commit();

                return false;
            }
        });


        logo = view.findViewById(R.id.pImgView);
        textView = view.findViewById(R.id.pCompanyName);

        //listview checks
        listView = view.findViewById(R.id.plist);
        productListAdapter = new ProductListAdapter(getContext(), productNames);
        listView.setAdapter(productListAdapter);
        listView.setEmptyView(view.findViewById(R.id.product_emptyLayout));

        textView.setText(company.getCompany_name() + "(" + company.getCompany_stockName() + ")");
        Picasso.get().load(company.getUrl()).resize(512, 512).into(logo);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                ProductSelectFragment productSelectFragment = new ProductSelectFragment();
                fragmentTransaction.add(R.id.fragment_container, productSelectFragment);
                fragmentTransaction.addToBackStack(PRODUCT_FRAGMENT);
                fragmentTransaction.commit();
            }
        });


    }

    //menu
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_padd,menu);
//    }

    //adapter notify
    public void reload() {
        productListAdapter.notifyDataSetChanged();
    }





}
