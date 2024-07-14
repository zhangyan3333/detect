package com.bjj.detect.service;

import com.bjj.detect.entity.StandardMeter;
import com.syzx.framework.dao.condition.IQueryCondition;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.query.QueryResult;
import java.util.List;

/**
 * StandardMeter服务接口 <br/>
 * <p>
 * CreateTime 2024/07/14 22:11
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
public interface StandardMeterService {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param standardMeter 需要插入的实体
     */
    void insert(StandardMeter standardMeter);
    /**
     * 插入多个实体.
     * @param standardMeters 需要插入的实体集合
     */
    void insertAll(List<StandardMeter> standardMeters);

    /**
     * 更新单个实体.
     * @param standardMeter 需要更新的实体
     */
    void update(StandardMeter standardMeter);


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    void deleteById(long id);

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    List<StandardMeter> get();
    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    StandardMeter getById(long id);
    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    List<StandardMeter> getByConditions(IQueryCondition... conditions);
    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    List<StandardMeter> getByQuery(IEntityQuery entityQuery);
    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    QueryResult<StandardMeter> pageByQuery(IEntityQuery entityQuery);

    //</editor-fold>

}
