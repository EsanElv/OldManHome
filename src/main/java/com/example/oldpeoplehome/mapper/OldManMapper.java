package com.example.oldpeoplehome.mapper;

import com.example.oldpeoplehome.dto.OldManUpdateDTO;
import com.example.oldpeoplehome.pojo.OldMan;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OldManMapper {



    @Insert("insert into old_man(address,state,guar_name,guar_phone,nur_id,care_total,plan,name,org_id,age,gender,id_number,other,user_id)"+
            "values(#{address},#{state},#{guarName},#{guarPhone},#{nurId},#{careTotal},#{plan},#{name},#{orgId},#{age},#{gender},#{idNumber},#{other},#{userId})")
    void add(OldMan oldMan);


    //返回完整的oldMan对象，传入参数可以是任意一个唯一属性
    @Select({
            "<script>",
            "SELECT * FROM old_man",
            "<where>",
            "<if test='id != null'>",
            "AND id = #{id}",
            "</if>",
            "<if test='guarPhone != null'>",
            "AND guar_phone = #{guarPhone}",
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
    OldMan find(Integer id, String guarPhone, String idNumber, Integer userId);

    @Select({
            "<script>",
            "SELECT * FROM old_man",
            "<where>",
            "<if test='state != null'>",
            "AND state = #{state}",
            "</if>",
            "<if test='nurId != null'>",
            "AND nur_id = #{nurId}",
            "</if>",
            "<if test='careTotal != null'>",
            "AND care_total = #{careTotal}",
            "</if>",
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
    List<OldMan> findList(String state, Integer nurId, Integer careTotal, Integer orgId, Integer age, String gender);


    @Update({
            "<script>",
            "UPDATE old_man",
            "<set>",
            "<if test='name != null'> name = #{name},</if>",
            "<if test='age != null'> age = #{age},</if>",
            "<if test='gender != null'> gender = #{gender},</if>",
            "<if test='orgId != null'> org_id = #{orgId},</if>",
            "<if test='nurId != null'> nur_id = #{nurId},</if>",
            "<if test='idNumber != null'> id_number = #{idNumber},</if>",
            "<if test='state != null'> state = #{state},</if>",
            "<if test='plan != null'> plan = #{plan},</if>",
            "<if test='guarName != null'> guar_name = #{guarName},</if>",
            "<if test='guarPhone != null'> guar_phone = #{guarPhone},</if>",
            "<if test='other != null'> other = #{other},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    void update(OldManUpdateDTO oldManUpdateDTO);
}
