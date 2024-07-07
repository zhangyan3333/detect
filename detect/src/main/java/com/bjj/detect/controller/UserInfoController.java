package com.bjj.detect.controller;

import com.bjj.detect.entity.UserInfo;
import com.bjj.detect.service.UserInfoService;
import com.bjj.detect.query.UserInfoQuery;
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
import com.syzx.framework.dto.Password;

/**
 * UserInfo控制器 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@RestController
@RequestMapping("/api/userInfoes")
public class UserInfoController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private UserInfoService userInfoService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param userInfo 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<UserInfo> insert(@RequestBody UserInfo userInfo) {
        userInfoService.insert(userInfo);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", userInfo);
    }

    /**
     * 修改单个实体.
     * @param userInfo 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<UserInfo> update(@RequestBody UserInfo userInfo) {
        userInfoService.update(userInfo);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", userInfo);
    }

    /**
     * 更新密码.
     * @param password 需要更新的密码
     * @return 服务器统一api回复
     */
    @PutMapping(value = "/password")
    public ApiResult<UserInfo> updatePassword(@RequestBody Password password) {
        userInfoService.updatePassword(password);
        return new ApiResult<>(ApiResultCode.Success, "更新成功", null);
    }

    /**
     * 更新名字和头像.
     * @param userInfo 需要更新的名字和头像
     * @return 服务器统一api回复
     */
    @PutMapping(value = "/info")
    public ApiResult<UserInfo> updateNameAndAvatar(@RequestBody UserInfo userInfo) {
        userInfoService.updateNameAndAvatar(userInfo);
        return new ApiResult<>(ApiResultCode.Success, "更新成功", null);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<UserInfo> delete(@PathVariable long id) {
        userInfoService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<UserInfo> getById(@PathVariable long id) {
        UserInfo userInfo = userInfoService.getById(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", userInfo);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<UserInfo>> get() {
        List<UserInfo> list = userInfoService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param userInfoQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<UserInfo>> getByQuery(@RequestBody UserInfoQuery userInfoQuery) {
        List<UserInfo> list = userInfoService.getByQuery(userInfoQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param userInfoQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/page")
    public ApiResult<QueryResult<UserInfo>> pageByQuery(@RequestBody UserInfoQuery userInfoQuery) {
        QueryResult<UserInfo> list = userInfoService.pageByQuery(userInfoQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    //</editor-fold>

}
