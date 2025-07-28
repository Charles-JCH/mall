package com.mall.service;

import com.mall.dto.LoginDTO;

public interface AuthService {
    String login(LoginDTO loginDto);
}
