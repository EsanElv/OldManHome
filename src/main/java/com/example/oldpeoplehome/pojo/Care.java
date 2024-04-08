package com.example.oldpeoplehome.pojo;

import lombok.Data;

@Data
public class Care {
    private Integer id;//某次关怀的id
    private String content;//内容
    private Integer manId;//老人id
    private Integer nurId;//护工id
    private String time;//时间

}
