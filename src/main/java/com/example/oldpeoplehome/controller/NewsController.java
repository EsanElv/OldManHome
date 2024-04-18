package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.dto.NewsInfoDTO;
import com.example.oldpeoplehome.dto.NewsListDTO;
import com.example.oldpeoplehome.pojo.News;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.service.NewsService;
import com.example.oldpeoplehome.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
@Validated
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/add")
    public Result addNews(@RequestBody News news){
        newsService.addNews(news);
        return Result.success();
    }

    @GetMapping("/list")
    public Result newsList(Integer id){
        Map<String,Object> res = new HashMap<>();
        List<NewsListDTO> newsListDTOList = newsService.list(id, new NewsListDTO());
        Integer total = newsListDTOList.size();
        res.put("total",total);
        res.put("items",newsListDTOList);
        return Result.success(res);
    }

    @GetMapping("/info")
    public Result<NewsInfoDTO> getNewsInfo(Integer id){
        NewsInfoDTO newsInfoDTO = newsService.info(id,new NewsInfoDTO());
        return Result.success(newsInfoDTO);
    }
}
