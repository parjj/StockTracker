package com.example.stocktracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stocktracker.R;
import com.example.stocktracker.fragments.company.EditCompanyFragment;
import com.example.stocktracker.fragments.product.ProductFragment;
import com.example.stocktracker.helper.ItemTouchHelperAdapter;
import com.example.stocktracker.helper.ItemTouchHelperViewHolder;
import com.example.stocktracker.helper.OnstartDragListener;
import com.example.stocktracker.model.entity.Company;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class CompanyRecyclerAdapter extends RecyclerView.Adapter<CompanyRecyclerAdapter.CompanyViewHolder> implements ItemTouchHelperAdapter {

    public static final String COMPANY_FRAGMENT = "company_fragment";
    private List<Company> companyList;

    public int visible = View.GONE;
    public int setValue = 1;
    Context context;
    private final OnstartDragListener mDragStartListener;


    public CompanyRecyclerAdapter(Context context, List<Company> companyList,OnstartDragListener mDragStartListener) {
        this.companyList = companyList;
        this.context = context;

        this.mDragStartListener = mDragStartListener;
    }


    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_listitem_layout, viewGroup, false);

        CompanyViewHolder viewHolder = new CompanyViewHolder(view); // pass the view to View Holder

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CompanyViewHolder companyViewHolder, int i) {

        final int position = i;

        final Company company = (Company) companyList.get(position);

        companyViewHolder.text_name.setTag(company.getCompany_name());

        new GetStockRate(company, companyViewHolder, context).execute();
        Picasso.get().load(R.drawable.icons8_minus_48).resize(70, 70).into(companyViewHolder.delImageView);

        companyViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("selected_position", position);
                bundle.putSerializable("company_values", company);

                FragmentActivity activity= (FragmentActivity) context;
                FragmentManager fm = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
              //  company_main = (Company) .getItemAtPosition(position);

                if(setValue==1) {

                    ProductFragment productFragment = new ProductFragment();
                    fragmentTransaction.add(R.id.fragment_container, productFragment, "prod_list");
                    productFragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(COMPANY_FRAGMENT);
                }else{

                    EditCompanyFragment editCompanyFragment = new EditCompanyFragment();
                    fragmentTransaction.add(R.id.fragment_container, editCompanyFragment);
                    editCompanyFragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack("company_list_edit");  // when you click delete from edit company and this line isn't defined the page doesn't come  back
                    setValue=1;
                }

                fragmentTransaction.commit();
            }
        });

        companyViewHolder.relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(companyViewHolder);
                }
                return false;
            }
        });


    }



    @Override
    public int getItemCount() {
        return companyList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(companyList, fromPosition, toPosition);
//        if (fromPosition < toPosition) {
//            for (int i = fromPosition; i < toPosition; i++) {
//                Collections.swap(companyList, i, i + 1);
//            }
//        } else {
//            for (int i = fromPosition; i > toPosition; i--) {
//                Collections.swap(companyList, i, i - 1);
//            }
//        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        companyList.remove(position);
        notifyItemRemoved(position);
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        TextView text_name;
        TextView text_code;
        ImageView imageView;
        TextView text_rate;
        ImageView delImageView;
        RelativeLayout relativeLayout;

        public CompanyViewHolder(View view) {

            super(view);

            delImageView = view.findViewById(R.id.delete_icon);
            delImageView.setVisibility(visible);
            text_name = view.findViewById(R.id.company_name);
            text_code = view.findViewById(R.id.company_code);
            imageView = view.findViewById(R.id.icon);
            text_rate = view.findViewById(R.id.rate);
            relativeLayout = view.findViewById(R.id.parentLayout);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

    }



}
