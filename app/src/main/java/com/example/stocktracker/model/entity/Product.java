package com.example.stocktracker.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Product implements Serializable {



    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String product_name;
    private String product_url;
    private String product_image;

    // companyId will hold the Company'id
    @ForeignKey(entity = Company.class,
            parentColumns = "id",
            childColumns = "companyId")
    private Long companyId;


    @Ignore
    public Product(String product_name, String product_url, String product_image) {
        this.product_name = product_name;
        this.product_url = product_url;
        this.product_image = product_image;
    }

    public Product(Long id, String product_name, String product_url, String product_image, Long companyId) {
        this.id = id;
        this.product_name = product_name;
        this.product_url = product_url;
        this.product_image = product_image;
        this.companyId = companyId;
    }

    @Ignore
    public Product(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
