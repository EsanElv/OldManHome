package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.dto.VisitorUpdateDTO;
import com.example.oldpeoplehome.mapper.UserMapper;
import com.example.oldpeoplehome.mapper.VisitorMapper;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.pojo.User;
import com.example.oldpeoplehome.pojo.Visitor;
import com.example.oldpeoplehome.service.VisitorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

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

    @Override
    public Result update(VisitorUpdateDTO visitorUpdateDTO) {
        Visitor visitor = visitorMapper.find(visitorUpdateDTO.getId(),null);
        boolean noNeedToUpdateVisitor = true;
        Field[] fields = visitorUpdateDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id") ||
                    field.getName().equals("username") ||
                    field.getName().equals("password") ||
                    field.getName().equals("avatar")) {
                continue; // 跳过id属性
            }
            try {
                Object value = field.get(visitorUpdateDTO);
                if (value != null) {
                    // 如果字段不为空
                    noNeedToUpdateVisitor = false;
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(!noNeedToUpdateVisitor){
            visitorMapper.update(visitorUpdateDTO);
        }

        User user = visitor.getUser();
        user.setUsername(visitorUpdateDTO.getPhone());
        user.setPassword(visitorUpdateDTO.getPassword());
        user.setUserPic(visitorUpdateDTO.getAvatar());
        boolean noNeedToUpdateUser = false;
        if(user.getUsername()==null && user.getPassword()==null && user.getUserPic()==null){
            noNeedToUpdateUser = true;
        } else {
            userMapper.update(user);
        }

        if(noNeedToUpdateUser && noNeedToUpdateVisitor){
            return Result.error("请至少更改一条信息");
        }

        return Result.success();
    }

}
