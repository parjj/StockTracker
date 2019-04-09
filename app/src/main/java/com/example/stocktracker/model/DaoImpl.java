package com.example.stocktracker.model;

import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements DaoInterface {

    private static DaoImpl dao= null;

    private List<Company> companyList;
    private List<Product> productList;
    Company companyInfo;
    Product productInfo;


    // static method to create instance of Singleton class
    public static DaoImpl getInstance()
    {
        if (dao == null)
            dao = new DaoImpl();

        return dao;
    }


    public DaoImpl() {
        companyList=new ArrayList<>();

        //Company(String company_name, String company_stockName,String url)

        companyList.add(new Company("Apple","APL","https://cdn4.iconfinder.com/data/icons/socialcones/508/Apple-512.png"));
        companyList.add(new Company("Google","GLE","https://image.flaticon.com/teams/slug/google.jpg"));
        companyList.add(new Company("Tesla","TSL","https://userscontent2.emaze.com/images/dc1bd939-2c82-4b65-9a4a-d30754860c2c/e90b0328-8342-4ab9-9661-3f83690e1d51image01.png"));

    }

    //Company

    @Override
    public List<Company> getAllCompanies() {
        return companyList;
    }

    @Override
    public Company getCompany(int position) {
        companyInfo=companyList.get(position);
        return companyInfo;
    }

    @Override
    public void addNewCompany(Company company) {
        companyList.add(company);
    }

    @Override
    public void deleteCompany(Company company) {
        companyList.remove(company);
    }

    @Override
    public Company updateCompany(Company company) {

        company.setCompany_name(company.getCompany_name());
        company.setCompany_stockName(company.getCompany_stockName());
        company.setUrl(company.getUrl());

        return company;
    }

    @Override
    public List<Product> getProductsForCompany(Company company) {
        return productList;
    }



    //Product

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public Product getProduct(int position) {
        productInfo=productList.get(position);
        return productInfo;
    }

    @Override
    public void addNewProduct(Product product) {
        productList.add(product);
    }

    @Override
    public void deleteProduct(Product product) {
            productList.remove(product);
    }

    @Override
    public Product updateProduct(Product product) {

        product.setProduct_image(product.getProduct_image());
        product.setProduct_name(product.getProduct_name());
        product.setProduct_url(product.getProduct_url());

        return product;
    }
}
