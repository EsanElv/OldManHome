package com.example.oldpeoplehome.pojo;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class News {
    private Integer id;
    private String title;
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,}(:(\\d{1,5}))?(/\\S*)?$")
    private String cover;
    private String content;
    private Integer manId;
    private String time;
}
