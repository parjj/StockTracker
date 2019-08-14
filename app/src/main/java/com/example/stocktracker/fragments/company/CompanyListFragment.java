package com.example.stocktracker.fragments.company;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.stocktracker.R;
import com.example.stocktracker.adapter.CompanyRecyclerAdapter;
import com.example.stocktracker.fragments.product.ProductFragment;
import com.example.stocktracker.helper.ItemTouchHelperAdapter;
import com.example.stocktracker.helper.OnstartDragListener;
import com.example.stocktracker.helper.SimpleItemTouchHelperCallback;
import com.example.stocktracker.model.DaoImpl;
import com.example.stocktracker.model.DaoInterface;
import com.example.stocktracker.model.database.DaoRoomImpl;
import com.example.stocktracker.model.database.LocalDatabase;
import com.example.stocktracker.model.IDaoUpdateDelegate;
import com.example.stocktracker.model.entity.Company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CompanyListFragment extends Fragment implements IDaoUpdateDelegate, OnstartDragListener {

    // extending list fragment only has or accepts listView nothing else can be defined.also no need of an xml to inflate .
    RecyclerView recyclerView;
    CompanyRecyclerAdapter companyRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    CompanyRecyclerAdapter.CompanyViewHolder companyViewHolder;
    public List<Company> companyNames = new ArrayList<>();

    Button toolbar_button;
    public static final String DATABASE_NAME = "company_database";

    Context context;
    DaoInterface daoInterface;

    private ItemTouchHelper mItemTouchHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        if (!isNetworkAvailable()) {
            Toast.makeText(getContext().getApplicationContext(), "No stock rates, will get your rates once network is back", Toast.LENGTH_LONG).show();
        }
        checkForDatabase();
    }

    private void checkForDatabase() {

        File dbFilePath = context.getDatabasePath(DATABASE_NAME);
        if (!dbFilePath.exists()) {
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
        } else {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.company_list, container, false);

        //recyclerView definitions
        recyclerView = view.findViewById(R.id.recyclerView);
        // recyclerView.setE
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);


        reload();

        //drag and swipe recycler view
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(companyRecyclerAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);


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

        //Edit functionality of the whole company list
        toolbarListener();
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
        recyclerView.setBackgroundResource(R.color.lightPink);
        companyRecyclerAdapter.visible = View.VISIBLE;

        reload();
        editCompanyFragmentCall();
    }

    //from done to edit tool bar button change
    public void doneToEditCall() {

        // done button
        toolbar_button.setText("Edit");
        toolbar_button.setClickable(true);
        recyclerView.setBackgroundResource(R.color.white);
        companyRecyclerAdapter.visible = View.GONE;
        // listViewListner();
        toolbarListener();
        reload();
    }

    //edit functionality
    public void editCompanyFragmentCall() {

            companyRecyclerAdapter.setValue=0;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        daoInterface = DaoRoomImpl.getInstance(context);
        daoInterface.addDaoUpdateDelegate(this);
        daoInterface.getAllCompanies();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        daoInterface.removeDaoUpdateDelegate(this);
        super.onStop();
    }

    public void reload() {
        if (companyRecyclerAdapter == null) {
            companyRecyclerAdapter = new CompanyRecyclerAdapter(context, companyNames, this);
            recyclerView.setAdapter(companyRecyclerAdapter);
        } else {

            companyRecyclerAdapter.notifyDataSetChanged();
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

    @Override
    public void onUpdateResult(int resultCode, Object result) {

        if (resultCode == 1) {
            companyNames.clear();
            companyNames.addAll((List<Company>) result);
            reload();
            // this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

        Log.d("onStartDrag", "onStartDrag");
        mItemTouchHelper.startDrag(viewHolder);
    }
}



