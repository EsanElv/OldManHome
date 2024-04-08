package com.example.oldpeoplehome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "content", "manName", "nurName", "time"})
public class CareDTO {
    @JsonProperty("careId")
    private Integer id;
    private String content;
    private String manName;
    private String nurName;
    private String time;
}
