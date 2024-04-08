package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.pojo.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VisitorMapper {
    @Select(("select * from visitor where user_id = #{userId}"))
    Visitor findByUserId(Integer userId);

    @Select({
            "<script>",
            "SELECT * FROM visitor",
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
    Visitor find(Integer id, Integer userId);
}
