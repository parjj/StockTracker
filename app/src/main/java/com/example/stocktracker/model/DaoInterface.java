package com.example.stocktracker.model;

import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public interface DaoInterface {


    //Company

    List<Company> getAllCompanies();

    Company getCompany(int position);

    void addNewCompany(Company company);

    void deleteCompany(Company company);

    Company updateCompany(Company company);

    List<Product> getProductsForCompany(Company company);


    //Product

    //List<Product> getAllProducts();

    Product getProduct(int position);

    void addNewProduct(Product product);

    void deleteProduct(Product product);

    Product updateProduct(Product product);


}
