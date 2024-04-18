package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.mapper.UserMapper;
import com.example.oldpeoplehome.mapper.VisitorMapper;
import com.example.oldpeoplehome.pojo.*;
import com.example.oldpeoplehome.service.*;
import com.example.oldpeoplehome.utils.Md5Util;
import com.example.oldpeoplehome.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OldManService oldManService;
    @Autowired
    private NursingService nursingService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private VisitorMapper visitorMapper;
    @Override
    public User findByUserName(String username){
        User u = userMapper.find(null,username);
        return u;
    }

    @Override
    public void register(String username, String password, String nickname){
        //加密
        String md5String = Md5Util.getMD5String(password);
        //创建对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(md5String);
        user.setType(1);
        //添加用户表
        userMapper.add(user);
        //添加游客表
        User addedUser = userMapper.find(null, username);
        Integer userId = addedUser.getUserId();
        Visitor visitor = new Visitor();
        visitor.setNickname(nickname);
        visitor.setUserId(userId);
        visitorMapper.add(visitor);
    }

/*    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);

    }*/



    @Override
    public Integer getParticularId(Integer type, Integer userId) {
        Integer id = 0;

        switch (type){
            case 1:
                Visitor visitor = visitorService.find(null,userId,new Visitor());
                id = visitor.getId();
                break;
            case 2:
                Administrator administrator = adminService.find(null,userId,new Administrator());
                id = administrator.getId();
                break;
            case 3:
                Nursing nursing = nursingService.find(null,null,userId,new Nursing());
                id = nursing.getId();
                break;
            case 4:
                OldMan oldMan = oldManService.find(null,null,null,userId,new OldMan());
                id = oldMan.getId();
                break;
            default:
                break;
        }
        return id;
    }
}
