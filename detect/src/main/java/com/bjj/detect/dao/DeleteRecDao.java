package com.bjj.detect.dao;

import com.bjj.detect.entity.DeleteRec;
import java.util.List;
import java.util.Map;

/**
 * DeleteRec仓储接口 <br/>
 * <p>
 * CreateTime 2024/07/30 13:47
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
public interface DeleteRecDao {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param deleteRec 需要插入的实体
     */
    void insert(DeleteRec deleteRec);
    /**
     * 插入多个实体.
     * @param deleteRecs 需要插入的实体集合
     */
    void insertAll(List<DeleteRec> deleteRecs);

    /**
     * 更新单个实体.
     * @param deleteRec 需要更新的实体
     */
    void update(DeleteRec deleteRec);
    /**
     * 更新多个实体.
     * @param deleteRecs 需要更新的实体集合
     */
    void updateAll(List<DeleteRec> deleteRecs);

    /**
     * 插入或更新单个实体(若实体不存在，则插入实体；若实体存在,在则更新实体).
     * @param deleteRec 需要插入或更新的实体
     */
    void insertOrUpdate(DeleteRec deleteRec);
    /**
     * 插入或更新多个实体(若实体不存在，则插入实体；若实体存在,在则更新实体).
     * @param deleteRecs 需要插入或更新的实体集合
     */
    void insertOrUpdateAll(List<DeleteRec> deleteRecs);

    /**
     * 删除单个实体.
     * @param deleteRec 需要删除的实体
     */
    void delete(DeleteRec deleteRec);
    /**
     * 删除多个实体.
     * @param deleteRecs 需要删除的实体集合
     */
    void deleteAll(List<DeleteRec> deleteRecs);
    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    void deleteById(long id);
    /**
     * 删除指定Id的实体集合.
     * @param ids 需要删除的实体Id集合
     */
    void deleteAllByIds(List<Long> ids);
    /**
     * 删除符合条件的实体集合.
     * @param query 删除条件集合
     */
    void deleteByQuery(Map<String, Object> query);
    /**
     * 删除属性匹配的实体集合.
     * @param propertyName 匹配属性名称
     * @param value 匹配属性值
     */
    void deleteByProperty(String propertyName, Object value);

    /**
     * 获取实体总数.
     * @return 实体总数
     */
    int count();
    /**
     * 获取属性匹配的实体总数.
     * @param propertyName 匹配属性名称
     * @param value 匹配属性值
     * @return 属性匹配的实体总数
     */
    int countByProperty(String propertyName, Object value);
    /**
     * 获取符合查询条件的实体总数.
     * @param query 查询条件集合
     * @return 符合查询条件的实体总数
     */
    int countByQuery(Map<String, Object> query);

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    List<DeleteRec> get();
    /**
     * 获取属性匹配的实体集合.
     * @param propertyName 匹配属性名称
     * @param value 匹配属性值
     * @return 属性匹配的实体集合
     */
    List<DeleteRec> getByProperty(String propertyName, Object value);
    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    DeleteRec getById(long id);
    /**
     * 获取符合查询条件的实体集合.
     * @param query 查询条件集合
     * @return 符合查询条件的实体集合
     */
    List<DeleteRec> getByQuery(Map<String, Object> query);

    //</editor-fold>

}
