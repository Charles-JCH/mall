package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.vo.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<S extends IService<T>, T> {

    protected final S service;

    protected BaseController(S service) {
        this.service = service;
    }

    /**
     * 分页查询
     *
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 分页结果
     */
    @GetMapping("/pageQuery")
    public R<List<T>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<T> page = service.page(new Page<>(pageNum, pageSize));
        if (page == null) {
            return R.error(404, "Page not found");
        }
        return R.ok(page.getRecords());
    }

    /**
     * 查询所有记录
     *
     * @return 所有记录
     */
    @GetMapping
    public R<List<T>> selectAll() {
        List<T> list = service.list();
        if (list == null) {
            return R.error(404, "Record not found");
        }
        return R.ok(list);
    }

    /**
     * 根据ID查询记录
     *
     * @param id 记录ID
     * @return 记录
     */
    @GetMapping("/{id}")
    public R<T> selectById(@PathVariable Integer id) {
        T t = service.getById(id);
        if (t == null) {
            return R.error(404, "Record not found");
        }
        return R.ok(t);
    }

    /**
     * 新增记录
     *
     * @param entity 记录
     * @return 新增结果
     */
    @PostMapping
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
    @PutMapping
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
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable String id) {
        boolean result = service.removeById(id);
        if (!result) {
            return R.error(400, "Delete failed");
        }
        return R.ok("Delete successful");
    }
}
