package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.mapper.AdminMapper;
import com.example.oldpeoplehome.mapper.UserMapper;
import com.example.oldpeoplehome.pojo.Administrator;
import com.example.oldpeoplehome.pojo.User;
import com.example.oldpeoplehome.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public <T> T find(Integer id, Integer userId, T dto) {
        //调用mapper层返回完整对象
        Administrator administrator = adminMapper.find(id, userId);
        User user = userMapper.find(administrator.getUserId(),null);
        administrator.setUser(user);

        //将完整对象赋给DTO
        BeanUtils.copyProperties(administrator,dto);

        return dto;
    }
}
