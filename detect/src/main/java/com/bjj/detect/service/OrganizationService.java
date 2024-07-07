package com.bjj.detect.service;

import com.bjj.detect.entity.Organization;
import java.util.List;
import com.syzx.framework.dao.condition.IQueryCondition;
import com.syzx.framework.query.IEntityQuery;

/**
 * Organization服务接口 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
public interface OrganizationService {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param organization 需要插入的实体
     */
    void insert(Organization organization);

    /**
     * 更新单个实体.
     * @param organization 需要更新的实体
     */
    void update(Organization organization);


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    void deleteById(long id);

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    List<Organization> get();
    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    Organization getById(long id);
    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    List<Organization> getByConditions(IQueryCondition... conditions);
    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    List<Organization> getByQuery(IEntityQuery entityQuery);
    /**
     * 获取指定ID的实体及其子孙.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体及其子孙
     */
    Organization getByIdWithChildren(long id);
    /**
     * 获取指定父节点的一代子节点.
     * @param parentId 父节点Id
     * @return 指定父节点的一代子节点集合
     */
    List<Organization> getChildren(long parentId);

    //</editor-fold>

}
