package com.example.oldpeoplehome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NurListDTO {
    @JsonProperty("nurId")
    private Integer id;
    @JsonProperty("nurName")
    private String name;
    @JsonProperty("nurAvatar")
    private String userPic;
}
