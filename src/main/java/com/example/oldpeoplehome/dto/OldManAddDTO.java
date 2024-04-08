package com.example.oldpeoplehome.dto;

import com.example.oldpeoplehome.anno.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OldManAddDTO {
    private String name;//姓名
    private Integer age;//年龄
    @Gender
    private String gender;//性别
    @JsonProperty("phone")
    @Pattern(regexp = "^\\d{11}$")
    private String username;//手机号，也是登陆用户名
    @Pattern(regexp = "^[a-zA-Z0-9_]{5,16}$")
    private String password;//密码
    @JsonProperty("avatar")
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,}(:(\\d{1,5}))?(/\\S*)?$")
    private String userPic;//头像
    private Integer orgId;//福利院id
    private Integer nurId;//护工id
    @Pattern(regexp = "^\\d{17}[0-9X]$")
    private String idNumber;//身份证号
    private String state;//健康信息
    private String plan;//关怀计划
    private String guarName;//监护人姓名
    @Pattern(regexp = "^\\d{11}$")
    private String guarPhone;//监护人电话
    private String other;//其他信息

}
