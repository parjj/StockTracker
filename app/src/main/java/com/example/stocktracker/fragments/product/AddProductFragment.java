package com.example.stocktracker.fragments.product;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.stocktracker.R;
import com.example.stocktracker.model.Database.LocalDatabase;
import com.example.stocktracker.model.entity.Product;

public class AddProductFragment extends Fragment {

    EditText productName, productUrl, productImage;
    Button save, cancel;
    Product product;

    LocalDatabase localDatabase;

    ProductFragment productFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDatabase = LocalDatabase.getDb(getContext().getApplicationContext());
        product = new Product();
        productFragment = (ProductFragment) getFragmentManager().findFragmentByTag("prod_list");

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
                product.setCompanyId(productFragment.companyID);

                // add product
                new InsertProduct(product).execute();

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


    //Async Insert task class
    public class InsertProduct extends AsyncTask<Void, Void, Void> {

        Product in_product;

        public InsertProduct(Product product) {
            this.in_product = product;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            localDatabase.daoAccess().addNewProduct(in_product);
            return null;
        }
    }
}

