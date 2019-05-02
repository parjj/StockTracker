package com.example.stocktracker.fragments.product;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.model.DaoImpl;
import com.example.stocktracker.model.entity.Product;

public class EditProductFragment extends Fragment {

    EditText edit_pName, edit_pUrl, edit_pImage;
    Button delete;
    Product product;
    Button toolbar_button, toolbar_buttonEnd;

    public static final String PRODUCT_VALUE = "product value";

    WebViewProduct webViewProduct;
    ProductFragment productFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewProduct = (WebViewProduct) getFragmentManager().findFragmentByTag("webview_fragment");
        productFragment = (ProductFragment) getFragmentManager().findFragmentByTag("prod_list");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_product_layout, container, false);

        edit_pName = view.findViewById(R.id.editPName);
        edit_pUrl = view.findViewById(R.id.editPUrl);
        edit_pImage = view.findViewById(R.id.editPImage);
        delete = view.findViewById(R.id.deleteB);

//        product= (Product)webViewProduct.getArguments().getSerializable(PRODUCT_VALUE);

        product = DaoImpl.getInstance().getProduct(webViewProduct.getArguments().
                getInt("prod_position"));

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
        toolbar_buttonEnd.setVisibility(View.VISIBLE);
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

        //delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //delete product
                DaoImpl.getInstance().deleteProduct(product);
                productFragment.productNames.remove(product);
                productFragment.reload();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, productFragment).commit();

            }
        });

        // cancel button
        toolbar_buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        //save button updates the product with new changes
        toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edit_pName.getText().toString();
                String url = edit_pUrl.getText().toString();
                String image = edit_pImage.getText().toString();

                product = new Product(name, url, image);

                DaoImpl.getInstance().updateProduct(product);

                webViewProduct.product_web.loadUrl(product.getProduct_url());
                webViewProduct.product_select.setText(product.getProduct_name());

                productFragment.productNames.set(webViewProduct.getArguments().
                        getInt("prod_position"), product);
                productFragment.reload();
                getFragmentManager().popBackStackImmediate();


            }
        });
    }
}
