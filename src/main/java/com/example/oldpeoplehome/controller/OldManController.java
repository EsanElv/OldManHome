package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.dto.*;

import com.example.oldpeoplehome.pojo.OldMan;
import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.pojo.User;
import com.example.oldpeoplehome.service.OldManService;
import com.example.oldpeoplehome.service.UserService;
import com.example.oldpeoplehome.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/man")
@Validated
public class OldManController {
    @Autowired
    private OldManService oldManService;

    @Autowired
    private UserService userService;


/*    @PostMapping("/login")
    public Result login(String phone, String password){
        //根据手机号查询老人
        OldMan loginOldMan = oldManService.findByPhone(phone);

        String s = Md5Util.getMD5String("1111");
//        System.out.println("1111的md5字符串："+s);
        //判断老人的账户是否存在
        if(loginOldMan == null){
            return Result.error("用户不存在");
        }

        //判断密码是否正确，此处的password已经是md5算法加密过后的
        if(Md5Util.getMD5String(password).equals(loginOldMan.get())){
            //登陆成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("phone", loginOldMan.getPhone());
            claims.put("manId", loginOldMan.getManId());
            String token = JwtUtil.genToken(claims);
            //把token存redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,12, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }*/

    @GetMapping("/list/org")
    public Result<Map<String, Object>> getByOrgId(Integer orgId){
        OldManListDTO oldManListDTO = new OldManListDTO();
        List<OldManListDTO> oldManListDTOList = oldManService.findList(null,null,null,orgId,null,null,oldManListDTO);
        Integer total = oldManListDTOList.size();

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("manItems", oldManListDTOList);

        return Result.success(data);
    }

    @GetMapping("/list/nur")
    public Result<Map<String, Object>> getByNurId(Integer nurId){
        OldManListDTO oldManListDTO = new OldManListDTO();
        List<OldManListDTO> oldManListDTOList = oldManService.findList(null,nurId,null,null,null,null,oldManListDTO);
        Integer total = oldManListDTOList.size();

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("manItems", oldManListDTOList);

        return Result.success(data);
    }

    @GetMapping("/info")
    public Result<OldManDetailInfoDTO> getOldManInfo(@RequestParam(required = false) Integer manId,
                                                     @RequestHeader(value = "Authorization", required = false) String token) {
        if (manId == null) {
            /*System.out.println(1);
            System.out.println(token);
            System.out.println(1);*/
            if (token == null) {
                return Result.error("Authorization token missing or invalid.");
            }
            try {
                // 解析token
                // 获取claims
                Map<String, Object> claims = JwtUtil.parseToken(token);
                System.out.println(claims);
                // 获取type
                Integer type = (Integer) claims.get("type");
                if(type != 4){
                    return Result.error("ID type error!");
                }

                // 使用解析出的ID
                manId = (Integer) claims.get("id");
                System.out.println("当前获取的manID："+manId);
            } catch (Exception e) {
                return Result.error("Failed to parse token: " + e.getMessage());
            }
        }

//        System.out.println("当前的manId："+manId);
        OldManDetailInfoDTO oldManDetailInfoDTO = new OldManDetailInfoDTO();
        OldManDetailInfoDTO targetOldMan = oldManService.find(manId,null,null,null,oldManDetailInfoDTO);
        return Result.success(targetOldMan);
    }


    @PostMapping("/add")
    public Result<OldManAddDTO> add(@RequestBody @Validated OldManAddDTO oldManAddDTO){
        //先查找用户是否存在
        String logUsername = oldManAddDTO.getUsername();
        User user = userService.findByUserName(logUsername);
        if(user != null){
            return Result.error("用户已经存在！");
        }

        //用户不存在则调用service加入
        OldMan oldMan = new OldMan();
        BeanUtils.copyProperties(oldManAddDTO,oldMan);
        oldMan.setType(4);
        oldManService.add(oldMan);
        return Result.success();
    }

    @PatchMapping("update")
    public Result update(@RequestBody @Validated OldManUpdateDTO oldManUpdateDTO,
                         @RequestHeader(value = "Authorization", required = false) String token){
        if(oldManUpdateDTO.getId() == null){
            if(token == null){
                return Result.error("Authorization token missing or invalid.");
            }
            try {
                // 获取claims
                Map<String, Object> claims = JwtUtil.parseToken(token);
                // 获取老人id和type
                Integer typeInToken = (Integer) claims.get("type");
                if(typeInToken != 4){
                    return Result.error("ID type error!");
                }
                //                System.out.println(userId);

                oldManUpdateDTO.setId((Integer) claims.get("id"));

            } catch (Exception e) {
                return Result.error("Failed to parse token: " + e.getMessage());
            }
        }

        return oldManService.update(oldManUpdateDTO);

    }

    @DeleteMapping("/delete")
    public Result delete(Integer id,
                         @RequestHeader(value = "Authorization", required = false) String token){
        if(id == null){
            id = JwtUtil.checkToken(token,4);
            if(id == -1){
                return Result.error("No id or Type error!");
            }
        } else {
            Integer adminId = JwtUtil.checkToken(token,2);
            if(adminId == -1){
                return Result.error("only administrator can delete");
            }
        }

        oldManService.delete(id);
        return Result.success();
    }
}
