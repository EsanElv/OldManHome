package com.example.oldpeoplehome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VisitorShowDTO {
    private Integer id;
    @JsonProperty("phone")
    private String username;
    private String nickname;
    @JsonProperty("avatar")
    private String userPic;
}
