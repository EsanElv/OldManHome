package com.example.oldpeoplehome.service;


import com.example.oldpeoplehome.dto.NurUpdateDTO;
import com.example.oldpeoplehome.pojo.Nursing;
import com.example.oldpeoplehome.pojo.Result;

import java.util.List;

public interface NursingService {
    //通过唯一的值返回某个特定对象
    //唯一的值有：id,idNumber,userId
    <T> T find(Integer id, String idNumber, Integer userId, T dto);

    //通过不唯一的值返回对象列表
    //不唯一且有可能用于查询的值有：org_id,age,gender
    <T> List<T> findList(Integer orgId, Integer age, String gender, T dto);

    void add(Nursing nursing);

    Result update(NurUpdateDTO nurUpdateDTO);
}
