package com.example.demo.product;

public class ProductDto {
    public String uid;
    public String shop_uid;
    public String product_name;
    public String product_img;
    public String product_cotegory;
    public int product_price;
    public int proudct_like;
    public String product_size;
    public String product_color;
    public String product_link;

    public ProductDto(String uid, String shop_uid, String product_name, String product_img, String product_cotegory, int product_price, int proudct_like, String product_size, String product_color, String product_link) {
        this.uid = uid;
        this.shop_uid = shop_uid;
        this.product_name = product_name;
        this.product_img = product_img;
        this.product_cotegory = product_cotegory;
        this.product_price = product_price;
        this.proudct_like = proudct_like;
        this.product_size = product_size;
        this.product_color = product_color;
        this.product_link = product_link;
    }
}
