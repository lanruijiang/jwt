package org.soft.base.jwtget.ctrl;


import io.jsonwebtoken.JwtException;
import org.soft.base.jwtget.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class ReceiveController {

    @GetMapping("/parse-token")
    public ResponseEntity<Map<String, Object>> parseToken(@RequestParam String token) {
        try {
            // 1. 使用工具类解析JWT以获取数据
            String data = JwtUtil.extractCustomData(token);
            // 2. 将数据放入Map中返回，方便前端JS处理
            Map<String, Object> response = Map.of("extractedData", data);
            return ResponseEntity.ok(response);
        } catch (JwtException e) {
            // 3. 如果token无效（过期、签名错误等），返回错误信息
            Map<String, Object> errorResponse = Map.of("error", "无效或已过期的Token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = Map.of("error", "服务器内部错误: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
