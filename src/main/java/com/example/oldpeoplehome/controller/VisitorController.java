package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.dto.VisitorShowDTO;
import com.example.oldpeoplehome.dto.VisitorUpdateDTO;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.pojo.User;
import com.example.oldpeoplehome.pojo.Visitor;
import com.example.oldpeoplehome.service.UserService;
import com.example.oldpeoplehome.service.VisitorService;
import com.example.oldpeoplehome.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/visitor")
@Validated
public class VisitorController {
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Result info(@RequestHeader(value = "Authorization")String token){
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer type = (Integer)claims.get("type");
        if(type != 1){
            return Result.error("Type error!");
        }
        Integer id = (Integer) claims.get("id");
        VisitorShowDTO visitorShowDTO = visitorService.find(id,null,new VisitorShowDTO());
        return Result.success(visitorShowDTO);
    }

    @PatchMapping("/update")
    public Result update(@RequestBody VisitorUpdateDTO visitorUpdateDTO,
                         @RequestHeader(value = "Authorization") String token){
        Map<String,Object> claims = JwtUtil.parseToken(token);
        Integer type = (Integer) claims.get("type");
        if(type != 1){
            return Result.error("Type error!");
        }
        Integer id = (Integer) claims.get("id");
        visitorUpdateDTO.setId(id);
        return visitorService.update(visitorUpdateDTO);
    }
}
