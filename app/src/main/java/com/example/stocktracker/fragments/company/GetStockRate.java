package com.example.stocktracker.fragments.company;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stocktracker.R;
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


public class GetStockRate extends AsyncTask<Void, Void, View> {
    private static final String url_string = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&";
    private static final String TAG = "Stock Tracker";
    Company company;
    View convertView;
    Context context;




    Double rate_value = null;

    public GetStockRate(Company company, View view, Context context) {
        this.company = company;
        this.convertView = view;
        this.context = context.getApplicationContext();
    }


    @Override
    protected View doInBackground(Void... voids) {
        String apikey = context.getString(R.string.api_key_new);    //"MP77SPA5MSCPB839";// "06TZZOMBWPSZNJKE";
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

                rate_value = jsonObject.getJSONObject("Global Quote").getDouble("05. price");
                company.setRate(rate_value);
            }
        }
        catch(IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
        }


    @Override
    protected void onPostExecute(View view) {
        super.onPostExecute(view);

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
