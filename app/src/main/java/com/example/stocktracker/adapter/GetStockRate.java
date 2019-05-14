package com.example.stocktracker.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocktracker.R;
import com.example.stocktracker.model.entity.Company;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetStockRate extends AsyncTask<Void, Void, Company> {

    private static final String url_string = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&";

    private static long API_KEY_INDEX = 0;

    private static String[] API_KEYS = {"MP77SPA5MSCPB839", "06TZZOMBWPSZNJKE", " PK3HSW2RKTGHSHF6"};

    Company company;
    View convertView;
    Context context;

    Double rate_value = null;

    public GetStockRate(Company company, View view, Context context) {
        this.company = company;
        this.convertView = view;
        this.context = context;
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
        ImageView imageView = convertView.findViewById(R.id.icon);
        Picasso.get().load(company.getUrl()).resize(128, 128).into(imageView);

        TextView text_name = convertView.findViewById(R.id.company_name);
        TextView text_code = convertView.findViewById(R.id.company_code);

        text_name.setText(company.getCompany_name());
        text_code.setText("(" + company.getCompany_stockName() + ")");
        text_name.setText(company.getCompany_name());
    }

    @Override
    protected void onPostExecute(Company company) {

        TextView text_rate = convertView.findViewById(R.id.rate);
        double d = company.getRate();
        text_rate.setText("Rate: $" + d);
    }
}
