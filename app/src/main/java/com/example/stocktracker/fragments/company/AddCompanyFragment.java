package com.example.stocktracker.fragments.company;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.stocktracker.R;
import com.example.stocktracker.model.DaoImpl;
import com.example.stocktracker.model.entity.Company;

public class AddCompanyFragment extends Fragment {

    EditText cName, cCode, cImageUrl;
    Button saveC, cancelC;
    Company company = new Company();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_company_layout, container, false);

        cName = view.findViewById(R.id.adCName);
        cCode = view.findViewById(R.id.adCCode);
        cImageUrl = view.findViewById(R.id.adCImage);
        saveC = view.findViewById(R.id.bCsave);
        cancelC = view.findViewById(R.id.bCCancel);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final FragmentManager manager = getFragmentManager();

        saveC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                company.setCompany_name(cName.getText().toString());
                company.setCompany_stockName(cCode.getText().toString());
                company.setUrl(cImageUrl.getText().toString());
                DaoImpl.getInstance().addNewCompany(company);

                //i am adding a dao and again i am calling the companylistfrag for adding it to the companyNames
                //is this really necessary , can we just have one line of addNewCompany and that reflect the changes in the listView without
                //defining the companylistfrag in this class.
                CompanyListFragment companyListFragment = (CompanyListFragment) getFragmentManager().getFragments().get(0);
                //companyListFragment.companyNames.add(company);
                companyListFragment.reload();
                manager.popBackStack();
            }

        });

        cancelC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.popBackStack();

            }
        });
    }


}
