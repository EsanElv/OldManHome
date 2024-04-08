package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(username,password,user_pic,type)"+
            "values(#{username},#{password},#{userPic},#{type})")
    @Options(useGeneratedKeys = true, keyProperty = "userId",keyColumn = "user_id")
    int add(User user);



    @Select({
            "<script>",
            "SELECT * FROM user",
            "<where>",
            "<if test='userId != null'>",
            "AND user_id = #{userId}",
            "</if>",
            "<if test='username != null'>",
            "AND username = #{username}",
            "</if>",
            "</where>",
            "</script>"
    })
    User find(Integer userId, String username);


    @Update({
            "<script>",
            "UPDATE user",
            "<set>",
            "<if test='username != null'> username = #{username},</if>",
            "<if test='password != null'> password = #{password},</if>",
            "<if test='userPic != null'> user_pic = #{userPic},</if>",
            "</set>",
            "WHERE user_id = #{userId}",
            "</script>"
    })
    void update(User user);
}
