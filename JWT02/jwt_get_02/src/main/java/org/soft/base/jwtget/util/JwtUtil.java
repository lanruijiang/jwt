package org.soft.base.jwtget.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.function.Function;

public class JwtUtil {

    // !!! 重要的密钥，必须与发送方项目中的密钥完全一致 !!!
    private static final String SECRET_KEY = "bXktc3VwZXItc2VjcmV0LWtleS10aGF0LWlzLWxvbmctZW5vdWdo";

    private static Key getSigningKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());
    }

    // 从JWT中解析所有声明
    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 从JWT中解析单个声明
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从JWT中提取我们存入的自定义数据
     * @param token JWT字符串
     * @return 自定义数据
     */
    public static String extractCustomData(String token) {
        return extractClaim(token, claims -> claims.get("customData", String.class));
//        return extractClaim(token, new Function<Claims, String>() {
//            @Override
//            public String apply(Claims claims) {
//                return claims.get("customData", String.class);
//            }
//        });
    }
}