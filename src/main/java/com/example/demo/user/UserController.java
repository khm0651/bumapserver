package com.example.demo.user;

import com.example.demo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/registerUser")
    @ResponseBody
    public String registerUser(@RequestBody UserDto userDto){
        try{
            String insertQuery = "insert into user (name, email) values(?,?)";
            int result = jdbcTemplate.update(insertQuery,userDto.name,userDto.email);
            if(result > 0){
                return "Success";
            }else{
                return "fail";
            }
        } catch (Exception e){
            return "fail";
        }

    }
}
