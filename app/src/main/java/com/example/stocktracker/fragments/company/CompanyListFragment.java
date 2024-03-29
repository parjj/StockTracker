package com.example.stocktracker.fragments.company;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.adapter.CompanyListAdapter;
import com.example.stocktracker.fragments.product.ProductFragment;
import com.example.stocktracker.model.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyListFragment extends Fragment {

    // extending list fragment only has or accepts listView nothing else can be defined.also no need of an xml to inflate .
    ListView listView;
    CompanyListAdapter companyListAdapter;
    public static final String COMPANY_VALUE = "company value";
    public static final String COMPANY_FRAGMENT = "company_fragment";
    List<Company> companyNames;
    Button toolbar_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyNames = new ArrayList<Company>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.company_list, container, false);

        //listview definitions
        listView = view.findViewById(android.R.id.list);
        companyListAdapter = new CompanyListAdapter(getContext(), companyNames);
        listView.setAdapter(companyListAdapter);
        listView.setEmptyView(view.findViewById(R.id.company_emptyLayout));

        //Toolbar definitions
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar_button = view.findViewById(R.id.toolbarButton);
        TextView toolbar_textview = view.findViewById(R.id.toolbarText);

        toolbar.setBackgroundColor(getResources().getColor(R.color.yellowishGreen));

        toolbar_button.setText("Edit");
        toolbar_button.setTextColor(getResources().getColor(R.color.white));

        toolbar_textview.setText(R.string.company_list);
        toolbar_textview.setTextColor(getResources().getColor(R.color.white));
        toolbar.inflateMenu(R.menu.menu_add);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                AddCompanyFragment addCompanyFragment = new AddCompanyFragment();
                fragmentTransaction.add(R.id.fragment_container, addCompanyFragment,"addcompany_fragment_tag");
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();

                Company company = (Company) companyListAdapter.getItem(position);
                bundle.putSerializable(COMPANY_VALUE, company);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                ProductFragment productFragment = new ProductFragment();
                fragmentTransaction.add(R.id.fragment_container, productFragment, "prod_list");
                productFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(COMPANY_FRAGMENT);
                fragmentTransaction.commit();
            }
        });


        toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show all the list and on select give your edit options
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                EditCompanyFragment editCompanyFragment = new EditCompanyFragment();
                fragmentTransaction.add(R.id.fragment_container, editCompanyFragment);
                fragmentTransaction.addToBackStack("company_edit");
                fragmentTransaction.commit();

            }
        });
    }

    public void reload() {
        companyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}