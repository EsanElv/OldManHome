package com.example.oldpeoplehome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NurDetailInfoDTO {
    @JsonProperty("nurId")
    private Integer id;
    @JsonProperty("nurName")
    private String name;
    @JsonProperty("nurAge")
    private Integer age;
    @JsonProperty("nurPhone")
    private String username;
    @JsonProperty("orgId")
    private Integer orgId;
    @JsonProperty("nurAvatar")
    private String userPic;
    @JsonProperty("nurIdNumber")
    private String idNumber;
    @JsonProperty("nurGender")
    private String gender;
    @JsonProperty("other")
    private String other;

}
