package com.example.demo.product;

public class ProductDto {
    public String uid;
    public String shop_uid;
    public String product_name;
    public String product_cotegory;
    public int product_price;
    public int like;
    public String size;
    public String color;
    public String link;

    public ProductDto(String uid, String shop_uid, String product_name, String product_cotegory, int product_price, int like, String size, String color, String link) {
        this.uid = uid;
        this.shop_uid = shop_uid;
        this.product_name = product_name;
        this.product_cotegory = product_cotegory;
        this.product_price = product_price;
        this.like = like;
        this.size = size;
        this.color = color;
        this.link = link;
    }
}
