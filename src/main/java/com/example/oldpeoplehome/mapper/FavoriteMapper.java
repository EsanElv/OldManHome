package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.pojo.Favorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoriteMapper {
    @Insert("INSERT INTO favorite (visitor_id,news_id)" +
            "values(#{visitorId},#{newsId})")
    void add(Favorite favorite);
}
