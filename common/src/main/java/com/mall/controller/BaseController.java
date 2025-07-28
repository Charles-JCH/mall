package com.mall.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.R;
import com.mall.util.Log;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public abstract class BaseController<S extends IService<T>, T> {

    protected final S service;

    protected BaseController(S service) {
        this.service = service;
    }

    /**
     * 根据ID查询记录
     *
     * @param id 记录ID
     * @return 记录
     */
    @GetMapping("/selectById/{id}")
    public R<T> selectById(@PathVariable Integer id) {
        T t = service.getById(id);
        if (t == null) {
            return R.error(404, "Record not found");
        }
        return R.ok(t);
    }

    /**
     * 分页条件查询
     *
     * @param pageNum  页码
     * @param pageSize 页大小
     * @param entity   查询条件
     * @return 条件查询结果
     */
    @PostMapping("/pageQuery")
    public R<List<T>> pageQuery(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestBody(required = false) T entity) {
        QueryWrapper<T> queryWrapper = buildQueryWrapper(entity);
        Page<T> page = service.page(new Page<>(pageNum, pageSize), queryWrapper);
        if (page == null) {
            return R.error(404, "Page not found");
        }
        return R.ok(page.getRecords());
    }

    /**
     * 新增记录
     *
     * @param entity 记录
     * @return 新增结果
     */
    @PostMapping("/insert")
    public R<String> insert(@RequestBody T entity) {
        boolean result = service.save(entity);
        if (!result) {
            return R.error(400, "Insert failed");
        }
        return R.ok("Insert successful");
    }

    /**
     * 修改记录
     *
     * @param entity 记录
     * @return 修改结果
     */
    @PutMapping("/update")
    public R<String> update(@RequestBody T entity) {
        boolean result = service.updateById(entity);
        if (!result) {
            return R.error(400, "Update failed");
        }
        return R.ok("Update successful");
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable String id) {
        boolean result = service.removeById(id);
        if (!result) {
            return R.error(400, "Delete failed");
        }
        return R.ok("Delete successful");
    }

    /**
     * 导出Excel
     *
     * @param entity 查询条件
     * @return 导出结果
     */
    @PostMapping("/exportExcel")
    public R<String> exportExcel(@RequestBody T entity, HttpServletResponse response) {
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        try {
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private QueryWrapper<T> buildQueryWrapper(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (entity == null) {
            return queryWrapper;
        }
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value != null && !"".equals(value)) {
                    // 将字段名转换为下划线格式
                    String columnName = StringUtils.camelToUnderline(field.getName());
                    if (value instanceof String) {
                        String strValue = ((String) value).trim();
                        if (StringUtils.isNotEmpty(strValue)) {
                            queryWrapper.like(columnName, strValue);
                        }
                    } else if (value instanceof Collection) {
                        Collection<?> collection = (Collection<?>) value;
                        if (CollectionUtils.isNotEmpty(collection)) {
                            queryWrapper.in(columnName, collection);
                        }
                    } else {
                        queryWrapper.eq(columnName, value);
                    }
                }
            } catch (IllegalAccessException e) {
                Log.warn("反射获取字段值失败: {}", field.getName(), e);
            }
        }
        return queryWrapper;
    }
}
