package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.pojo.Browse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BrowseMapper {
    @Insert("INSERT INTO browse (visitor_id, news_id)" +
            "values(#{visitorId},#{newsId})")
    void add(Browse browse);

    @Select("SELECT * FROM browse WHERE visitor_id = #{id}")
    List<Browse> findList(Integer id);
}
