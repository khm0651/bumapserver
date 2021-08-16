package com.example.demo.store;

import com.example.demo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StoreController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/store")
    @ResponseBody
    List<StoreDto> getStore(){
        String query = "SELECT * FROM shop";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        String content = Json.getJsonFrom(result);
        return Json.deserializeAsListOf(content, StoreDto.class);
    }
}
