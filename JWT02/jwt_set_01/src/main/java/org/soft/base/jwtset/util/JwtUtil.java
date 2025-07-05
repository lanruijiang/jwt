package org.soft.base.jwtset.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // !!! 重要的密钥，必须与接收方项目中的密钥完全一致 !!!
    private static final String SECRET_KEY = "bXktc3VwZXItc2VjcmV0LWtleS10aGF0LWlzLWxvbmctZW5vdWdo";

    // 过期时间（毫秒），这里设置为1小时
    private static final long EXPIRATION_TIME = 3600_000;

    /**
     * 生成JWT
     * @param data 要存储在JWT中的自定义数据
     * @return JWT字符串
     */
    public static String generateToken(String data) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("customData", data); // 将我们的数据放入自定义声明中

        Key signingKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims) // 设置自定义声明
                .setSubject("data-transfer") // 主题
                .setIssuedAt(new Date(System.currentTimeMillis())) // 发布时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .signWith(signingKey, SignatureAlgorithm.HS256) // 签名算法和密钥
                .compact();
    }
}