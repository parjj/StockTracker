package com.example.stocktracker.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.fragments.company.GetStockRate;
import com.example.stocktracker.model.entity.Company;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Company company = (Company) getItem(position);


      /*  Handler handler= new Handler();
        Runnable runnable= new Runnable() {
            @Override
            public void run() {


            }
        };*/


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

    private class StockRateValue extends AsyncTask<Void, Void, Void> {

        private static final String url_string = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&";
        Company company;
        View convertView;

        String apikey = getContext().getString(R.string.api_key);

        Double rate_value = null;

        public StockRateValue(Company company, View view) {
            this.company = company;
            this.convertView = view;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            StringBuffer whole_url = new StringBuffer(url_string);
            String company_code = company.getCompany_stockName();
            whole_url.append("symbol=" + company_code);                                                                                   //appending the url
            whole_url.append("&apikey=" + apikey);


            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL(whole_url.toString());
                httpURLConnection = (HttpURLConnection) url.openConnection();

                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    httpURLConnection.setRequestMethod("GET");
                    InputStream inputStream = httpURLConnection.getInputStream();


                    InputStreamReader streamReader =
                            new InputStreamReader(inputStream, "UTF-8");

                    BufferedReader bstreamReader = new BufferedReader(streamReader);
                    StringBuilder responseStrBuilder = new StringBuilder();

                    String inputStr;
                    while ((inputStr = bstreamReader.readLine()) != null)
                        responseStrBuilder.append(inputStr);

                    JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

                    rate_value = jsonObject.getJSONObject("Global Quote").getDouble("05. price");
                    company.setRate(rate_value);

                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ImageView imageView = convertView.findViewById(R.id.icon);
            Picasso.get().load(company.getUrl()).resize(128, 128).into(imageView);


            TextView text_name = convertView.findViewById(R.id.company_name);
            TextView text_code = convertView.findViewById(R.id.company_code);
            TextView text_rate = convertView.findViewById(R.id.rate);

            text_name.setText(company.getCompany_name());
            text_code.setText("(" + company.getCompany_stockName() + ")");
            double d = company.getRate();
            text_rate.setText("Rate: $" + Double.toString(d));

        }


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
