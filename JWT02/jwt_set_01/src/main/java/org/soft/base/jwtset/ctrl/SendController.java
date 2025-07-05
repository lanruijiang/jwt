package org.soft.base.jwtset.ctrl;

import org.soft.base.jwtset.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SendController {

    @PostMapping("/generate-token")
    public RedirectView generateTokenAndRedirect(@RequestParam String data) {
        // 1. 使用工具类生成JWT
        String token = JwtUtil.generateToken(data);

        // 2. 构建目标URL，将token作为参数
        String targetUrl = "http://localhost:4321/get.html?token=" + token;

        // 3. 重定向到项目二的页面
        return new RedirectView(targetUrl);
    }
}