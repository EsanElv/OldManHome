package com.example.oldpeoplehome.pojo;

import com.mysql.cj.exceptions.StreamingNotifiable;
import lombok.Data;
@Data
public abstract class Activist extends User{
    protected String name;//姓名

    protected Integer orgId;//所属福利院id

    protected Integer age;//年龄

    protected String gender;//性别，只能是"Male","Female"

    protected String idNumber;//身份证号

    protected String other;//其他信息
}
