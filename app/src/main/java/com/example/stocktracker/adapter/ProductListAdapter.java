package com.example.stocktracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.model.Product;

public class ProductListAdapter  extends ArrayAdapter {

    ImageView imageViewP;
    TextView nameP,rateP;
    private Product product;


    public ProductListAdapter( Context context, Product product) {

        super(context, R.layout.product_list_layout);
        this.product=product;
    }





    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
       // Product  product= new Product();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.product_list_layout, parent, false);
            convertView = view;
        }

         imageViewP = convertView.findViewById(R.id.product_image);
         nameP = convertView.findViewById(R.id.product_name);
         rateP = convertView.findViewById(R.id.pRate);


        //  Picasso.get().load(R.drawable.X).into(imageView)



        return convertView;

    }
}
