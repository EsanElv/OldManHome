package com.example.oldpeoplehome.pojo;

import lombok.Data;

@Data
public class Administrator extends User{
    private Integer id;//自己的id
    private Integer orgId;//福利院id
}
