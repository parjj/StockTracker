package com.example.stocktracker.fragments.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.example.stocktracker.model.DaoImpl;
import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {


    // this class has the list view by getting datas from add product fragment and the list view is represented by productlistadapter FYR(4 ur ref)
    ImageView logo;
    TextView textView;
    ListView listView;
    List<Product> productNames;

    Bundle bundle;
    Button toolbar_button;

    public static final String COMPANY_VALUE = "company value";
    public static final String PRODUCT_VALUE = "product value";
    public static final String PRODUCT_FRAGMENT = "product_fragment";

    ProductListAdapter productListAdapter;
    Company company;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_page_layout, container, false);

        // the company selected
        bundle = getArguments();

        company = DaoImpl.getInstance().getCompany(bundle.getInt("selected_position"));

        //Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar_button = view.findViewById(R.id.toolbarButton);
        TextView toolbar_textview = view.findViewById(R.id.toolbarText);

        toolbar.setBackgroundColor(getResources().getColor(R.color.reddishPink));

        toolbar_button.setText("BACK");
        toolbar_button.setTextColor(getResources().getColor(R.color.darkBrown));

        toolbar_textview.setText(company.getCompany_name());
        toolbar_textview.setTextSize(20);
        toolbar_textview.setTextColor(getResources().getColor(R.color.darkBrown));

        toolbar.inflateMenu(R.menu.menu_add);

        //add new products to the company
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
        productNames = DaoImpl.getInstance().getProductsForCompany(company);

        if(productNames==null) {
            productNames = new ArrayList<Product>();
        }
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

        //product web view on selection of the product
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           //     Product product = (Product) productListAdapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putInt("prod_position", position);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                WebViewProduct webViewProduct = new WebViewProduct();
                fragmentTransaction.add(R.id.fragment_container, webViewProduct, "webview_fragment");
                webViewProduct.setArguments(bundle);
                fragmentTransaction.addToBackStack(PRODUCT_FRAGMENT);
                fragmentTransaction.commit();
            }
        });

        //back button
        toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }

    //adapter notify
    public void reload() {
        productListAdapter.notifyDataSetChanged();
    }

}
