package com.example.oldpeoplehome.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String username;
    private String password;
    private String userPic;
    private Integer userId;
}
