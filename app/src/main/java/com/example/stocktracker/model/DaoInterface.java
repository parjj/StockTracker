package com.example.stocktracker.model;

import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import java.util.List;

public interface DaoInterface {

    void addDaoUpdateDelegate(IDaoUpdateDelegate delegate);

    void removeDaoUpdateDelegate(IDaoUpdateDelegate delegate);

    //Company
    void getAllCompanies();

    //Company getCompany(int position);

    void addNewCompany(Company company);

    void deleteCompany(Company company);

    Company updateCompany(Company company);

    void getProductsForCompanyId(Long id);


    //Product


    void addNewProduct(Product product);

    void deleteProduct(Product product);

    Product updateProduct(Product product);


}
