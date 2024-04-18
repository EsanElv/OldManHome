package com.example.oldpeoplehome.service;

import com.example.oldpeoplehome.dto.VisitorUpdateDTO;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.pojo.Visitor;

public interface VisitorService {
    <T> T find(Integer id, Integer userId, T dto);

    Result update(VisitorUpdateDTO visitorUpdateDTO);

}
