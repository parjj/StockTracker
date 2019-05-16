package com.example.stocktracker.fragments.product;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.fragments.company.CompanyListFragment;
import com.example.stocktracker.model.DaoInterface;
import com.example.stocktracker.model.database.DaoRoomImpl;
import com.example.stocktracker.model.database.LocalDatabase;
import com.example.stocktracker.model.entity.Product;

public class EditProductFragment extends Fragment {

    EditText edit_pName, edit_pUrl, edit_pImage;
    Button delete;
    Product product;
    Button toolbar_button, toolbar_buttonEnd;

    public static final String PRODUCT_VALUE = "product value";

    WebViewProduct webViewProduct;
    ProductFragment productFragment;
    CompanyListFragment companyListFragment;
    DaoInterface daoInterface;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webViewProduct = (WebViewProduct) getFragmentManager().findFragmentByTag("webview_fragment");
        productFragment = (ProductFragment) getFragmentManager().findFragmentByTag("prod_list");
        daoInterface = DaoRoomImpl.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_product_layout, container, false);

        edit_pName = view.findViewById(R.id.editPName);
        edit_pUrl = view.findViewById(R.id.editPUrl);
        edit_pImage = view.findViewById(R.id.editPImage);
        delete = view.findViewById(R.id.deleteB);

        product= productFragment.product;
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

                new DeleteProduct(product).execute();
                getFragmentManager().popBackStack(ProductFragment.PRODUCT_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

                product.setProduct_name(name);
                product.setProduct_url(url);
                product.setProduct_image(image);

                new UpdateProduct(product).execute();
                //companyListFragment.reload();

                webViewProduct.product_web.loadUrl(product.getProduct_url());
                webViewProduct.product_select.setText(product.getProduct_name());


                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    //Async Delete task class
    public class DeleteProduct extends AsyncTask<Void, Void, Void> {

        Product del_product;

        public DeleteProduct(Product del_product) {
            this.del_product = del_product;
        }

        @Override
        protected Void doInBackground(Void... people) {
            daoInterface.deleteProduct(del_product);
            return null;
        }
    }


    //Async Update task class
    public class UpdateProduct extends AsyncTask<Void, Void, Product> {

        private  Product up_product;

        public UpdateProduct(Product up_product) {
            this.up_product = up_product;
        }

        @Override
        protected Product doInBackground(Void... people) {
            daoInterface.updateProduct(up_product);
            return up_product;
        }
    }
}
