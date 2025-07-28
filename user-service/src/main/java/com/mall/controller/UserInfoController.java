package com.mall.controller;

import com.mall.api.R;
import com.mall.entity.UserInfo;
import com.mall.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserInfoController extends BaseController<UserInfoService, UserInfo> {

    public UserInfoController(UserInfoService service) {
        super(service);
    }

    @GetMapping("/login")
    public R<UserInfo> login(@RequestParam(name = "username") String username) {
        UserInfo userInfo = service.login(username);
        if (userInfo == null) {
            return R.error(400, "Invalid username or password");
        }
        return R.ok(userInfo);
    }
}
