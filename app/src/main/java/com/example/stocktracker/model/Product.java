package com.example.stocktracker.model;

import java.io.Serializable;

public class Product implements Serializable {


    private String product_name;
    private String product_url;
    private String product_image;

    public Product(String product_name, String product_url, String product_image) {
        this.product_name = product_name;
        this.product_url = product_url;
        this.product_image = product_image;
    }



    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
