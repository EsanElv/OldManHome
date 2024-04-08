package com.example.oldpeoplehome.service;

import com.example.oldpeoplehome.pojo.Administrator;

public interface AdminService {


    <T>  T find(Integer id, Integer userId, T dto);
}
