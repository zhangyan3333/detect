package com.bjj.detect.controller;

import com.bjj.detect.entity.StandardTool;
import com.bjj.detect.query.StandardToolQuery;
import com.bjj.detect.service.StandardToolService;
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
 * StandardTool控制器 <br/>
 * <p>
 * CreateTime 2024/07/30 11:42
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@RequestMapping("/api/standardTools")
@RestController
public class StandardToolController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private StandardToolService standardToolService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param standardTool 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<StandardTool> insert(@RequestBody StandardTool standardTool) {
        standardToolService.insert(standardTool);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", standardTool);
    }

    /**
     * 修改单个实体.
     * @param standardTool 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<StandardTool> update(@RequestBody StandardTool standardTool) {
        standardToolService.update(standardTool);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", standardTool);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<StandardTool> delete(@PathVariable long id) {
        standardToolService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<StandardTool> getById(@PathVariable long id) {
        StandardTool standardTool = standardToolService.getById(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", standardTool);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<StandardTool>> get() {
        List<StandardTool> list = standardToolService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param standardToolQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<StandardTool>> getByQuery(@RequestBody StandardToolQuery standardToolQuery) {
        List<StandardTool> list = standardToolService.getByQuery(standardToolQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param standardToolQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/page")
    public ApiResult<QueryResult<StandardTool>> pageByQuery(@RequestBody StandardToolQuery standardToolQuery) {
        QueryResult<StandardTool> list = standardToolService.pageByQuery(standardToolQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    //</editor-fold>

}
