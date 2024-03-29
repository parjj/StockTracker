package com.example.stocktracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.model.Company;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyListAdapter extends ArrayAdapter {

    private List<Company> companyList;

    public CompanyListAdapter(Context context, List<Company> companyList) {

        super(context, R.layout.company_listitem_layout, companyList);
        this.companyList = companyList;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Company company = (Company) getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.company_listitem_layout, parent, false);
            convertView = view;
        }

        ImageView imageView = convertView.findViewById(R.id.icon);
        TextView text_name = convertView.findViewById(R.id.company_name);
        TextView text_code = convertView.findViewById(R.id.company_code);
        TextView text_rate = convertView.findViewById(R.id.rate);

        if(company !=null) {
        company.setRate(299.00);
        Picasso.get().load(company.getUrl()).resize(128,128).into(imageView);
        text_name.setText(company.getCompany_name());
        text_code.setText("("+company.getCompany_stockName()+")");
        text_rate.setText(String.valueOf("Rate :" + "$" + company.getRate()));
        }

        return convertView;

    }

}

