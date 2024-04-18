package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.mapper.NewsMapper;
import com.example.oldpeoplehome.pojo.News;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.service.NewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public void addNews(News news) {
        newsMapper.add(news);
    }

    @Override
    public <T>List<T> list(Integer manId, T dto) {
        Class<?> dtoClass = dto.getClass();

        List<News> newsList = newsMapper.findList(manId);

        List<T> dtoList = new ArrayList<>();
        for(News news : newsList){
            try{
                T newDto = (T) dtoClass.newInstance();
                BeanUtils.copyProperties(news,newDto);
                dtoList.add(newDto);
            } catch(InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return dtoList;
    }

    @Override
    public <T> T info(Integer id, T dto) {
        News news = newsMapper.find(id);
        BeanUtils.copyProperties(news,dto);
        return dto;
    }
}
