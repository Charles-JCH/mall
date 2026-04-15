package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dao.IUserMapper;
import org.example.entity.UserInfo;
import org.example.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, UserInfo> implements IUserService {

    @Override
    public UserInfo login(String username, String password) {
        return this.getOne(new QueryWrapper<UserInfo>().eq("username", username).eq("password", password));
    }
}
