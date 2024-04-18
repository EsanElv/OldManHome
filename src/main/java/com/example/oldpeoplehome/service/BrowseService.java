package com.example.oldpeoplehome.service;

import com.example.oldpeoplehome.dto.BrowseListDTO;
import com.example.oldpeoplehome.pojo.Browse;
import com.example.oldpeoplehome.pojo.Result;

import java.util.List;

public interface BrowseService {
    Result add(Browse browse);

    <T> List<T> findList(Integer visitorId, T dto) throws InstantiationException, IllegalAccessException;
}
