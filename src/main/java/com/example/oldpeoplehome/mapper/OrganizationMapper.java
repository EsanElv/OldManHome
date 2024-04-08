package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.pojo.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrganizationMapper {
    @Select({
            "<script>",
            "SELECT * FROM organization",
            "<where>",
            "<if test='id != null'>",
            "AND id = #{id}",
            "</if>",
            "<if test='name != null'>",
            "AND name = #{name}",
            "</if>",
            "</where>",
            "</script>"
    })
    Organization find(Integer id, String name);

    @Select("select * from organization")
    List<Organization> findList();
}
