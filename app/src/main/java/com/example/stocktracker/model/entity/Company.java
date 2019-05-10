package com.example.stocktracker.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class Company implements Serializable {



    @PrimaryKey(autoGenerate = true)

    private Long id;
    private String company_name;

    @Ignore
    private double rate;

    private String url;
    private String company_stockName;

    @Ignore
    private List<Product> product;

    @Ignore
    public Company(String company_name, double rate) {
        this.company_name = company_name;
        this.rate = rate;

    }

    public Company(Long id, String company_name, String url, String company_stockName) {
        this.id = id;
        this.company_name = company_name;
        this.url = url;
        this.company_stockName = company_stockName;
    }
    @Ignore
    public Company(String company_name, String company_stockName) {
        this.company_name = company_name;
        this.company_stockName = company_stockName;
    }
    @Ignore
    public Company(String company_name, String company_stockName,String url) {
        this.company_name = company_name;
        this.company_stockName = company_stockName;
        this.url=url;
    }
    @Ignore
    public Company(String company_name, double rate,  String url, String company_stockName, List<Product> product) {
        this.company_name = company_name;
        this.rate = rate;
        this.url = url;
        this.company_stockName = company_stockName;
        this.product = product;
    }
    @Ignore
    public Company(String company_name, String company_stockName, String url,  List<Product> product) {
        this.company_name = company_name;
        this.rate = rate;
        this.url = url;
        this.company_stockName = company_stockName;
        this.product = product;
    }

    @Ignore
    public Company() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getCompany_stockName() {
        return company_stockName;
    }

    public void setCompany_stockName(String company_stockName) {
        this.company_stockName = company_stockName;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
