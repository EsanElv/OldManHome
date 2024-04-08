package com.example.oldpeoplehome.pojo;

import com.example.oldpeoplehome.anno.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OldMan extends Activist {
    private Integer id;//自己的id
    private String address;//家庭住址
    @NotBlank
    private String state;//健康状况
    @NotBlank
    private String guarName;//监护人姓名
    @Pattern(regexp = "^\\d{11}$")
    private String guarPhone;//监护人电话
    @NotNull
    private Integer nurId;//护工id
    private Integer careTotal;//关怀次数
    private String plan;//当前关怀计划
}
