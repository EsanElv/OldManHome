package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.mapper.UserMapper;
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
    @Override
    public User findByUserName(String username){
        User u = userMapper.find(null,username);
        return u;
    }

    @Override
    public void register(String username, String password){
        //加密
        String md5String = Md5Util.getMD5String(password);
        //创建对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(md5String);
        user.setType(1);
        //添加
        userMapper.add(user);

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
