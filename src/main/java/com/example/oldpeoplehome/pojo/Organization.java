package com.example.oldpeoplehome.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Organization {
    @JsonProperty("orgId")
    private Integer id;//福利院id
    @JsonProperty("orgName")
    private String name;//福利院名字
}