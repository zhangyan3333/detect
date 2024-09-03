package com.bjj.detect.controller;

import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.query.PgRecordQuery;
import com.bjj.detect.service.PgRecordService;
import com.bjj.detect.util.DataTransfer;
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
 * PgRecord控制器 <br/>
 * <p>
 * CreateTime 2024/07/10 01:44
 *
 * @version 1.0.0
 * @author 代码生成器
 */
@RequestMapping("/api/pgRecords")
@RestController
public class PgRecordController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private PgRecordService pgRecordService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param pgRecord 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<PgRecord> insert(@RequestBody PgRecord pgRecord) {
        pgRecordService.insert(pgRecord);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", pgRecord);
    }

    /**
     * 修改单个实体.
     * @param pgRecord 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<PgRecord> update(@RequestBody PgRecord pgRecord) {
        pgRecordService.update(pgRecord);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", pgRecord);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<PgRecord> delete(@PathVariable long id) {
        pgRecordService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<PgRecord> getById(@PathVariable long id) {
        PgRecord pgRecord = pgRecordService.getById(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", pgRecord);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<PgRecord>> get() {
        List<PgRecord> list = pgRecordService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param pgRecordQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<PgRecord>> getByQuery(@RequestBody PgRecordQuery pgRecordQuery) {
        List<PgRecord> list = pgRecordService.getByQuery(pgRecordQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param pgRecordQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/page")
    public ApiResult<QueryResult<PgRecord>> pageByQuery(@RequestBody PgRecordQuery pgRecordQuery) {
        QueryResult<PgRecord> list = pgRecordService.pageByQuery(pgRecordQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    //</editor-fold>

    @Autowired
    private DataTransfer dataTransfer;

    @PostMapping(value = "/print")
    public ApiResult<QueryResult<PgRecord>> printWord(@RequestBody PgRecordQuery pgRecordQuery) {
        pgRecordService.printWord("");
        return new ApiResult<>(ApiResultCode.Success, "获取成功", null);
    }

    @PostMapping(value = "/refreshDetectData")
    public ApiResult<QueryResult<PgRecord>> printWord() {
        dataTransfer.detectRecordSqlToMysql();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", null);
    }

}
