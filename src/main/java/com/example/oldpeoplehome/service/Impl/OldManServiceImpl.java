package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.dto.OldManUpdateDTO;
import com.example.oldpeoplehome.mapper.OldManMapper;
import com.example.oldpeoplehome.mapper.UserMapper;
import com.example.oldpeoplehome.pojo.OldMan;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.pojo.User;
import com.example.oldpeoplehome.service.OldManService;
import com.example.oldpeoplehome.utils.Md5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class OldManServiceImpl implements OldManService {
    @Autowired
    private OldManMapper oldManMapper;

    @Autowired
    private UserMapper userMapper;

    //通过唯一的值返回某个特定对象
    //唯一的值有：id,guarPhone,idNumber,userId
    @Override
    public <T> T find(Integer id, String guarPhone, String idNumber, Integer userId, T dto){
        //调用mapper层返回完整的old_man对象,及其对应的user对象
        OldMan oldMan = oldManMapper.find(id, guarPhone, idNumber, userId);
//        System.out.println("find方法获取到的OldMan对象:"+oldMan);
//        System.out.println("find中oldman的userId：" + userId);
        User user = userMapper.find(oldMan.getUserId(),null);
//        System.out.println("find获取的user对象：" + user);
        oldMan.setUser(user);

        //将OldMan对象的值赋给DTO对象
        BeanUtils.copyProperties(oldMan, dto);
        System.out.println("dto中的id："+dto);

        return dto;
    }
    //通过不唯一的值返回对象列表
    //不唯一且有可能用于查询的值有：state,nur_id,care_total,org_id,age,gender
    @Override
    public <T> List<T> findList(String state, Integer nurId, Integer careTotal, Integer orgId, Integer age, String gender, T dto){
        //获取dto的类
        Class<?> dtoClass = dto.getClass();
        //调用mapper层返回完整的old_man对象的列表
        List<OldMan> oldManList = oldManMapper.findList(state, nurId, careTotal, orgId, age, gender);

        //将OldMan对象的值赋给DTO对象
        List<T> dtoList = new ArrayList<>();
        for(OldMan oldMan : oldManList){
            try{
                User user = userMapper.find(oldMan.getUserId(),null);
                oldMan.setUser(user);
                T newDto = (T)dtoClass.newInstance();
                BeanUtils.copyProperties(oldMan, newDto);
                dtoList.add(newDto);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return dtoList;
    }


    @Override
    public void add(OldMan oldMan) {
        //将原始密码加密
        String originPassword = oldMan.getPassword();
        String md5Password = Md5Util.getMD5String(originPassword);
        oldMan.setPassword(md5Password);
        /*String s = Md5Util.getMD5String("1111");
        System.out.println("加密密码："+s);*/

        //先在user表中创建，并获取user_id
        User user = oldMan.getUser();
        userMapper.add(user);
        Integer userId = user.getUserId();
        //再去old_man表中创建
        oldMan.setUserId(userId);
        oldManMapper.add(oldMan);
    }

    @Override
    public Result update(OldManUpdateDTO oldManUpdateDTO) {
        OldMan oldMan = oldManMapper.find(oldManUpdateDTO.getId(),null,null,null);
//        System.out.println(oldMan);
//        System.out.println("oldMan的user_id："+oldMan.getUserId());

//        System.out.println(oldManUpdateDTO);
//        System.out.println(user);
        boolean noNeedToUpdateOldMan = true;
        Field[] fields = oldManUpdateDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id") ||
                field.getName().equals("username") ||
                field.getName().equals("password") ||
                field.getName().equals("avatar")) {
                continue; // 跳过id属性
            }
            try {
                Object value = field.get(oldManUpdateDTO);
                if (value != null) {
                    // 如果字段不为空
                    noNeedToUpdateOldMan = false;
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(!noNeedToUpdateOldMan){
            oldManMapper.update(oldManUpdateDTO);
        }

        User user = oldMan.getUser();
        user.setUsername(oldManUpdateDTO.getPhone());
        user.setPassword(oldManUpdateDTO.getPassword());
        user.setUserPic(oldManUpdateDTO.getAvatar());
        boolean noNeedToUpdateUser = false;
        if(user.getUsername()==null && user.getPassword()==null && user.getUserPic()==null){
            noNeedToUpdateUser = true;
        } else {
            userMapper.update(user);
        }

        if(noNeedToUpdateOldMan && noNeedToUpdateUser){
            return Result.error("请更改至少一条信息");
        }
        return Result.success();
    }
}
