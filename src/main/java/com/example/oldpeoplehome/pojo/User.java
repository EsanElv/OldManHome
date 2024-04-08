package com.example.oldpeoplehome.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    @NotNull
    protected Integer userId;//数据库中的userId
    protected String username;//用户名
    @JsonIgnore //让SpringMVC在把当前对象转换为json字符串时，忽略password，最终的json字符传中就没有password这个属性了
    protected String password;//密码
    protected String userPic;//用户头像地址
    protected Integer type;//1:游客 2：管理员 3：护工 4：老人

    public User getUser(){
        User u = new User();
        u.setUserId(userId);
        u.setUsername(username);
        u.setPassword(password);
        u.setUserPic(userPic);
        u.setType(type);
        return u;
    }

    public void setUser(User u){
        userId = u.getUserId();
        username = u.getUsername();
        password = u.getPassword();
        userPic = u.getUserPic();
        type = u.getType();
    }
}
