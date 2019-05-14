package com.example.stocktracker.fragments.company;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.stocktracker.R;
import com.example.stocktracker.adapter.CompanyListAdapter;
import com.example.stocktracker.fragments.product.ProductFragment;
import com.example.stocktracker.model.DaoImpl;
import com.example.stocktracker.model.Database.LocalDatabase;
import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CompanyListFragment extends Fragment {

    // extending list fragment only has or accepts listView nothing else can be defined.also no need of an xml to inflate .
    ListView listView;
    CompanyListAdapter companyListAdapter;
    public List<Company> companyNames = new ArrayList<>();

    public Company company_main;
    Button toolbar_button;

    public static final String COMPANY_FRAGMENT = "company_fragment";
    public static final String DATABASE_NAME = "company_database";

    DaoImpl dao;
    LocalDatabase localDatabase;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        if(!isNetworkAvailable()){
            Toast.makeText(getContext().getApplicationContext(), "No stock rates, will get your rates once network is back", Toast.LENGTH_LONG).show();
        }


        localDatabase = LocalDatabase.getDb(getContext().getApplicationContext());
        checkForDatabase();
        livedataCheck();   // Room Database live data check

        initrun();

    }

    private void checkForDatabase() {

        File dbFilePath = context.getDatabasePath(DATABASE_NAME);
        if(!dbFilePath.exists()){
        dbFilePath.getParentFile().mkdirs();

        // Try to copy database file
        try {
            final InputStream inputStream = context.getAssets().open("data/" + DATABASE_NAME + ".db");
            final OutputStream output = new FileOutputStream(dbFilePath);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            inputStream.close();
        } catch (IOException e) {
            Log.d("DB Message", "Failed to open file", e);
            e.printStackTrace();
        }
    }else {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.company_list, container, false);

        //listview definitions
        listView = view.findViewById(android.R.id.list);
        listView.setEmptyView(view.findViewById(R.id.company_emptyLayout));

        //Toolbar definitions
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar_button = view.findViewById(R.id.toolbarButton);
        TextView toolbar_textview = view.findViewById(R.id.toolbarText);

        toolbar.setBackgroundColor(getResources().getColor(R.color.yellowishGreen));

        toolbar_button.setText("Edit");
        toolbar_button.setTextColor(getResources().getColor(R.color.white));

        toolbar_textview.setText(R.string.stock_tracker);
        toolbar_textview.setTextColor(getResources().getColor(R.color.white));
        toolbar.inflateMenu(R.menu.menu_add);

        //add new companies  toolbar=menu add
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                AddCompanyFragment addCompanyFragment = new AddCompanyFragment();
                fragmentTransaction.add(R.id.fragment_container, addCompanyFragment, "addcompany_fragment_tag");
                fragmentTransaction.addToBackStack("company_fragmentlist");
                fragmentTransaction.commit();
                return false;
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        // on company click goes to the products page of that chosen comapny
        listViewListner();

        //Edit functionality of the whole company list
        toolbarListener();
    }

    //listview click listener
    public void listViewListner() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putInt("selected_position", position);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                company_main = (Company) listView.getItemAtPosition(position);
                ProductFragment productFragment = new ProductFragment();
                fragmentTransaction.add(R.id.fragment_container, productFragment, "prod_list");
                productFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(COMPANY_FRAGMENT);
                fragmentTransaction.commit();
            }
        });
    }

    //toolbar button click listener
    private void toolbarListener() {
        toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toolbar_button.getText().equals("Edit")) {

                    editToDoneCall();   // done page
                } else if (toolbar_button.getText().equals("Done")) {

                    doneToEditCall();   //edit page :home page
                }
            }
        });
    }

    //from edit to done tool bar button change
    public void editToDoneCall() {

        toolbar_button.setText("Done");
        listView.setBackgroundResource(R.color.lightPink);
        companyListAdapter.visible = View.VISIBLE;
        reload();
        editCompanyFragmentCall();
    }

    //from done to edit tool bar button change
    public void doneToEditCall() {

        // done button
        toolbar_button.setText("Edit");
        toolbar_button.setClickable(true);
        listView.setBackgroundResource(R.color.white);
        companyListAdapter.visible = View.GONE;
        listViewListner();
        toolbarListener();
        reload();
    }

    //edit functionality
    public void editCompanyFragmentCall() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);

                //show all the list and on select give your edit options
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                EditCompanyFragment editCompanyFragment = new EditCompanyFragment();
                fragmentTransaction.add(R.id.fragment_container, editCompanyFragment);
                editCompanyFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack("company_list_edit");  // when you click delete from edit company and this line isn't defined the page doesn't come  back
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // room database live data check
    public void livedataCheck() {

        //database
        localDatabase.daoAccess().getAllCompanies().observe(CompanyListFragment.this, new Observer<List<Company>>() {
            @Override
            public void onChanged(List<Company> companies) {
                // companies value is returned from database
                companyNames.clear();
                companyNames.addAll(companies);

                reload();
            }
        });
    }

    public void reload() {
        if (companyListAdapter == null) {
            companyListAdapter = new CompanyListAdapter(getContext(), companyNames);
            listView.setAdapter(companyListAdapter);
        } else {
            companyListAdapter.notifyDataSetChanged();

        }

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
            return true;
        } else {
            return false;

        }
    }

    public void initrun(){
        final Handler handler= new Handler();
        final int timer= 60000;
        final long[] i = {0};

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                companyListAdapter.notifyDataSetChanged();
                Log.d(" n times", String.valueOf(i[0]++));
                handler.postDelayed(this,timer);
            }
        },timer);
    }
}



