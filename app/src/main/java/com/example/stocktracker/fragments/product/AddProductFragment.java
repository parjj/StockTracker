package com.example.stocktracker.fragments.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.stocktracker.R;
import com.example.stocktracker.adapter.ProductListAdapter;
import com.example.stocktracker.model.DaoImpl;
import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

public class AddProductFragment extends Fragment {

    EditText productName, productUrl, productImage;
    Button save, cancel;
    Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product=new Product();

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

        //save button adds a new product
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = productUrl.getText().toString();
                String name = productName.getText().toString();
                String image = productImage.getText().toString();

                product.setProduct_name(name);
                product.setProduct_url(url);
                product.setProduct_image(image);

                // add product
                DaoImpl.getInstance().addNewProduct(product);

                ProductFragment productFragment = (ProductFragment) getFragmentManager().findFragmentByTag("prod_list");

              if(productFragment.productNames.size()==0) {
                  productFragment.productNames.add(product);
                  productFragment.company.setProduct(productFragment.productNames);
              }
                productFragment.reload();

                manager.popBackStack();
            }
        });

        //cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.popBackStack();

            }
        });
    }

}

