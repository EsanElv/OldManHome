package com.example.oldpeoplehome.dto;

import com.example.oldpeoplehome.anno.Gender;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NurUpdateDTO {
    private Integer id;//id
    private String name;//姓名
    private Integer age;//年龄

    private String gender;//性别
    @Pattern(regexp = "^\\d{11}$")
    private String phone;//手机号，用户名
    @Pattern(regexp = "^[a-zA-Z0-9_]{5,16}$")
    private String password;//密码
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,}(:(\\d{1,5}))?(/\\S*)?$")
    private String avatar;//头像
    private Integer orgId;//福利院id
    @Pattern(regexp = "^\\d{17}[0-9X]$")
    private String idNumber;//身份证号
    private String other;//其他
}
