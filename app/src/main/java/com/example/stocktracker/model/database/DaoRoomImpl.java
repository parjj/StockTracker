package com.example.stocktracker.model.database;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.stocktracker.fragments.product.ProductFragment;
import com.example.stocktracker.model.DaoInterface;
import com.example.stocktracker.model.IDaoUpdateDelegate;
import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DaoRoomImpl implements DaoInterface {

    private static DaoRoomImpl daoRoomImpl = null;

    private LocalDatabase db = null;

    private List<IDaoUpdateDelegate> delegates;

    private LifecycleOwner owner;

    private List<Company> allCompanies;
    private List<Product> productList;

    private Long id;


    public static DaoInterface getInstance(Context context) {

        if (daoRoomImpl == null) {

            daoRoomImpl = new DaoRoomImpl();
            daoRoomImpl.db = LocalDatabase.getDb(context.getApplicationContext());
            daoRoomImpl.delegates = new ArrayList<>();
            daoRoomImpl.productList = new ArrayList<>();
            daoRoomImpl.allCompanies = new ArrayList<>();

            //daoRoomImpl.livedataCheck();
        }
        daoRoomImpl.owner = (LifecycleOwner) context;
        return daoRoomImpl;
    }

    private void livedataCheck() {

//        //database
////        db.daoAccess().getAllCompanies().observe(owner, new Observer<List<Company>>() {
////            @Override
////            public void onChanged(List<Company> companies) {
////                // companies value is returned from database
////                allCompanies.clear();
////                allCompanies.addAll(companies);
////                for (IDaoUpdateDelegate delegate : delegates) {
////                    if(delegate instanceof  ProductFragment){
////                        productLivedataCheck();
////                    }
////                    delegate.update();
////                }
////            }
////        });
    }

    @Override
    public void addDaoUpdateDelegate(IDaoUpdateDelegate delegate) {
        delegates.remove(delegate);
        delegates.add(delegate);
    }

    @Override
    public void removeDaoUpdateDelegate(IDaoUpdateDelegate delegate) {
        delegates.remove(delegate);
    }

    @Override
    public void getAllCompanies() {

        db.daoAccess().getAllCompanies().observe(owner, new Observer<List<Company>>() {
            @Override
            public void onChanged(List<Company> companies) {
                // companies value is returned from database
                //allCompanies.clear();
                //allCompanies.addAll(companies);
                for (IDaoUpdateDelegate delegate : delegates) {
                     delegate.onUpdateResult(1, companies);
                }
            }
        });
     }

  /*  @Override
    public Company getCompany(int position) {
        return null;
    }
*/
    @Override
    public void addNewCompany(Company company) {
        db.daoAccess().addNewCompany(company);
    }

    @Override
    public void deleteCompany(Company company) {
        db.daoAccess().deleteCompany(company);

    }

    @Override
    public Company updateCompany(Company company) {

        Company companyInfo = company;
        db.daoAccess().updateCompany(companyInfo);

        return companyInfo;
    }

    @Override
    public void getProductsForCompanyId(Long id) {


        db.daoAccess().getProductsForCompany(id).observe(owner, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                for (IDaoUpdateDelegate delegate : delegates) {
                    delegate.onUpdateResult(2, products);
                }

            }
        });

    }

    @Override
    public void addNewProduct(Product product) {
        db.daoAccess().addNewProduct(product);
    }

    @Override
    public void deleteProduct(Product product) {
        db.daoAccess().deleteProduct(product);
    }

    @Override
    public Product updateProduct(Product product) {
        db.daoAccess().updateProduct(product);

        return product;
    }
}
