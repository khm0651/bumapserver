package com.example.demo.user;

import com.example.demo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @GetMapping("/users")
    @ResponseBody
    public List<UserDto> getUsers(){
        String query = "select * from user";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        String content = Json.getJsonFrom(result);
        return Json.deserializeAsListOf(content, UserDto.class);
    }

    @GetMapping("/user")
    @ResponseBody
    public UserDto getUser(@RequestParam String email){
        String query = "select * from user where email = " + email;
        Map<String, Object> result = jdbcTemplate.queryForMap(query);
        String content = Json.getJsonFrom(result);
        return Json.deserializeAs(content, UserDto.class);
    }
}
