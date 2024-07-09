package com.bjj.detect.controller;

import com.bjj.detect.entity.PgNotice;
import com.bjj.detect.service.PgNoticeService;
import com.bjj.detect.query.PgNoticeQuery;
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
 * PgNotice控制器 <br/>
 * <p>
 * CreateTime 2024/07/10 01:44
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@RestController
@RequestMapping("/api/pgNotices")
public class PgNoticeController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private PgNoticeService pgNoticeService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param pgNotice 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<PgNotice> insert(@RequestBody PgNotice pgNotice) {
        pgNoticeService.insert(pgNotice);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", pgNotice);
    }

    /**
     * 修改单个实体.
     * @param pgNotice 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<PgNotice> update(@RequestBody PgNotice pgNotice) {
        pgNoticeService.update(pgNotice);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", pgNotice);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<PgNotice> delete(@PathVariable long id) {
        pgNoticeService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<PgNotice> getById(@PathVariable long id) {
        PgNotice pgNotice = pgNoticeService.getById(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", pgNotice);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<PgNotice>> get() {
        List<PgNotice> list = pgNoticeService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param pgNoticeQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<PgNotice>> getByQuery(@RequestBody PgNoticeQuery pgNoticeQuery) {
        List<PgNotice> list = pgNoticeService.getByQuery(pgNoticeQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param pgNoticeQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/page")
    public ApiResult<QueryResult<PgNotice>> pageByQuery(@RequestBody PgNoticeQuery pgNoticeQuery) {
        QueryResult<PgNotice> list = pgNoticeService.pageByQuery(pgNoticeQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    //</editor-fold>

}
