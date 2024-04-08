package com.example.oldpeoplehome.service;


import com.example.oldpeoplehome.dto.OldManUpdateDTO;
import com.example.oldpeoplehome.pojo.OldMan;
import com.example.oldpeoplehome.pojo.Result;


import java.util.List;

public interface OldManService {

    //通过唯一的值返回某个特定对象
    //唯一的值有：id,guarPhone,idNumber,userId
    <T> T find(Integer id, String guarPhone, String idNumber, Integer userId, T dto);

    //通过不唯一的值返回对象列表
    //不唯一且有可能用于查询的值有：state,nur_id,care_total,org_id,age,gender
    <T> List<T> findList(String state, Integer nurId, Integer careTotal, Integer orgId, Integer age, String gender, T dto);

    void add(OldMan oldMan);


    Result update(OldManUpdateDTO oldManUpdateDTO);
}
