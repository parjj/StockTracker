package com.example.stocktracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.stocktracker.model.Company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CompanyListFragment extends Fragment {

    // extending list fragment only has or accepts listView nothing else can be defined.also no need of an xml to inflate .
    ListView listView;
    CompanyListAdapter companyListAdapter;
    FloatingActionButton actionButton;
    public static final String COMPANY_VALUE = "company value";
    public static final String COMPANY_CLASS = "company class value";
    Bundle bundle;
    Company company;
    List<Company> companiNames = new ArrayList<Company>();
    HashMap<String, List<Company>> map = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.company_list, container, false);

        listView = view.findViewById(android.R.id.list);
        companyListAdapter= new CompanyListAdapter(getContext(),companiNames);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        Button toolbar_button = view.findViewById(R.id.toolbarButton);
        TextView toolbar_textview = view.findViewById(R.id.toolbarText);
        toolbar.setBackgroundColor(getResources().getColor(R.color.yellowishGreen));
        toolbar_button.setText("Edit");
        toolbar_button.setTextColor(getResources().getColor(R.color.white));
        toolbar_textview.setText(R.string.watch_list);
        toolbar_textview.setTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        bundle = getArguments();
        if (bundle != null) {
            company = (Company) bundle.getSerializable(COMPANY_CLASS);

        }
        if(companyListAdapter.getCount()==0){
            Toast.makeText(getContext(),"Empty adapter",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"Not Empty adapter",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        AddCompanyFragment addCompanyFragment = new AddCompanyFragment();
        fragmentTransaction.replace(R.id.fragment_container, addCompanyFragment);
        fragmentTransaction.addToBackStack("company_fragmentlist");
        fragmentTransaction.commit();
        return true;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        if (company != null) {
            companiNames.add(company);

        }


        listView.setAdapter(companyListAdapter);

        listView.setEmptyView(getActivity().findViewById(R.id.company_emptyLayout));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();

                Company company = (Company) companyListAdapter.getItem(position);
                bundle.putSerializable(COMPANY_VALUE, company);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                ProductFragment productFragment = new ProductFragment();
                fragmentTransaction.replace(R.id.fragment_container, productFragment);
                productFragment.setArguments(bundle);
              //  fragmentTransaction.addToBackStack("company_fragment");
                fragmentTransaction.commit();
            }
        });
    }
}