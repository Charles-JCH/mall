package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.UserInfo;

public interface IUserService extends IService<UserInfo> {
    UserInfo login(String username, String password);
}
