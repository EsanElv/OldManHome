package com.example.oldpeoplehome.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VisitorUpdateDTO {
    private Integer id;
    @Pattern(regexp = "^\\d{11}$")
    private String phone;
    @Pattern(regexp = "^[a-zA-Z0-9_]{5,16}$")
    private String password;
    private String nickname;
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,}(:(\\d{1,5}))?(/\\S*)?$")
    private String avatar;
}
