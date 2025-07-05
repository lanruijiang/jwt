import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class SimpleJwtExample {

    // 定义一个密钥 (在真实应用中，这应该是一个复杂的、安全存储的密钥)
    private static final String SECRET_KEY = "123--9=@$9527";

    public static void main(String[] args) {
        try {
            // =================  1. 生成 JWT =================
            System.out.println("--- 开始生成 JWT ---");

            // 使用 HMAC256 算法，并提供密钥
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            // JWT 的过期时间 (例如：10分钟后)
            long expirationTimeInMillis = System.currentTimeMillis() + 10 * 60 * 1000; // 10 minutes
            Date expirationDate = new Date(expirationTimeInMillis);

            // 创建 JWT
            String token = JWT.create()
                    .withIssuer("MyAuthServer") // iss (签发者)
                    .withSubject("user-details") // sub (主题)
                    .withClaim("userId", "123456") // 添加自定义声明 (payload)
                    .withClaim("username", "zhangsan")
                    .withIssuedAt(new Date()) // iat (签发时间)
                    .withExpiresAt(expirationDate) // exp (过期时间)
                    .sign(algorithm); // 使用算法进行签名

            System.out.println("生成的 Token: " + token);
            System.out.println("========================\n");


            // ================= 2. 验证 JWT =================
            System.out.println("--- 开始验证 JWT ---");

            // 准备一个验证器
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("MyAuthServer") // 验证签发者
                    .build(); // 创建验证器

            // 验证 token
            DecodedJWT decodedJWT = verifier.verify(token);

            System.out.println("验证成功!");

            // 从解码后的 JWT 中获取信息
            String userId = decodedJWT.getClaim("userId").asString();
            String username = decodedJWT.getClaim("username").asString();
            Date expiresAt = decodedJWT.getExpiresAt();

            System.out.println("用户ID: " + userId);
            System.out.println("用户名: " + username);
            System.out.println("过期时间: " + expiresAt);
            System.out.println("========================\n");


            // ================= 3. 演示验证失败的场景 =================
            System.out.println("--- 演示一个验证失败的场景 (使用错误的密钥) ---");
            try {
                Algorithm wrongAlgorithm = Algorithm.HMAC256("this-is-a-wrong-key");
                JWTVerifier wrongVerifier = JWT.require(wrongAlgorithm)
                        .withIssuer("MyAuthServer")
                        .build();
                wrongVerifier.verify(token); // 这行代码会抛出异常
            } catch (JWTVerificationException e) {
                System.out.println("验证失败，正如预期! 错误信息: " + e.getMessage());
            }

        } catch (JWTVerificationException e) {
            // 如果 JWT 无效 (例如：签名不匹配, token过期)
            System.out.println("JWT 验证失败: " + e.getMessage());
        }
    }
}