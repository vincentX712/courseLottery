package com.officerschool.courselottery.common.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : create by xiangwenchao@zhejianglab.com
 * @version : v1.0
 * @date : 2024/4/3
 */
public class JwtUtil {
    /**
     * 密钥
     */
    private static final String SECRET = "courselottery";

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 86400L;//单位为秒

    /**
     * 生成用户token,设置token超时时间
     */
//    public static String createToken(UserInfo user) {
//        //过期时间
//        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
//        Map<String, Object> map = new HashMap<>();
//        map.put("alg", "HS256");
//        map.put("typ", "JWT");
//        String token = JWT.create()
//                .withHeader(map)// 添加头部
//                //可以将基本信息放到claims中
//                .withClaim("id", user.getId())//userId
//                .withClaim("phone", user.getPhone())//phone
//                .withClaim("name", user.getName())//name
//                .withClaim("roleId", user.getRoleId())
//                .withExpiresAt(expireDate) //超时设置,设置过期的日期
//                .withIssuedAt(new Date()) //签发时间
//                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
//        return token;
//    }

    /**
     * 校验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //解码异常则抛出异常
            return null;
        }
        return jwt.getClaims();
    }
}
