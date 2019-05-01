package com.example.stocktracker.model;

import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements DaoInterface {

    private static DaoImpl dao = null;

    private List<Company> companyList;
    private List<Product> productList;
    Company companyInfo;
    Product productInfo;


    // static method to create instance of Singleton class
    public static DaoImpl getInstance() {
        if (dao == null)
            dao = new DaoImpl();

        return dao;
    }


    public DaoImpl() {
        companyList = new ArrayList<>();

        //Company(String company_name, String company_stockName,String url)

        companyList.add(new Company("Apple", "APL", "https://cdn4.iconfinder.com/data/icons/socialcones/508/Apple-512.png"));
        companyList.add(new Company("Google", "GLE", "https://image.flaticon.com/teams/slug/google.jpg"));
        companyList.add(new Company("Tesla", "TSL", "https://userscontent2.emaze.com/images/dc1bd939-2c82-4b65-9a4a-d30754860c2c/e90b0328-8342-4ab9-9661-3f83690e1d51image01.png"));
        companyList.add(new Company("WWE", "WF", "https://thumbor.forbes.com/thumbor/960x0/https%3A%2F%2Fblogs-images.forbes.com%2Fblakeoestriecher%2Ffiles%2F2016%2F05%2Fwwe-1200x1200.jpg"));

        productList = new ArrayList<>();
        productList.add(new Product("WWE Shirt","https://kenaishop.com/products/wwe-logo-graphic-hoodie?gclid=EAIaIQobChMI6KiTs5_74QIVhoSzCh0F9gb1EAkYESABEgLtlPD_BwE","https://cdn.shopify.com/s/files/1/0125/6823/2000/products/DynamicImageHandler_6e0bc2b2-cdaa-4b29-99ef-d15cc7c0222d_800x.png?v=1545208093"));
        productList.add(new Product("WWE Belt","https://shop.wwe.com/wwe-championship-title-throw-pillow/M10152.html?&country=US&currency=USD&utm_source=google&utm_medium=cpc&utm_term=&utm_campaign=&mkwid=&pcrid=287166836576&pkw=&placement=&pmt=&intent=&pgrid=60532495840&ptaid=pla-293946777986&gclid=EAIaIQobChMI6KiTs5_74QIVhoSzCh0F9gb1EAkYCyABEgJOOvD_BwE",
                "https://shop.wwe.com/on/demandware.static/-/Sites-main/default/dw25214cba/images/large/M10152.jpg"));
        productList.add(new Product("WWE Wrist Band","https://www.partycity.com/wwe-wristbands-6ct-620168.html?gclid=EAIaIQobChMI-crWgJ_74QIVy5yzCh37cQ46EAQYAiABEgKm_PD_BwE&gclsrc=aw.ds&extcmp=pla%7CGoogle",
                "https://partycity6.scene7.com/is/image/PartyCity/_pdp_sq_?$_1000x1000_$&$product=PartyCity/620168"));
    }

    //Company

    @Override
    public List<Company> getAllCompanies() {
        return companyList;
    }

    @Override
    public Company getCompany(int position) {
        companyInfo = companyList.get(position);
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
        productInfo = productList.get(position);
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
