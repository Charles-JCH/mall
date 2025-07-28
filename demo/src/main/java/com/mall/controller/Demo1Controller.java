package com.mall.controller;

import com.mall.service.PoiService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class Demo1Controller {

    @Autowired
    private PoiService poiService;

    @GetMapping("/test")
    public void test1() {
        System.out.println("hello world");
    }

    @PostMapping("/export")
    public void exportExcel(HttpServletResponse response) throws Exception {
        Workbook workbook = poiService.exportExcel();
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // 写入响应流中
        workbook.write(response.getOutputStream());
        // 释放资源
        workbook.close();
    }
}
