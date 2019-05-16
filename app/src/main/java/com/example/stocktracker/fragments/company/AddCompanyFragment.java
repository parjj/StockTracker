package com.example.stocktracker.fragments.company;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.stocktracker.R;
import com.example.stocktracker.model.DaoInterface;
import com.example.stocktracker.model.database.DaoRoomImpl;
import com.example.stocktracker.model.database.LocalDatabase;
import com.example.stocktracker.model.entity.Company;

public class AddCompanyFragment extends Fragment {

    EditText cName, cCode, cImageUrl;
    Button saveC, cancelC;
    Company company;

    LocalDatabase localDatabase;
    DaoInterface daoInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localDatabase = LocalDatabase.getDb(getContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_company_layout, container, false);

        cName = view.findViewById(R.id.adCName);
        cCode = view.findViewById(R.id.adCCode);
        cImageUrl = view.findViewById(R.id.adCImage);
        saveC = view.findViewById(R.id.bCsave);
        cancelC = view.findViewById(R.id.bCCancel);

        daoInterface= DaoRoomImpl.getInstance(getContext());


        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final FragmentManager manager = getFragmentManager();

        saveC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = cName.getText().toString();
                String code = cCode.getText().toString();
                String url = cImageUrl.getText().toString();

                company = new Company(name,code,url);

                new InsertTask(company).execute();



                //i am adding a dao and again i am calling the companylistfrag for adding it to the companyNames
                //is this really necessary , can we just have one line of addNewCompany and that reflect the changes in the listView without
                //defining the companylistfrag in this class.
                //CompanyListFragment companyListFragment = (CompanyListFragment) getFragmentManager().getFragments().get(0);
                //companyListFragment.companyNames.add(company);

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

    //Async Insert task class
    public class InsertTask extends AsyncTask<Void, Void, Void> {

        private final Company in_company;

        public InsertTask(Company company) {
            this.in_company = company;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            daoInterface.addNewCompany(in_company);
            return null;
        }
    }
}