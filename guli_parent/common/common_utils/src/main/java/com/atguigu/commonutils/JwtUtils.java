package com.atguigu.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description：JWT令牌工具类
 * @Author：Ivy_up
 * @Create：2022-11-07 10:46
 */
public class JwtUtils {

    private static final long EXPIRE = 24 * 60 * 60 * 1000;

    private static final String APP_SECRET = "hudh2e2jfbs80hda1";

    public static String getJwtToken(String id, String nickname){
        String JwtToken = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("nickname", nickname)
                .signWith(SignatureAlgorithm.HS256,APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断Token是否有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken){
        if (StringUtils.isEmpty(jwtToken)){
            return false;
        }

        Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);

        return true;
    }

    /**
     * 判断Token是否有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request){
        String jwtToken = request.getHeader("token");

        if (StringUtils.isEmpty(jwtToken)){
            return false;
        }

        Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);

        return true;
    }

    /**
     * 根据Token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request){
        String jwtToken = request.getHeader("token");

        if (StringUtils.isEmpty(jwtToken)){
            return "";
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);

        Claims claims = claimsJws.getBody();

        return claims.get("id",String.class);
    }

}
