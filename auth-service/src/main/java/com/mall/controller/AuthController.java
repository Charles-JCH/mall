package com.mall.controller;

import cn.hutool.core.io.IoUtil;
import com.mall.api.R;
import com.mall.config.RsaKeyProperties;
import com.mall.dto.LoginDTO;
import com.mall.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    @PostMapping("/login")
    public R<String> login(@RequestBody @Validated LoginDTO loginDTO) {
        return R.success(authService.login(loginDTO));
    }

    @GetMapping("/public-key")
    public R<String> getPublicKey() {
        try {
            ClassPathResource resource = new ClassPathResource(rsaKeyProperties.getPublicKeyPath().replace("classpath:", ""));
            String keyStr = IoUtil.readUtf8(resource.getInputStream());
            return R.success(keyStr);
        } catch (Exception e) {
            return R.failed("获取公钥失败");
        }
    }
}
