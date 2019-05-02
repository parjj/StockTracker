package com.example.stocktracker.fragments.company;

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
import com.example.stocktracker.model.DaoImpl;
import com.example.stocktracker.model.entity.Company;

public class EditCompanyFragment extends Fragment {

    EditText edit_cName, edit_cCode, edit_cImageUrl;
    Button delete, toolbar_button, toolbar_buttonEnd;
    Company company;
    Bundle bundle;

    CompanyListFragment companyListFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         companyListFragment = (CompanyListFragment) getFragmentManager().getFragments().get(0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_company_layout, container, false);

        bundle=getArguments();
        //company= (Company) bundle.getSerializable("company_editable");

        company=DaoImpl.getInstance().getCompany(bundle.getInt("position"));

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


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


           //delete button
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //delete
                    DaoImpl.getInstance().deleteCompany(company);
                    companyListFragment.companyNames.remove(company);
                    companyListFragment.reload();
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

                    String name=edit_cName.getText().toString();
                    String code= edit_cCode.getText().toString();
                    String url= edit_cImageUrl.getText().toString();

                    company= new Company(name, code, url);

                    //update
                    DaoImpl.getInstance().updateCompany(company);
                    companyListFragment.companyNames.set(bundle.getInt("position"),company);
                    companyListFragment.reload();
                    getFragmentManager().popBackStack();
                }
            });
        }
    }


