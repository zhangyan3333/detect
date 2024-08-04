package com.bjj.detect.controller;

import com.bjj.detect.entity.MyAuth;
import com.bjj.detect.query.MyAuthQuery;
import com.bjj.detect.service.MyAuthService;
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
 * MyAuth控制器 <br/>
 * <p>
 * CreateTime 2024/08/01 19:20
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@RequestMapping("/api/myAuths")
@RestController
public class MyAuthController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private MyAuthService myAuthService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param myAuth 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<MyAuth> insert(@RequestBody MyAuth myAuth) {
        myAuthService.insert(myAuth);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", myAuth);
    }

    /**
     * 修改单个实体.
     * @param myAuth 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<MyAuth> update(@RequestBody MyAuth myAuth) {
        myAuthService.update(myAuth);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", myAuth);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<MyAuth> delete(@PathVariable long id) {
        myAuthService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<MyAuth> getById(@PathVariable long id) {
        MyAuth myAuth = myAuthService.getById(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", myAuth);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<MyAuth>> get() {
        List<MyAuth> list = myAuthService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param myAuthQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<MyAuth>> getByQuery(@RequestBody MyAuthQuery myAuthQuery) {
        List<MyAuth> list = myAuthService.getByQuery(myAuthQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param myAuthQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/page")
    public ApiResult<QueryResult<MyAuth>> pageByQuery(@RequestBody MyAuthQuery myAuthQuery) {
        QueryResult<MyAuth> list = myAuthService.pageByQuery(myAuthQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    //</editor-fold>

}
