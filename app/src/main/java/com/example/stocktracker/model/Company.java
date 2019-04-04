package com.example.stocktracker.model;

import java.io.Serializable;
import java.util.List;

public class Company implements Serializable {


    private String company_name;
    private double rate;
    private Integer imgValue;
    private String url;
    private String company_stockName;
    private List<Product> product;


    public Company(String company_name, double rate,Integer imgValue) {
        this.company_name = company_name;
        this.rate = rate;
        this.imgValue=imgValue;
    }

    public Company(String company_name, String company_stockName,Integer imgValue) {
        this.company_name = company_name;
        this.company_stockName = company_stockName;
        this.imgValue=imgValue;
    }

    public Company(String company_name, String company_stockName,String url) {
        this.company_name = company_name;
        this.company_stockName = company_stockName;
        this.url=url;
    }

    public Company(String company_name, double rate, Integer imgValue, String url, String company_stockName, List<Product> product) {
        this.company_name = company_name;
        this.rate = rate;
        this.imgValue = imgValue;
        this.url = url;
        this.company_stockName = company_stockName;
        this.product = product;
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

    public Integer getImgValue() {
        return imgValue;
    }

    public void setImgValue(Integer imgValue) {
        this.imgValue = imgValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Company() {
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
