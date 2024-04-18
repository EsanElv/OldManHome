package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.dto.CareAddDTO;
import com.example.oldpeoplehome.dto.CareUpdateDTO;
import com.example.oldpeoplehome.mapper.CareMapper;
import com.example.oldpeoplehome.mapper.NursingMapper;
import com.example.oldpeoplehome.mapper.OldManMapper;
import com.example.oldpeoplehome.pojo.Care;
import com.example.oldpeoplehome.pojo.OldMan;
import com.example.oldpeoplehome.service.CareService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CareServiceImpl implements CareService {
    @Autowired
    private CareMapper careMapper;
    @Override
    public <T> List<T> findList(Integer manId, Integer nurId, T dto) {
        //解析dto的类
        Class<?> dtoClass = dto.getClass();
        //从Mapper获取全部信息
        List<Care> careList = careMapper.findList(manId, nurId);
        //赋给DTO
        List<T> dtoList = new ArrayList<>();
        for(Care care : careList){
            try{
                T newDto = (T)dtoClass.newInstance();
                BeanUtils.copyProperties(care, newDto);
                dtoList.add(newDto);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return dtoList;
    }

    @Override
    public void add(CareAddDTO careAddDTO) {
        careMapper.add(careAddDTO);
    }

    @Override
    public void update(CareUpdateDTO careUpdateDTO) {
        System.out.println(careUpdateDTO.getCareId());
        System.out.println(careUpdateDTO.getContent());
        careMapper.update(careUpdateDTO);
    }

    @Override
    public void delete(Integer careId) {
        careMapper.delete(careId);
    }
}
