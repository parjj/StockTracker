package com.example.stocktracker.fragments.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.stocktracker.R;
import com.example.stocktracker.model.Company;
import com.example.stocktracker.model.Product;

import java.util.ArrayList;
import java.util.List;

public class AddProductFragment extends Fragment {

    EditText productName, productUrl, productImage;
    Button save, cancel;
    Product product= new Product();
    Company company=new Company();
    Button toolbar_button, toolbar_buttonEnd;

    public static final String PRODUCT_CLASS = "product value";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_product_layout, container, false);

        productName = view.findViewById(R.id.adPName);
        productUrl = view.findViewById(R.id.adPUrl);
        productImage = view.findViewById(R.id.adPImage);
        save = view.findViewById(R.id.b2save);
        cancel = view.findViewById(R.id.b1Cancel);

        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FragmentManager manager = getFragmentManager();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = productUrl.getText().toString();
                String name = productName.getText().toString();
                String image = productImage.getText().toString();

                product.setProduct_name(name);
                product.setProduct_url(url);
                product.setProduct_image(image);


                ProductFragment productFragment = (ProductFragment) getFragmentManager().findFragmentByTag("prod_list");




                //ProductFragment productFragment = (ProductFragment) getFragmentManager().getFragments().get(1);
                productFragment.productNames.add(product);
                productFragment.reload();

                manager.popBackStack();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.popBackStack();

            }
        });
    }

}

