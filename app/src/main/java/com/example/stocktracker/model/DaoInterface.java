package com.example.stocktracker.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import java.util.List;

@Dao
public interface DaoInterface {

    //Company
    List<Company> getAllCompanies();
   // LiveData<List<Person>> fetchAllPersons();

    Company getCompany(int position);

    void addNewCompany(Company company);

    void deleteCompany(Company company);

    Company updateCompany(Company company);

    List<Product> getProductsForCompany(Company  company);


    //Product

    //List<Product> getAllProducts();

    Product getProduct(int position);

    void addNewProduct(Product product);

    void deleteProduct(Product product);

    Product updateProduct(Product product);


}
