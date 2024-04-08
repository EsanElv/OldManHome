package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.pojo.Organization;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/org")
@Validated
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/list")
    public Result list(){
        List<Organization> organizations = organizationService.findList();
        Map<String,Object> map = new HashMap<>();
        map.put("total", organizations.size());
        map.put("orgItems", organizations);

        return Result.success(map);
    }
}