package com.bjj.detect.service;

import com.bjj.detect.entity.DetectRecord;
import com.bjj.detect.entity.VO.DetectRecordVO;
import com.syzx.framework.dao.condition.IQueryCondition;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.query.QueryResult;
import java.util.List;

/**
 * DetectRecord服务接口 <br/>
 * <p>
 * CreateTime 2024/09/03 22:48
 *
 * @version 1.0.0
 * @author 代码生成器
 */
public interface DetectRecordService {

    QueryResult<DetectRecordVO> pageByQuery(IEntityQuery entityQuery);
    DetectRecordVO changeToVo(DetectRecord detectRecord);
    DetectRecord changeToPo(DetectRecordVO detectRecordVO);

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param detectRecord 需要插入的实体
     */
    void insert(DetectRecord detectRecord);
    /**
     * 插入多个实体.
     * @param detectRecords 需要插入的实体集合
     */
    void insertAll(List<DetectRecord> detectRecords);

    /**
     * 更新单个实体.
     * @param detectRecord 需要更新的实体
     */
    void update(DetectRecord detectRecord);


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    void deleteById(long id);

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    List<DetectRecord> get();
    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    DetectRecord getById(long id);
    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    List<DetectRecord> getByConditions(IQueryCondition... conditions);
    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    List<DetectRecord> getByQuery(IEntityQuery entityQuery);
    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
//    QueryResult<DetectRecord> pageByQuery(IEntityQuery entityQuery);

    //</editor-fold>

}
