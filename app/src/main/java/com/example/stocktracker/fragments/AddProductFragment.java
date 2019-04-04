package com.example.stocktracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.stocktracker.R;
import com.example.stocktracker.model.Product;

public class AddProductFragment extends Fragment {

    EditText   pName,pUrl,pImage;
    Button save,cancel;
    Product product;

    public static final String PRODUCT_CLASS = "product value";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


/*
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_product_layout, container, false);

        pName=view.findViewById(R.id.adPName);
        pUrl=view.findViewById(R.id.adPUrl);
        pImage=view.findViewById(R.id.adPImage);
        save=view.findViewById(R.id.b2save);
        cancel=view.findViewById(R.id.b1Cancel);

        return view;


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_back,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Bundle bundle= new Bundle();
        bundle.putSerializable(PRODUCT_CLASS, product);

        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                product.setProduct_url(pUrl.getText().toString());
                product.setProduct_name(pName.getText().toString());
                product.setProduct_image(pImage.getText().toString());

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pName.setText("");
                pUrl.setText("");
                pImage.setText("");
            }
        });
    }





}

