package com.example.oldpeoplehome.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NewsInfoDTO {
    private String title;
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,}(:(\\d{1,5}))?(/\\S*)?$")
    private String cover;
    private String content;
    private String time;
    private Integer manId;
}
