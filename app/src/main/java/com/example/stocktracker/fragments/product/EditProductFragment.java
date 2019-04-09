package com.example.stocktracker.fragments.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.model.entity.Product;

public class EditProductFragment extends Fragment {

    EditText edit_pName, edit_pUrl, edit_pImage;
    Button delete;
    Product product;
    Button toolbar_button, toolbar_buttonEnd;

    public static final String PRODUCT_VALUE = "product value";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_product_layout, container, false);

        edit_pName = view.findViewById(R.id.editPName);
        edit_pUrl = view.findViewById(R.id.editPUrl);
        edit_pImage = view.findViewById(R.id.editPImage);
        edit_pImage = view.findViewById(R.id.editPImage);
        delete = view.findViewById(R.id.deleteB);


        ProductDetailFragment fragment = (ProductDetailFragment) getFragmentManager().findFragmentByTag("prodselect_fragment");
        product= (Product)fragment.getArguments().getSerializable(PRODUCT_VALUE);


        edit_pName.setText(product.getProduct_name());
        edit_pUrl.setText(product.getProduct_url());
        edit_pImage.setText(product.getProduct_image());


        //Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar_button = view.findViewById(R.id.toolbarButton);
        toolbar_buttonEnd = view.findViewById(R.id.toolbarButtonEnd);
        TextView toolbar_textview = view.findViewById(R.id.toolbarText);

        toolbar.setBackgroundColor(getResources().getColor(R.color.reddishPink));
        toolbar_textview.setText("Edit Product");
        toolbar_buttonEnd.setText("Cancel");
        toolbar_button.setText("Save");
        toolbar_button.setTextColor(getResources().getColor(R.color.darkBrown));
        toolbar_textview.setTextSize(20);
        toolbar_textview.setTextColor(getResources().getColor(R.color.darkBrown));

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        toolbar_buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }
}
