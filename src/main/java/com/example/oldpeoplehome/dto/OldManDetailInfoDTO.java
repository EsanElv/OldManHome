package com.example.oldpeoplehome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OldManDetailInfoDTO {
    @JsonProperty("manId")
    private Integer id;
    @JsonProperty("manName")
    private String name;
    @JsonProperty("manAge")
    private Integer age;
    @JsonProperty("manPhone")
    private String username;
    @JsonProperty("manAvatar")
    private String userPic;
    @JsonProperty("manIdNumber")
    private String idNumber;
    @JsonProperty("manGender")
    private String gender;
    @JsonProperty("guarName")
    private String guarName;
    @JsonProperty("guarPhone")
    private String guarPhone;
    @JsonProperty("nurId")
    private Integer nurId;
    @JsonProperty("orgId")
    private Integer orgId;
    @JsonProperty("state")
    private String state;
    @JsonProperty("plan")
    private String plan;
    @JsonProperty("other")
    private String other;
}
