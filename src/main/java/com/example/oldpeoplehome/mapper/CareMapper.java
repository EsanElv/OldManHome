package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.dto.CareAddDTO;
import com.example.oldpeoplehome.pojo.Care;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CareMapper {
    @Select({
            "<script>",
            "SELECT * FROM care",
            "<where>",
            "<if test='manId != null'>",
            "AND man_id = #{manId}",
            "</if>",
            "<if test='nurId != null'>",
            "AND nur_id = #{nurId}",
            "</if>",
            "</where>",
            "</script>"
    })
    List<Care> findList(Integer manId, Integer nurId);

    @Insert("INSERT INTO care (man_id,nur_id,content,time)"+
            "values(#{manId},#{nurId},#{content},NOW())")
    void add(CareAddDTO careAddDTO);
}
