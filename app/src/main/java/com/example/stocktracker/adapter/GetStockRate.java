package com.example.stocktracker.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.model.entity.Company;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetStockRate extends AsyncTask<Void, Void, Company> {

    private static final String url_string = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&";

    private static long API_KEY_INDEX = 0;

    private static String[] API_KEYS = {"MP77SPA5MSCPB839", "06TZZOMBWPSZNJKE", " PK3HSW2RKTGHSHF6"};

    Company company;
    CompanyRecyclerAdapter.CompanyViewHolder convertView;
    Context context;

    Double rate_value = null;

    public GetStockRate(Company company, CompanyRecyclerAdapter.CompanyViewHolder viewHolder, Context context) {
        this.company = company;
        this.convertView = viewHolder;
        this.context = context;

        //this.convertView.setTag(this.company.getId());
    }

    @Override
    protected Company doInBackground(Void... voids) {

        String apikey = API_KEYS[(int) (++API_KEY_INDEX % 3)];  // context.getString(R.string.api_key_new);    //"MP77SPA5MSCPB839";// "06TZZOMBWPSZNJKE";
        StringBuffer whole_url = new StringBuffer(url_string);
        String company_code = company.getCompany_stockName();
        whole_url.append("symbol=" + company_code);                                                                          //appending the url
        whole_url.append("&apikey=" + apikey);

        try {

            URL url = new URL(whole_url.toString());
            HttpURLConnection httpURLConnection = null;

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

                //  if (jsonObject.has("Global Quote")) {
                rate_value = jsonObject.getJSONObject("Global Quote").getDouble("05. price");
                company.setRate(rate_value);

                //} else {
                //  company.setRate(-1);
                //}

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return company;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Picasso.get().load(company.getUrl()).resize(128, 128).into(convertView.imageView);

        convertView.text_name.setText(company.getCompany_name());
        convertView.text_code.setText("(" + company.getCompany_stockName() + ")");
        convertView.text_name.setText(company.getCompany_name());
    }

    @Override
    protected void onPostExecute(Company company) {

        if(convertView.text_name.getTag()==company.getCompany_name()){

            double d = company.getRate();
            convertView.text_rate.setText("Rate: $" + d);
        }

     }
}
