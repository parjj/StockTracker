package com.example.stocktracker.model.Database;

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
    // LiveData<List<Person>> fetchAllPersons();

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
   // List<Product> getProductsForCompany(Long  companyId);


    //Product

    //@Query(INSERT INTO Product  )
   // addProductForComapny()

    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAllProducts();

    @Insert
    void addNewProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Query("DELETE FROM Product WHERE companyId IS:companyId")
    void deleteProductforCompany(Long companyId);

}
