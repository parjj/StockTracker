package com.example.stocktracker.fragments;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.adapter.CompanyListAdapter;
import com.example.stocktracker.adapter.ProductListAdapter;
import com.example.stocktracker.model.Company;
import com.example.stocktracker.model.Product;

public class ProductFragment extends Fragment {

    // this class has the list view by getting datas from add product fragment and the list view is represented by productlistadapter FYR(4 ur ref)
    ImageView logo;
    TextView textView;
    ListView listView;

    FloatingActionButton actionButton;
    Bundle bundle;

    public static final String COMPANY_VALUE = "company value";
    public static final String PRODUCT_CLASS = "product value";

    ProductListAdapter productListAdapter;
    private String productFrag_name;
    private String productFrag_url;
    private String productFrag_image;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_page_layout, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        Button toolbar_button = view.findViewById(R.id.toolbarButton);
        TextView toolbar_textview = view.findViewById(R.id.toolbarText);
        toolbar.setBackgroundColor( getResources().getColor(R.color.yellowishGreen) );
        toolbar_button.setText("Edit");
        toolbar_button.setTextColor(getResources().getColor(R.color.peralishWhite));
        toolbar_textview.setText(R.string.watch_list);
        toolbar_textview.setTextColor(getResources().getColor(R.color.peralishWhite));


        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);



        bundle = getArguments();
        Company company = (Company) bundle.getSerializable(COMPANY_VALUE);


        logo = view.findViewById(R.id.pImgView);
        textView = view.findViewById(R.id.pCompanyName);
        listView = view.findViewById(R.id.pList);
        listView.setEmptyView(view.findViewById(R.id.product_emptyLayout));

        textView.setText(company.getCompany_name());
       // logo.setImageResource(company.getimgValue());


        return view;


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        AddProductFragment fragment_addProduct = new AddProductFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment_addProduct);
       // fragmentTransaction.addToBackStack("company_fragment");
        fragmentTransaction.commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Product product_detail;

        bundle = getArguments();
        product_detail = (Product) bundle.getSerializable(PRODUCT_CLASS);
        productListAdapter = new ProductListAdapter(getContext(), product_detail);
        listView.setAdapter(productListAdapter);

    }
}
