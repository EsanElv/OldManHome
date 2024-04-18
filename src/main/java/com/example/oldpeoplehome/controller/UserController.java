package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.pojo.*;
import com.example.oldpeoplehome.service.*;
import com.example.oldpeoplehome.utils.Md5Util;
import com.example.oldpeoplehome.utils.JwtUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "\\S{5,16}") String password,
                           String nickname) {
        //查询用户
        User u = userService.findByUserName(username);
        if (u == null) {
            //没有被占用，进行注册
            userService.register(username, password, nickname);
            return Result.success();
        } else {
            //被占用
            return Result.error("用户名已被使用！");
        }
    }

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "\\S{5,16}") String password) {
        String md5 = Md5Util.getMD5String(password);
        System.out.println("当前密码的md5字符串：" + md5);

        //根据用户名查询用户
        User loginUser = userService.findByUserName(username);

        //判断用户是否存在
        if (loginUser == null) {
            return Result.error("用户不存在");
        }

        //判断密码是否正确，此处的password已经是md5算法加密过后的
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            //登录成功
            //首先获取用户的身份信息
            Integer type = loginUser.getType();
            Integer userId = loginUser.getUserId();
            //用户自己属性的id
            Integer id = userService.getParticularId(type, userId);
            System.out.println("加入token的id："+id);

            Map<String, Object> claims = new HashMap<>();
            /*claims.put("id", loginUser.getUserId());
            claims.put("username", loginUser.getUsername());*/
            //token中加入身份信息
            claims.put("type",type);
            claims.put("id",id);//用户自己属性的id
            String token = JwtUtil.genToken(claims);
            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 12, TimeUnit.HOURS);
            Map<String,Object> res = new HashMap<>();
            res.put("type",type);
            res.put("token",token);
            return Result.success(res);
        }
        return Result.error("密码错误");
    }
}