package com.example.oldpeoplehome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OldManListDTO {
    @JsonProperty("manId")
    private Integer id;
    @JsonProperty("manName")
    private String name;
    @JsonProperty("manAvatar")
    private String userPic;
}
