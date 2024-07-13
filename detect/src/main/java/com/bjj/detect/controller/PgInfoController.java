package com.bjj.detect.controller;

import com.bjj.detect.entity.PgInfo;
import com.bjj.detect.query.PgInfoQuery;
import com.bjj.detect.service.PgInfoService;
import com.syzx.framework.controller.ApiResult;
import com.syzx.framework.controller.ApiResultCode;
import com.syzx.framework.query.QueryResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PgInfo控制器 <br/>
 * <p>
 * CreateTime 2024/07/10 01:44
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@RequestMapping("/api/pgInfoes")
@RestController
public class PgInfoController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private PgInfoService pgInfoService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param pgInfo 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<PgInfo> insert(@RequestBody PgInfo pgInfo) {
        pgInfoService.insert(pgInfo);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", pgInfo);
    }

    /**
     * 修改单个实体.
     * @param pgInfo 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<PgInfo> update(@RequestBody PgInfo pgInfo) {
        pgInfoService.update(pgInfo);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", pgInfo);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<PgInfo> delete(@PathVariable long id) {
        pgInfoService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<PgInfo> getById(@PathVariable long id) {
        PgInfo pgInfo = pgInfoService.getById(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", pgInfo);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<PgInfo>> get() {
        List<PgInfo> list = pgInfoService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param pgInfoQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<PgInfo>> getByQuery(@RequestBody PgInfoQuery pgInfoQuery) {
        List<PgInfo> list = pgInfoService.getByQuery(pgInfoQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param pgInfoQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/page")
    public ApiResult<QueryResult<PgInfo>> pageByQuery(@RequestBody PgInfoQuery pgInfoQuery) {
        QueryResult<PgInfo> list = pgInfoService.pageByQuery(pgInfoQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    //</editor-fold>

}
