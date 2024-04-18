package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.pojo.News;
import com.example.oldpeoplehome.pojo.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsMapper {
    @Insert("INSERT INTO news (title, cover, content, man_id, time)" +
            "values(#{title},#{cover},#{content},#{manId},NOW())")
    void add(News news);

    @Select("SELECT * FROM old_people.news " +
            "WHERE " +
            "IF(#{id} IS NOT NULL, man_id = #{id}, 1)")
    List<News> findList(Integer id);

    @Select("SELECT * FROM news WHERE id =#{id}")
    News find(Integer id);
}
