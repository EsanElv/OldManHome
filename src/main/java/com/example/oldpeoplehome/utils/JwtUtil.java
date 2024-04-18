package com.example.oldpeoplehome.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.oldpeoplehome.pojo.Result;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "itheima";
	
	//接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256(KEY));
    }

	//接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

    public static Integer checkToken(String token, Integer type){
        Map<String,Object> claims = parseToken(token);
        Integer id = (Integer) claims.get("id");
        Integer typeInToken = (Integer) claims.get("type");
        if(type != typeInToken){
            return -1;
        }
        return id;
    }
}
