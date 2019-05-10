package com.example.stocktracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.stocktracker.R;
import com.example.stocktracker.model.entity.Company;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyListAdapter extends ArrayAdapter {

    private List<Company> companyList;

    public int visible = View.GONE;
    Context context;

    public CompanyListAdapter(Context context, List<Company> companyList) {

        super(context, R.layout.company_listitem_layout, companyList);
        this.companyList = companyList;
        this.context = context.getApplicationContext();

    }

    public CompanyListAdapter(Context context, int resource,  List<Company> companyList) {
        super(context, resource);
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

        new GetStockRate(company, convertView, context).execute();
        ImageView delImageView = convertView.findViewById(R.id.delete_icon);
        delImageView.setVisibility(visible);
        Picasso.get().load(R.drawable.icons8_minus_48).resize(70, 70).into(delImageView);

        return convertView;

    }

}


// defining a global views makes the concept that needs to be understood here. each company sets for the global view
// so need to define imageview in getview ()

// when setting up a value sometimes think of defining a common value eg, visible


//         JsonReader jsonReader = new JsonReader(streamReader);

//to extract the key value
//        jsonReader.beginArray();
//      while (jsonReader.hasNext()) {

//         jsonReader.beginObject();
//       while (jsonReader.hasNext()) {
//          String key = jsonReader.nextName();

//         if (key.contains("price")) {


//               break;
//           } else {
//              jsonReader.skipValue();
//          }
//      jsonReader.endObject();
//       }
//
//   jsonReader.close();
//    }

// use handler for live data update
/*
handler1.postDelayed(this, 1000);
        }
        };
        handler1.post(runnable1);*/
