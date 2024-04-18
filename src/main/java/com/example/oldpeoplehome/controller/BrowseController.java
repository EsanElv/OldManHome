package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.dto.BrowseListDTO;
import com.example.oldpeoplehome.pojo.Browse;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.service.BrowseService;
import com.example.oldpeoplehome.service.NewsService;
import com.example.oldpeoplehome.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/browse")
@Validated
public class BrowseController {
    @Autowired
    private BrowseService browseService;

    @PostMapping("/add")
    public Result addBrowse(Browse browse){
        return browseService.add(browse);
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getBrowseList() throws InstantiationException, IllegalAccessException {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer type = (Integer) claims.get("type");
        if(type != 1){
            return Result.error("Type error!");
        }
        Integer id = (Integer) claims.get("id");

        List<BrowseListDTO> browseListDTOList = browseService.findList(id,new BrowseListDTO());
        Integer total = browseListDTOList.size();
        Map<String,Object> res = new HashMap<>();
        res.put("total",total);
        res.put("items",browseListDTOList);
        return Result.success(res);
    }
}
