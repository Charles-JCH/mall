package com.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entity.UserInfo;

public interface UserInfoService extends IService<UserInfo> {

    UserInfo login(String username);

}
