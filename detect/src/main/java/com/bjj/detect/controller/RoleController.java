package com.bjj.detect.controller;

import com.bjj.detect.entity.Role;
import com.bjj.detect.service.RoleService;
import com.bjj.detect.query.RoleQuery;
import com.syzx.framework.controller.ApiResult;
import com.syzx.framework.controller.ApiResultCode;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.syzx.framework.query.QueryResult;

/**
 * Role控制器 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private RoleService roleService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param role 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<Role> insert(@RequestBody Role role) {
        roleService.insert(role);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", role);
    }

    /**
     * 修改单个实体.
     * @param role 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<Role> update(@RequestBody Role role) {
        roleService.update(role);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", role);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<Role> delete(@PathVariable long id) {
        roleService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<Role> getById(@PathVariable long id) {
        Role role = roleService.getById(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", role);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<Role>> get() {
        List<Role> list = roleService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param roleQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<Role>> getByQuery(@RequestBody RoleQuery roleQuery) {
        List<Role> list = roleService.getByQuery(roleQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param roleQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/page")
    public ApiResult<QueryResult<Role>> pageByQuery(@RequestBody RoleQuery roleQuery) {
        QueryResult<Role> list = roleService.pageByQuery(roleQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    //</editor-fold>

}
