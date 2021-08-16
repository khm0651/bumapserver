package com.example.demo.store;

public class StoreDto {
    public String uid;
    public String shop_name;
    public String bookmark;
    public String url;

    public StoreDto(String uid, String shop_name, String bookmark, String url) {
        this.uid = uid;
        this.shop_name = shop_name;
        this.bookmark = bookmark;
        this.url = url;
    }
}
