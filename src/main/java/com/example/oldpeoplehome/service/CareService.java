package com.example.oldpeoplehome.service;

import com.example.oldpeoplehome.dto.CareAddDTO;

import java.util.List;

public interface CareService {
    //通过不唯一的值返回对象列表
    //不唯一且有可能用于查询的值有：man_id,nur_id
    <T> List<T> findList(Integer manId, Integer nurId, T dto);

    void add(CareAddDTO careAddDTO);
}
