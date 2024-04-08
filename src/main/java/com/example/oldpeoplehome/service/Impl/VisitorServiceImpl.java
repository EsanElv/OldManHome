package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.mapper.UserMapper;
import com.example.oldpeoplehome.mapper.VisitorMapper;
import com.example.oldpeoplehome.pojo.User;
import com.example.oldpeoplehome.pojo.Visitor;
import com.example.oldpeoplehome.service.VisitorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    private VisitorMapper visitorMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public <T> T find(Integer id, Integer userId, T dto) {
        //调用mapper层返回完整对象
        Visitor visitor = visitorMapper.find(id, userId);
        User user = userMapper.find(visitor.getUserId(),null);
        visitor.setUser(user);
        //将完整对象赋给DTO
        BeanUtils.copyProperties(visitor,dto);

        return dto;
    }
}
