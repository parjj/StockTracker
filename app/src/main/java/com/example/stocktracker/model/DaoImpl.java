package com.example.stocktracker.model;

import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements DaoInterface {

    private static DaoImpl dao = null;

    private List<Company> companyList;
    private List<Product> productList,products;
    Company companyInfo;
    Product productInfo;
    Double rate=0.0;


    // static method to create instance of Singleton class
    public static DaoImpl getInstance() {
        if (dao == null)
            dao = new DaoImpl();

        return dao;
    }


    public DaoImpl() {

        companyList = new ArrayList<>();

        //wwe company
        productList = new ArrayList<>();
        productList.add(new Product("WWE Shirt", "https://kenaishop.com/products/wwe-logo-graphic-hoodie?gclid=EAIaIQobChMI6KiTs5_74QIVhoSzCh0F9gb1EAkYESABEgLtlPD_BwE", "https://cdn.shopify.com/s/files/1/0125/6823/2000/products/DynamicImageHandler_6e0bc2b2-cdaa-4b29-99ef-d15cc7c0222d_800x.png?v=1545208093"));
        productList.add(new Product("WWE Belt", "https://shop.wwe.com/wwe-championship-title-throw-pillow/M10152.html?&country=US&currency=USD&utm_source=google&utm_medium=cpc&utm_term=&utm_campaign=&mkwid=&pcrid=287166836576&pkw=&placement=&pmt=&intent=&pgrid=60532495840&ptaid=pla-293946777986&gclid=EAIaIQobChMI6KiTs5_74QIVhoSzCh0F9gb1EAkYCyABEgJOOvD_BwE",
                "https://shop.wwe.com/on/demandware.static/-/Sites-main/default/dw25214cba/images/large/M10152.jpg"));
        productList.add(new Product("WWE Wrist Band", "https://www.partycity.com/wwe-wristbands-6ct-620168.html?gclid=EAIaIQobChMI-crWgJ_74QIVy5yzCh37cQ46EAQYAiABEgKm_PD_BwE&gclsrc=aw.ds&extcmp=pla%7CGoogle",
                "https://partycity6.scene7.com/is/image/PartyCity/_pdp_sq_?$_1000x1000_$&$product=PartyCity/620168"));

        companyList.add(new Company("WWE", "WWE", "https://thumbor.forbes.com/thumbor/960x0/https%3A%2F%2Fblogs-images.forbes.com%2Fblakeoestriecher%2Ffiles%2F2016%2F05%2Fwwe-1200x1200.jpg", productList));

        //apple company
        productList = new ArrayList<>();
        productList.add(new Product("MacBook", "https://www.bhphotovideo.com/c/product/1343177-REG/apple_z0uv_mq423_bh_macbook_air_i7_8gb.html/?ap=y&gclid=EAIaIQobChMIz_G83YzB4QIVTuDICh194Q2uEAQYAiABEgJf0fD_BwE&lsft=BI%3A514&smp=Y", "https://static.bhphoto.com/images/images500x500/apple_z0uv_mq423_bh_macbook_air_i7_8gb_1544477238000_1343177.jpg"));
        productList.add(new Product("Watch", "https://www.apple.com/shop/buy-watch/apple-watch/44mm-cellular-stainless-steel-stainless-steel-indigo-craie-orange-single-tour?afid=p238|sfxarZs8s-dc_mtid_1870765e38482_pcrid_246386975229_&cid=aos-us-kwgo-pla-watch--slid----product-MU6X2LL/A", "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/image/AppleInc/aos/published/images/4/4/44/hermes/44-hermes-indigo-craie-orange-singletour-s4-1up?wid=940&hei=1112&fmt=png-alpha&qlt=80&.v=1541713526101"));

        companyList.add(new Company("Apple", "AAPL", "https://cdn4.iconfinder.com/data/icons/socialcones/508/Apple-512.png", productList));

      /*  //google company
        productList = new ArrayList<>();
        productList.add(new Product("Google Home", "https://www.bestbuy.com/site/google-home-smart-speaker-with-google-assistant-white-slate/5578849.p?skuId=5578849", "https://icdn4.digitaltrends.com/image/google-home-security-thumb-2-1500x1000.jpg"));

        companyList.add(new Company("Google", "GOOGL", "https://image.flaticon.com/teams/slug/google.jpg",productList));

        //Tesla company
        productList= new ArrayList<>();
        productList.add(new Product("T-shirt", "https://www.cafepress.com/+,1293115580/?utm_medium=cpc&utm_source=pla-google&utm_campaign=653161166-d-c&utm_content=31173505377-adid-96354654250&utm_term=pla-585005665864-pid-1293115580&gclid=EAIaIQobChMI-Ivji6_B4QIVAUCGCh3hKARaEAYYBCABEgIQD_D_BwE",
                "https://i3.cpcache.com/merchandise/497_550x550_Front_Color-WithCheckerPant.jpg?Size=Small&AttributeValue=NA&region={\"name\":\"FrontCenter\",\"width\":10,\"height\":10,\"alignment\":\"TopCenter\",\"orientation\":0,\"dpi\":100,\"crop_x\":0,\"crop_y\":0,\"crop_h\":1000,\"crop_w\":1000,\"scale\":0,\"template\":{\"id\":88800272,\"params\":{}}}"));

        productList.add(new Product("Mini SuperCharger", "https://www.etsy.com/listing/620777993/mini-tesla-supercharger-tesla?gpla=1&gao=1&&utm_source=google&utm_medium=cpc&utm_campaign=shopping_us_c-electronics_and_accessories-docking_and_stands-docking_stations&utm_custom1=b789475a-597c-4cdb-8798-f04c7dcf561b&utm_content=go_304502075_22746069635_78727298915_pla-106554179795_c__620777993&gclid=EAIaIQobChMI_PSj2dn_4QIVkVqGCh3hAwZoEAkYCSABEgIgwPD_BwE",
                "https://i.etsystatic.com/17897391/r/il/bdb329/1860405860/il_794xN.1860405860_8uty.jpg"));

        companyList.add(new Company("Tesla", "TSLA", "https://userscontent2.emaze.com/images/dc1bd939-2c82-4b65-9a4a-d30754860c2c/e90b0328-8342-4ab9-9661-3f83690e1d51image01.png",productList));

    }*/
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

        companyInfo.setCompany_name(company.getCompany_name());
        companyInfo.setCompany_stockName(company.getCompany_stockName());
        companyInfo.setUrl(company.getUrl());

        return companyInfo;
    }

    @Override
    public List<Product> getProductsForCompany(Company company) {
        this.companyInfo=company;
        products=companyInfo.getProduct();
        return products;
    }


    //Product

    /*@Override
    public List<Product> getAllProducts() {
        return productList;
    }
*/
    @Override
    public Product getProduct(int position) {

        productInfo = products.get(position);
        return productInfo;
    }

    @Override
    public void addNewProduct(Product product) {
        products=companyInfo.getProduct();
        if(products==null){
            products=new ArrayList<>();
        }
        products.add(product);
    }

    @Override
    public void deleteProduct(Product product) {
        products.remove(product);
    }

    @Override
    public Product updateProduct(Product product) {

        productInfo.setProduct_image(product.getProduct_image());
        productInfo.setProduct_name(product.getProduct_name());
        productInfo.setProduct_url(product.getProduct_url());

        return productInfo;
    }
}
