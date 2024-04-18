package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.mapper.BrowseMapper;
import com.example.oldpeoplehome.mapper.NewsMapper;
import com.example.oldpeoplehome.mapper.VisitorMapper;
import com.example.oldpeoplehome.pojo.Browse;
import com.example.oldpeoplehome.pojo.News;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.pojo.Visitor;
import com.example.oldpeoplehome.service.BrowseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrowseServiceImpl implements BrowseService {
    @Autowired
    private BrowseMapper browseMapper;
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public Result add(Browse browse) {
        Visitor visitor = visitorMapper.find(browse.getVisitorId(), null);
        if(visitor == null){
            return Result.error("不存在该用户！");
        }
        News news = newsMapper.find(browse.getNewsId());
        if(news == null){
            return Result.error("不存在这条新闻！");
        }
        //find这条收藏，如果已经有了，就写明已经添加进收藏
        browseMapper.add(browse);
        return Result.success();
    }

    @Override
    public <T> List<T> findList(Integer visitorId, T dto) throws InstantiationException, IllegalAccessException {
        Class<?> dtoClass = dto.getClass();
        List<Browse> browseList = browseMapper.findList(visitorId);
        List<T> dtoList = new ArrayList<>();
        for(Browse browse : browseList){
            Integer newsId = browse.getNewsId();
            News news = newsMapper.find(newsId);
            T newDto = (T) dtoClass.newInstance();
            BeanUtils.copyProperties(news,newDto);
            dtoList.add(newDto);
        }

        return dtoList;
    }
}
