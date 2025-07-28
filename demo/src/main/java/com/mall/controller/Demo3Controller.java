package com.mall.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.TimeZone;

@RestController
@RequestMapping("/demo3")
public class Demo3Controller {

    @PostMapping("/test")
    public String test(@RequestBody TestDate testDate) {
        TimeZone defaultZone = TimeZone.getDefault();
        System.out.println("时区ID: " + defaultZone.getID());  // 如Asia/Shanghai
        System.out.println("显示名称: " + defaultZone.getDisplayName());
        System.out.println("偏移量(毫秒): " + defaultZone.getOffset(System.currentTimeMillis()));
        
        System.out.println(testDate.getDate());
        return "success";
    }

    @GetMapping("/a")
    public String a() {
        System.out.println("aaaaaaaaaaaaaaaaa");
        return "aaaaaaaaaaaaaaaaa";
    }
}

@Data
class TestDate implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime date;
}