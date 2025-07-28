package com.mall.service.impl;

import com.mall.dto.LoginDTO;
import com.mall.dto.UserDTO;
import com.mall.exception.BizException;
import com.mall.service.AuthService;
import com.mall.util.JwtTokenGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtTokenGenerator jwtTokenGenerator, PasswordEncoder passwordEncoder) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    // 这里暂时注入 Mock，实际场景应通过 Feign 调用 User 服务或直接查 User 库
    // private final UserMapper userMapper;

    public String login(LoginDTO loginDTO) {
        // 1. 模拟数据库查询用户 (实际开发中请查数据库)
        // 假设数据库中有一个 admin / 123456 的用户
        if (!"admin".equals(loginDTO.getUsername())) {
            throw new BizException("用户不存在");
        }

        // 2. 校验密码 (数据库存的是加密后的，这里模拟比对)
        // 假设数据库存的是 BCrypt 加密后的 123456
        String dbHash = passwordEncoder.encode("123456");
        if (!passwordEncoder.matches(loginDTO.getPassword(), dbHash)) {
            throw new BizException("密码错误");
        }

        // 3. 构建 UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1001L);
        userDTO.setUsername("admin");
        // 赋予权限：商品添加和订单查询
        userDTO.setRoles(Arrays.asList("sys:product:add", "sys:order:list", "ROLE_ADMIN"));

        // 4. 生成 Token
        String token = jwtTokenGenerator.generateToken(userDTO);

        return token;
    }
}
