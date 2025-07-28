package com.mall.feign;

import com.mall.api.R;
import com.mall.dto.UserDTO;
import com.mall.feign.fallback.UserServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", fallback = UserServiceFeignFallback.class)
public interface IUserFeign {

    @GetMapping("/user/login")
    R<UserDTO> login(@RequestParam(name = "username") String username);
}
