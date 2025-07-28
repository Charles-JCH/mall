package com.mall;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.ibatis.type.JdbcType;

import java.util.Collections;

public class GeneratorCodeApplication {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mall_user?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";

        FastAutoGenerator.create(url, username, password)
                // 数据源配置
                .dataSourceConfig(builder -> {
                    builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                        // 兼容旧版本转换成Integer
                        if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                            return DbColumnType.INTEGER;
                        }
                        if (JdbcType.BIGINT == metaInfo.getJdbcType()) {
                            return DbColumnType.LONG;
                        }
                        return typeRegistry.getColumnType(metaInfo);
                    });
                })
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("") // 不设置作者
                            .commentDate("") // 不设置日期
                            .dateType(DateType.TIME_PACK) // 设置时间类型为 LocalDateTime
                            // .disableOpenDir() // 禁用自动打开输出目录
                            .outputDir(System.getProperty("user.dir") + "/src/main/java"); // 输出目录
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.mall") // 父包名
                            .entity("entity") // entity 包名
                            .mapper("mapper") // mapper 包名
                            .service("service") // service 包名
                            .serviceImpl("service.impl") // serviceImpl 包名
                            .controller("controller") // controller 包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    System.getProperty("user.dir") + "/src/main/resources/mapper")); // mapper.xml 路径
                })
                // 模板配置
                .templateConfig(builder -> {
                    builder.mapper("/templates/mapper.java.vm") // mapper 模板
                            .entity("/templates/entity.java.vm") // entity 模板
                            .service("/templates/service.java.vm") // service 模板
                            .serviceImpl("/templates/serviceImpl.java.vm") // serviceImpl 模板
                            .controller("/templates/controller.java.vm") // controller 模板
                            .build();
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("user_info", "user_address") // 需要生成的表
                            .addTablePrefix("t_", "sys_") // 过滤表前缀
                            // entity 策略
                            .entityBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            .logicDeleteColumnName("is_delete") // 逻辑删除字段
                            .versionColumnName("version") // 乐观锁字段
                            .addTableFills(new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE))
                            // mapper 策略
                            .mapperBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            .superClass(BaseMapper.class) // mapper 父类
                            .enableBaseResultMap() // 启用 BaseResultMap
                            .enableBaseColumnList() // 启用 BaseColumnList
                            .formatMapperFileName("%sMapper") // mapper 命名
                            .formatXmlFileName("%sMapper") // mapper.xml 命名
                            // service 策略
                            .serviceBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            .superServiceClass(IService.class) // service 父类
                            .superServiceImplClass(ServiceImpl.class) // serviceImpl 父类
                            .formatServiceFileName("%sService") // service 命名
                            .formatServiceImplFileName("%sServiceImpl") // serviceImpl 命名
                            // controller 策略
                            .controllerBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            .formatFileName("%sController"); // controller 命名
                })
                .templateEngine(new VelocityTemplateEngine()) // 模板引擎：Velocity
                .execute();
    }
}