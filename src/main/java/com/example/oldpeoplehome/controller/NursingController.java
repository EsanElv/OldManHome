package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.dto.NurAddDTO;
import com.example.oldpeoplehome.dto.NurDetailInfoDTO;
import com.example.oldpeoplehome.dto.NurListDTO;
import com.example.oldpeoplehome.dto.NurUpdateDTO;
import com.example.oldpeoplehome.pojo.Nursing;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.pojo.User;
import com.example.oldpeoplehome.service.NursingService;
import com.example.oldpeoplehome.service.UserService;
import com.example.oldpeoplehome.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nur")
@Validated
public class NursingController {
    @Autowired
    private NursingService nursingService;

    @Autowired
    private UserService userService;



    @GetMapping("/list")
    public Result<Map<String,Object>> list(@RequestParam Integer orgId){
        NurListDTO nurListDTO = new NurListDTO();
        List<NurListDTO> nurListDTOList = nursingService.findList(orgId,null,null,nurListDTO);
        Integer total =nurListDTOList.size();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("nurItems",nurListDTOList);

        return Result.success(map);
    }


    @GetMapping("/info")
    public Result<NurDetailInfoDTO> info(@RequestParam(required = false) Integer nurId,
                       @RequestHeader(value = "Authorization", required = false) String token){
        if (nurId == null) {
            /*System.out.println(1);
            System.out.println(token);
            System.out.println(1);*/
            if (token == null) {
                return Result.error("Authorization token missing or invalid.");
            }
            try {
                // 获取claims
                Map<String, Object> claims = JwtUtil.parseToken(token);
                // 获取护工id和type
                Integer typeInToken = (Integer) claims.get("type");
                if(typeInToken != 3){
                    return Result.error("ID type error!");
                }
                //                System.out.println(userId);

                nurId = (Integer) claims.get("id");

            } catch (Exception e) {
                return Result.error("Failed to parse token: " + e.getMessage());
            }
        }

        NurDetailInfoDTO nurDetailInfoDTO = new NurDetailInfoDTO();
        NurDetailInfoDTO targetNur = nursingService.find(nurId,null,null,nurDetailInfoDTO);
        System.out.println(targetNur);
        return Result.success(targetNur);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Validated NurAddDTO nurAddDTO){
        Nursing nursing = new Nursing();

        BeanUtils.copyProperties(nurAddDTO, nursing);
        nursing.setUsername(nurAddDTO.getPhone());
        nursing.setUserPic(nurAddDTO.getAvatar());
        nursing.setType(3);


        String logUsername = nursing.getUsername();

        User logUser = userService.findByUserName(logUsername);
        if(logUser != null){
            return Result.error("该用户已经存在");
        }
        nursingService.add(nursing);
        return Result.success();
    }

    @PatchMapping("update")
    public Result update(@RequestBody @Validated NurUpdateDTO nurUpdateDTO,
                         @RequestHeader(value = "Authorization", required = false) String token){
        if(nurUpdateDTO.getId() == null){
            if(token == null){
                return Result.error("Authorization token missing or invalid.");
            }
            try {
                // 获取claims
                Map<String, Object> claims = JwtUtil.parseToken(token);
                // 获取护工id和type
                Integer typeInToken = (Integer) claims.get("type");
                if(typeInToken != 3){
                    return Result.error("ID type error!");
                }
                //                System.out.println(userId);

                nurUpdateDTO.setId((Integer) claims.get("id"));

            } catch (Exception e) {
                return Result.error("Failed to parse token: " + e.getMessage());
            }
        }
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer id = (Integer) claims.get("id");
        Integer type = (Integer) claims.get("type");
        System.out.println("当前用户的id："+id);
        System.out.println("当前用户的type："+type);
        System.out.println("要修改的id："+nurUpdateDTO.getId());
        return nursingService.update(nurUpdateDTO);

    }

    @DeleteMapping("/delete")
    public Result delete(Integer id,
                         @RequestHeader(value = "Authorization", required = false) String token){
        if(id == null){
            id = JwtUtil.checkToken(token,3);
            if(id == -1){
                return Result.error("No id or Type error!");
            }
        } else {
            Integer adminId = JwtUtil.checkToken(token,2);
            if(adminId == -1){
                return Result.error("only administrator can delete");
            }
        }
        return nursingService.delete(id);
    }
}
