package com.bjj.detect.service;

import com.bjj.detect.entity.UserInfo;
import java.util.List;
import com.syzx.framework.dao.condition.IQueryCondition;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.query.QueryResult;
import com.syzx.framework.dto.Password;

/**
 * UserInfo服务接口 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
public interface UserInfoService {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param userInfo 需要插入的实体
     */
    void insert(UserInfo userInfo);
    /**
     * 插入多个实体.
     * @param userInfoes 需要插入的实体集合
     */
    void insertAll(List<UserInfo> userInfoes);

    /**
     * 更新单个实体.
     * @param userInfo 需要更新的实体
     */
    void update(UserInfo userInfo);
    /**
     * 更新密码.
     * @param password 需要更新的密码
     */
    void updatePassword(Password password);
    /**
     * 更新名字和头像.
     * @param userInfo 需要更新的名字和头像
     */
    void updateNameAndAvatar(UserInfo userInfo);


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    void deleteById(long id);

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    List<UserInfo> get();
    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    UserInfo getById(long id);
    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    List<UserInfo> getByConditions(IQueryCondition... conditions);
    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    List<UserInfo> getByQuery(IEntityQuery entityQuery);
    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    QueryResult<UserInfo> pageByQuery(IEntityQuery entityQuery);

    //</editor-fold>

}
