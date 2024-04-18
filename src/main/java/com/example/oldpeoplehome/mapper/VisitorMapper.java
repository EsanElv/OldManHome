package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.dto.VisitorUpdateDTO;
import com.example.oldpeoplehome.pojo.Visitor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Insert("insert into visitor(nickname, user_id)" +
            "values(#{nickname}, #{userId})")
    void add(Visitor visitor);

    @Update({
            "<script>",
            "UPDATE visitor",
            "<set>",
            "<if test='nickname != null'> nickname = #{nickname},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    void update(VisitorUpdateDTO visitorUpdateDTO);

}
