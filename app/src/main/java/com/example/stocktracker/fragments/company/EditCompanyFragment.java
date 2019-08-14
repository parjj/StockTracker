package com.example.stocktracker.fragments.company;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.model.DaoInterface;
import com.example.stocktracker.model.database.DaoRoomImpl;
import com.example.stocktracker.model.database.LocalDatabase;
import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import java.util.List;

public class EditCompanyFragment extends Fragment {

    EditText edit_cName, edit_cCode, edit_cImageUrl;
    Button delete, toolbar_button, toolbar_buttonEnd;
    Company company;
    List<Product> products;
    Long company_id;

    Bundle bundle;
    LocalDatabase localDatabase;
    DaoInterface daoInterface;

    CompanyListFragment companyListFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDatabase = LocalDatabase.getDb(getContext().getApplicationContext());
        companyListFragment = (CompanyListFragment) getFragmentManager().getFragments().get(0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_company_layout, container, false);

        bundle = getArguments();
      //  int pos = bundle.getInt("position");
        int pos = bundle.getInt("selected_position");
        company = companyListFragment.companyNames.get(pos);
        company_id = company.getId();

        products = company.getProducts();

        edit_cName = view.findViewById(R.id.editCName);
        edit_cCode = view.findViewById(R.id.editCCode);
        edit_cImageUrl = view.findViewById(R.id.editCImage);
        delete = view.findViewById(R.id.editCdelete);

        edit_cName.setText(company.getCompany_name());
        edit_cCode.setText(company.getCompany_stockName());
        edit_cImageUrl.setText(company.getUrl());

        //Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar_button = view.findViewById(R.id.toolbarButton);
        toolbar_buttonEnd = view.findViewById(R.id.toolbarButtonEnd);
        TextView toolbar_textview = view.findViewById(R.id.toolbarText);

        toolbar.setBackgroundColor(getResources().getColor(R.color.reddishPink));
        toolbar_textview.setText("Edit Product");
        toolbar_buttonEnd.setVisibility(View.VISIBLE);
        toolbar_buttonEnd.setText("Cancel");
        toolbar_button.setText("Save");
        toolbar_button.setTextColor(getResources().getColor(R.color.darkBrown));
        toolbar_textview.setTextSize(20);
        toolbar_textview.setTextColor(getResources().getColor(R.color.darkBrown));

        daoInterface= DaoRoomImpl.getInstance(getContext());

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DeleteCompany(company).execute();
                getFragmentManager().popBackStack();
            }
        });

        //cancel button
        toolbar_buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //save button
        toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edit_cName.getText().toString();
                String code = edit_cCode.getText().toString();
                String url = edit_cImageUrl.getText().toString();

                company.setId(company_id);
                company.setCompany_name(name);
                company.setCompany_stockName(code);
                company.setUrl(url);

                company.setCompany_name(name);
                company.setCompany_stockName(code);
                company.setUrl(url);

                if (products != null) {
                    company.setProducts(products);
                }

                //update
                new UpdateCompany(company).execute();

                getFragmentManager().popBackStack();
            }
        });
    }


    //Async Delete task class
    public class DeleteCompany extends AsyncTask<Company, Void, Void> {

        private final Company del_company;


        public DeleteCompany(Company del_company) {
            this.del_company = del_company;
        }

        @Override
        protected Void doInBackground(Company... companies) {
            daoInterface.deleteCompany(del_company);
            return null;
        }

    }


    //Async Update task class
    public class UpdateCompany extends AsyncTask<Void, Void, Company> {

        private final Company up_company;

        public UpdateCompany(Company company) {
            this.up_company = company;
        }

        @Override
        protected Company doInBackground(Void... people) {

            daoInterface.updateCompany(up_company);
            return up_company;
        }
    }


}


