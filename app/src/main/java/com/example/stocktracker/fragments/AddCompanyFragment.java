package com.example.stocktracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stocktracker.R;
import com.example.stocktracker.model.Company;

import java.util.ArrayList;
import java.util.List;

public class AddCompanyFragment extends Fragment {

    EditText cName,cCode,cImageUrl;
    Button saveC,cancelC;
    Bundle bundle=new Bundle();
    Company company=new Company();
    public static final String COMPANY_CLASS = "company class value";
    List<Company> companiNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_company_layout, container, false);
        cName=view.findViewById(R.id.adCName);
        cCode=view.findViewById(R.id.adCCode);
        cImageUrl=view.findViewById(R.id.adCImage);
        saveC=view.findViewById(R.id.bCsave);
        cancelC=view.findViewById(R.id.bCCancel);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_back,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        saveC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                company.setCompany_name(cName.getText().toString());
                company.setCompany_stockName(cCode.getText().toString());
                company.setUrl("http://1.bp.blogspot.com/-RRnuo67oT2c/UgF29or5qwI/AAAAAAAAO_I/zM9Dr5e3QvM/s1600/Twitter+for+Android.png ");

                bundle.putSerializable(COMPANY_CLASS, company);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                CompanyListFragment companyListFragment = new CompanyListFragment();
                fragmentTransaction.replace(R.id.fragment_container, companyListFragment);
                companyListFragment.setArguments(bundle);
                fragmentTransaction.commit();

            }
        });

        cancelC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
