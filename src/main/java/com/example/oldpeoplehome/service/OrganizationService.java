package com.example.oldpeoplehome.service;

import com.example.oldpeoplehome.pojo.Organization;

import java.util.List;

public interface OrganizationService {

    Organization find(Integer id, String name);
    List<Organization> findList();
}
