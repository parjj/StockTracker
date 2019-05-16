package com.example.stocktracker.model.database;

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
public interface DaoAccess {

    //Company

    @Query("SELECT * FROM Company")
    LiveData<List<Company>> getAllCompanies();

    @Query("SELECT * FROM Company")
    List<Company> getCompanies();


    @Query("SELECT * FROM Company WHERE id IS:position")
    Company getCompany(int position);

    @Insert
    void addNewCompany(Company company);

    @Delete
    void deleteCompany(Company company);

    @Update
    void updateCompany(Company company);

    @Query("SELECT * FROM Product WHERE companyId IS:companyId")
    LiveData<List<Product>> getProductsForCompany(Long  companyId);

    //Products

    @Insert
    void addNewProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Query("DELETE FROM Product WHERE companyId IS:companyId")
    void deleteProductforCompany(Long companyId);

}
