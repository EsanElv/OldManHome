package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.pojo.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("select * from administrator where user_id = #{userId}")
    Administrator findByUserId(Integer userId);

    @Select({
            "<script>",
            "SELECT * FROM administrator",
            "<where>",
            "<if test='id != null'>",
            "AND id = #{id}",
            "</if>",
            "<if test='userId != null'>",
            "AND user_id = #{userId}",
            "</if>",
            "</where>",
            "</script>"
    })
    Administrator find(Integer id, Integer userId);

}
