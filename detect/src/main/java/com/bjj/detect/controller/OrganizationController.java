package com.bjj.detect.controller;

import com.bjj.detect.entity.Organization;
import com.bjj.detect.service.OrganizationService;
import com.bjj.detect.query.OrganizationQuery;
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

/**
 * Organization控制器 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private OrganizationService organizationService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param organization 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<Organization> insert(@RequestBody Organization organization) {
        organizationService.insert(organization);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", organization);
    }

    /**
     * 修改单个实体.
     * @param organization 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<Organization> update(@RequestBody Organization organization) {
        organizationService.update(organization);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", organization);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<Organization> delete(@PathVariable long id) {
        organizationService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体及其子孙.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<Organization> getByIdWithChildren(@PathVariable long id) {
        Organization organization = organizationService.getByIdWithChildren(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", organization);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<Organization>> get() {
        List<Organization> list = organizationService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param organizationQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<Organization>> getByQuery(@RequestBody OrganizationQuery organizationQuery) {
        List<Organization> list = organizationService.getByQuery(organizationQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param parentId 父节点Id
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/{parentId}/children")
    public ApiResult<List<Organization>> getChildren(@PathVariable long parentId) {
        List<Organization> list = organizationService.getChildren(parentId);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    //</editor-fold>

}
