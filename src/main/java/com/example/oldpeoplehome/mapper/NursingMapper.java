package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.dto.NurUpdateDTO;
import com.example.oldpeoplehome.pojo.Nursing;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NursingMapper {


    @Insert("insert into nursing(name,age,gender,org_id,id_number,other,user_id)"+
            "values(#{name},#{age},#{gender},#{orgId},#{idNumber},#{other},#{userId})")
    int add(Nursing nursing);


    @Select({
            "<script>",
            "SELECT * FROM nursing",
            "<where>",
            "<if test='id != null'>",
            "AND id = #{id}",
            "</if>",
            "<if test='idNumber != null'>",
            "AND id_number = #{idNumber}",
            "</if>",
            "<if test='userId != null'>",
            "AND user_id = #{userId}",
            "</if>",
            "</where>",
            "</script>"
    })
    Nursing find(Integer id, String idNumber, Integer userId);

    @Select({
            "<script>",
            "SELECT * FROM nursing",
            "<where>",
            "<if test='orgId != null'>",
            "AND org_id = #{orgId}",
            "</if>",
            "<if test='age != null'>",
            "AND age = #{age}",
            "</if>",
            "<if test='gender != null'>",
            "AND gender = #{gender}",
            "</if>",
            "</where>",
            "</script>"
    })
    List<Nursing> findList(Integer orgId, Integer age, String gender);

    @Update({
            "<script>",
            "UPDATE nursing",
            "<set>",
            "<if test='name != null'> name = #{name},</if>",
            "<if test='age != null'> age = #{age},</if>",
            "<if test='gender != null'> gender = #{gender},</if>",
            "<if test='orgId != null'> org_id = #{orgId},</if>",
            "<if test='idNumber != null'> id_number = #{idNumber},</if>",
            "<if test='other != null'> other = #{other},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    void update(NurUpdateDTO nurUpdateDTO);
}
