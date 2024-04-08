package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.dto.CareAddDTO;
import com.example.oldpeoplehome.dto.CareDTO;
import com.example.oldpeoplehome.pojo.Care;
import com.example.oldpeoplehome.pojo.Nursing;
import com.example.oldpeoplehome.pojo.OldMan;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.service.CareService;
import com.example.oldpeoplehome.service.NursingService;
import com.example.oldpeoplehome.service.OldManService;
import com.example.oldpeoplehome.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/care")
@Validated
public class CareController {
    @Autowired
    private CareService careService;
    @Autowired
    private OldManService oldManService;
    @Autowired
    private NursingService nursingService;
    @GetMapping("/list")
    public Result<Map<String,Object>> getCareList(@RequestParam(required = false) Integer manId,
                                             @RequestParam(required = false) Integer nurId,
                                             @RequestHeader(value = "Authorization", required = true) String token){
        if(manId == null && nurId == null){
            if(token == null){
                return Result.error("Authorization token missing or invalid. Or miss parameter.");
            }

            try {
                Map<String, Object> claims = JwtUtil.parseToken(token);
                System.out.println(claims);
                // 获取type
                Integer type = (Integer) claims.get("type");
                if(type == 3){//护工
                    nurId = (Integer) claims.get("id");
                } else if(type == 4){//老人
                    manId = (Integer) claims.get("id");
                } else {
                    return Result.error("ID type error!");
                }
            } catch (Exception e) {
                return Result.error("Failed to parse token: " + e.getMessage());
            }

        }
        List<Care> careList = careService.findList(manId, nurId, new Care());
        List<CareDTO> careDTOList = new ArrayList<>();
        for(Care care:careList){
            CareDTO careDTO = new CareDTO();
            OldMan oldMan = oldManService.find(care.getManId(),null,null,null,new OldMan());
            careDTO.setManName(oldMan.getName());
            Nursing nursing = nursingService.find(care.getNurId(),null,null,new Nursing());
            careDTO.setNurName(nursing.getName());
            BeanUtils.copyProperties(care,careDTO);
            careDTOList.add(careDTO);
        }
        Integer size = careDTOList.size();
        Map<String,Object> res = new HashMap<>();
        res.put("total",size);
        res.put("careItems",careDTOList);
        return Result.success(res);
    }

    @PostMapping("/add")
    public Result careAdd(@RequestBody CareAddDTO careAddDTO,
                          @RequestHeader(value = "AUthorization", required = false)String token) {
        if(careAddDTO.getNurId() == null){
            if(token == null){
                return Result.error("Authorization token missing or invalid. Or miss parameter.");
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer type = (Integer) claims.get("type");
            if(type != 3){
                return Result.error("ID type error!");
            }
            careAddDTO.setNurId((Integer) claims.get("id"));
        }

        careService.add(careAddDTO);
        return Result.success();
    }
}
