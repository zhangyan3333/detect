package com.bjj.detect.controller;

import com.bjj.detect.entity.DeleteRec;
import com.bjj.detect.service.DeleteRecService;
import com.bjj.detect.query.DeleteRecQuery;
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
 * DeleteRec控制器 <br/>
 * <p>
 * CreateTime 2024/07/30 13:47
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@RestController
@RequestMapping("/api/deleteRecs")
public class DeleteRecController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private DeleteRecService deleteRecService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param deleteRec 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<DeleteRec> insert(@RequestBody DeleteRec deleteRec) {
        deleteRecService.insert(deleteRec);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", deleteRec);
    }

    /**
     * 修改单个实体.
     * @param deleteRec 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<DeleteRec> update(@RequestBody DeleteRec deleteRec) {
        deleteRecService.update(deleteRec);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", deleteRec);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<DeleteRec> delete(@PathVariable long id) {
        deleteRecService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<DeleteRec> getById(@PathVariable long id) {
        DeleteRec deleteRec = deleteRecService.getById(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", deleteRec);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<DeleteRec>> get() {
        List<DeleteRec> list = deleteRecService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param deleteRecQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<DeleteRec>> getByQuery(@RequestBody DeleteRecQuery deleteRecQuery) {
        List<DeleteRec> list = deleteRecService.getByQuery(deleteRecQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param deleteRecQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/page")
    public ApiResult<QueryResult<DeleteRec>> pageByQuery(@RequestBody DeleteRecQuery deleteRecQuery) {
        QueryResult<DeleteRec> list = deleteRecService.pageByQuery(deleteRecQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    //</editor-fold>

}
