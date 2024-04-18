package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.dto.NurUpdateDTO;
import com.example.oldpeoplehome.mapper.NursingMapper;
import com.example.oldpeoplehome.mapper.UserMapper;
import com.example.oldpeoplehome.pojo.Nursing;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.pojo.User;
import com.example.oldpeoplehome.service.NursingService;
import com.example.oldpeoplehome.utils.Md5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class NursingServiceImpl implements NursingService {
    @Autowired
    private NursingMapper nursingMapper;
    @Autowired
    private UserMapper userMapper;

    //通过唯一的值返回某个特定对象
    //唯一的值有：id,idNumber,userId
    @Override
    public <T> T find(Integer id, String idNumber, Integer userId, T dto) {
        //调用mapper层返回完整的old_man对象，及其对应的user对象，并进行装配
        Nursing nursing = nursingMapper.find(id, idNumber, userId);
        User user = userMapper.find(nursing.getUserId(),null);
        nursing.setUser(user);

        System.out.println("nursing的内容：" + nursing);
        //将OldMan对象的值赋给DTO对象
        BeanUtils.copyProperties(nursing, dto);
        System.out.println("dto的内容：" + dto);
        return dto;
    }

    //通过不唯一的值返回对象列表
    //不唯一且有可能用于查询的值有：org_id,age,gender
    @Override
    public <T> List<T> findList(Integer orgId, Integer age, String gender, T dto) {
        //获取dto的类
        Class<?> dtoClass = dto.getClass();

        //调用mapper层返回完整的nursing对象的列表
        List<Nursing> nursingList = nursingMapper.findList(orgId, age, gender);

        //将OldMan对象的值赋给DTO对象
        List<T> dtoList = new ArrayList<>();
        for (Nursing nursing : nursingList) {
            try{
                User user = userMapper.find(nursing.getUserId(),null);
                nursing.setUser(user);
                T newDto = (T) dtoClass.newInstance();
                BeanUtils.copyProperties(nursing, newDto);
                dtoList.add(newDto);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return dtoList;
    }


    @Override
    public void add(Nursing nursing) {
        String originPassword = nursing.getPassword();
//        System.out.println(originPassword);
        String md5Password = Md5Util.getMD5String(originPassword);
        nursing.setPassword(md5Password);

        User user = nursing.getUser();
        System.out.println(user);
        userMapper.add(user);
        nursing.setUserId(user.getUserId());
        nursingMapper.add(nursing);
    }

    @Override
    public Result update(NurUpdateDTO nurUpdateDTO) {
        Nursing nursing = nursingMapper.find(nurUpdateDTO.getId(),null,null);
        User user = userMapper.find(nursing.getUserId(),null);

        boolean noNeedToUpdateNursing = true;
        Field[] fields = nurUpdateDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id") ||
                    field.getName().equals("phone") ||
                    field.getName().equals("password") ||
                    field.getName().equals("avatar")) {
                continue; // 跳过user中的属性，只检查visitor中特有的属性
            }
            try {
                Object value = field.get(nurUpdateDTO);
                if (value != null) {
                    // 如果字段不为空
                    noNeedToUpdateNursing = false;
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(!noNeedToUpdateNursing){
            nursingMapper.update(nurUpdateDTO);
        }

        user.setUsername(nurUpdateDTO.getPhone());
        user.setPassword(nurUpdateDTO.getPassword());
        user.setUserPic(nurUpdateDTO.getAvatar());
        boolean noNeedToUpdateUser = false;
        if(user.getUsername()==null && user.getPassword()==null && user.getUserPic()==null){
            noNeedToUpdateUser = true;
        } else {
            userMapper.update(user);
        }

        if(noNeedToUpdateNursing && noNeedToUpdateUser){
            return Result.error("请更改至少一条信息");
        }
        return Result.success();
    }

    @Override
    public Result delete(Integer id) {
        Nursing nursing = nursingMapper.find(id, null, null);
        nursingMapper.delete(id);
        userMapper.delete(nursing.getUserId());
        return Result.success();
    }

}
