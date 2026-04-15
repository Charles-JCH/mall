package org.example.controller;

import org.example.entity.UserInfo;
import org.example.service.IUserService;
import org.example.util.JwtUtil;
import org.example.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<IUserService, UserInfo> {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    protected UserController(IUserService service) {
        super(service);
    }

    @GetMapping("/login")
    public R<String> loginHandler(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        UserInfo userInfo = userService.login(username, password);
        if (userInfo == null) {
            return R.error(400, "Invalid username or password");
        }
        Map<String, Object> claims = new HashMap<>();
        String token = jwtUtil.generateToken(username, claims);
        return R.ok(token);
    }
}
