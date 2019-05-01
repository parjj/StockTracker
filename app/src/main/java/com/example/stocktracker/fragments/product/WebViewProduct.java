package com.example.stocktracker.fragments.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.model.entity.Product;

public class WebViewProduct extends Fragment {


    TextView product_select;
    WebView product_web;
    Button toolbar_button, toolbar_buttonEnd;

    Product product= new Product();

    public static final String PRODUCT_VALUE = "product value";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.web_view_layout, container, false);

        product_web=view.findViewById(R.id.webView);
        product_select=view.findViewById(R.id.webProdName);

        //get the product selected
        Bundle bundle = getArguments();
        product = (Product) bundle.getSerializable(PRODUCT_VALUE);

        product_web.loadUrl(product.getProduct_url());
        product_web.getSettings().setJavaScriptEnabled(true);
        product_web.setWebChromeClient(new WebChromeClient());
        product_select.setText(product.getProduct_name());



        //Toolbar

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar_button = view.findViewById(R.id.toolbarButton);
        toolbar_buttonEnd = view.findViewById(R.id.toolbarButtonEnd);
        toolbar_buttonEnd.setVisibility(View.VISIBLE);
        TextView toolbar_textview = view.findViewById(R.id.toolbarText);

        toolbar.setBackgroundColor(getResources().getColor(R.color.reddishPink));
        toolbar_buttonEnd.setText("Edit");
        toolbar_buttonEnd.setVisibility(View.VISIBLE);
        toolbar_button.setText("Back");
        toolbar_button.setTextColor(getResources().getColor(R.color.darkBrown));

        toolbar_textview.setText("Product Link");
        toolbar_textview.setTextSize(20);



        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // back button
        toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });



        // edit button
        toolbar_buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager= getFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                EditProductFragment editProductFragment= new EditProductFragment();
                transaction.add(R.id.fragment_container,editProductFragment,"edit_product_fragment_tag");
                transaction.addToBackStack("product_saved");
                transaction.commit();
            }
        });
    }
}
