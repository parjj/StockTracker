package com.example.stocktracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.model.entity.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter {

    private List<Product> productList;

    public ProductListAdapter(Context context, List<Product> productList) {

        super(context, R.layout.product_list_layout,productList);
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = (Product) getItem(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.product_list_layout, parent, false);
            convertView = view;
        }

        ImageView imageViewP = convertView.findViewById(R.id.product_image);
        TextView nameP = convertView.findViewById(R.id.product_name);
        TextView rateP = convertView.findViewById(R.id.product_rate);

        if (product != null) {
            Picasso.get().load(product.getProduct_image()).resize(300, 300).into(imageViewP);
            nameP.setText(product.getProduct_name());
            rateP.setText("$--");
        }
        return convertView;
    }
}
