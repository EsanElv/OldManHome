package com.example.oldpeoplehome.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.oldpeoplehome.pojo.Administrator;
import com.example.oldpeoplehome.pojo.Organization;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.service.AdminService;
import com.example.oldpeoplehome.service.OrganizationService;
import com.example.oldpeoplehome.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private OrganizationService organizationService;

    /*管理员首页，返回管理员的福利院*/
    @GetMapping("/home")
    public Result<Organization> getOrganization(@RequestHeader(value = "Authorization", required = true) String token){
        if(token == null){
            return Result.error("Authorization token missing or invalid.");
        }
        try{
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //获取type和id
            Integer type = (Integer) claims.get("type");
            if(type != 2){
                return Result.error("ID type error!");
            }
            Integer id = (Integer) claims.get("id");

            //administrator表中根据id找到福利院id
            Administrator administrator = adminService.find(id,null,new Administrator());
            //organization表中找到对应福利院
            Organization organization = organizationService.find(administrator.getOrgId(), null);
            return Result.success(organization);
        } catch (Exception e) {
            return Result.error("Failed to parse token: " + e.getMessage());
        }
    }
}
