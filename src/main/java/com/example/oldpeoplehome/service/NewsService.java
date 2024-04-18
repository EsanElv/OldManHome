package com.example.oldpeoplehome.service;

import com.example.oldpeoplehome.pojo.News;
import com.example.oldpeoplehome.pojo.Result;

import java.util.List;

public interface NewsService {
    void addNews(News news);

    <T> List<T> list(Integer manId, T dto);

    <T> T info(Integer id, T dto);
}
