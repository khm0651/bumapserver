package com.example.demo.product;

import com.example.demo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/product")
    @ResponseBody
    List<ProductDto> getProduct(){
        String query = "select " +
                "p.uid, " +
                "p.shop_uid, " +
                "p.product_name, " +
                "p.product_img, " +
                "p.product_category, " +
                "p.product_price, " +
                "p.product_like, " +
                "p.product_size, " +
                "p.product_color, " +
                "p.product_link, " +
                "s.shop_name " +
                "from product as p join shop as s on p.shop_uid = s.uid";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        String content = Json.getJsonFrom(result);
        return Json.deserializeAsListOf(content, ProductDto.class);
    }


    @PostMapping("/product_like")
    @ResponseBody
    void productLike(@RequestBody ProductLikeDto productLikeDto){

        String updateQuery = "update product set product_like = product_like + 1 where uid = ?";
        jdbcTemplate.update(updateQuery,productLikeDto.productUID);
        String insertQuery = "insert into product_like(user_email, product_uid) values(?,?)";
        jdbcTemplate.update(insertQuery,productLikeDto.email,productLikeDto.productUID);
    }

    @GetMapping("/product/category")
    @ResponseBody
    List<ProductDto> getProduct(@RequestParam("type") String type){
        String query = "select " +
                "p.uid," +
                " p.shop_uid," +
                " p.product_name," +
                " p.product_category," +
                " p.product_price," +
                " p.product_like," +
                " p.product_size," +
                " p.product_color," +
                " p.product_link," +
                " s.shop_name" +
                " from product as p join shop as s on p.shop_uid = s.uid where p.product_category = '"+ type + "'";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        String content = Json.getJsonFrom(result);
        return Json.deserializeAsListOf(content, ProductDto.class);
    }
}
