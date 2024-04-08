package com.example.oldpeoplehome.service.Impl;

import com.example.oldpeoplehome.mapper.OrganizationMapper;
import com.example.oldpeoplehome.pojo.Organization;
import com.example.oldpeoplehome.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public Organization find(Integer id, String name) {
        return organizationMapper.find(id, name);
    }

    @Override
    public List<Organization> findList() {
        List<Organization> organizationList = organizationMapper.findList();
//        System.out.println(organizationList);
        return organizationList;
    }
}
