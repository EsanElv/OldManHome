package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.mapper.FavoriteMapper;
import com.example.oldpeoplehome.mapper.NewsMapper;
import com.example.oldpeoplehome.mapper.VisitorMapper;
import com.example.oldpeoplehome.pojo.Favorite;
import com.example.oldpeoplehome.pojo.News;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.pojo.Visitor;
import com.example.oldpeoplehome.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public Result add(Favorite favorite) {
        Visitor visitor = visitorMapper.find(favorite.getVisitorId(), null);
        if(visitor == null){
            return Result.error("不存在该用户！");
        }
        News news = newsMapper.find(favorite.getNewsId());
        if(news == null){
            return Result.error("不存在这条新闻！");
        }
        favoriteMapper.add(favorite);
        return Result.success();
    }
}
