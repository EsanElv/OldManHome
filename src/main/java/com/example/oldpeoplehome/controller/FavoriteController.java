package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.pojo.Favorite;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favor")
@Validated
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public Result addFavor(Favorite favorite){
        return favoriteService.add(favorite);
    }
}
