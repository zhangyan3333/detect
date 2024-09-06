package com.bjj.detect.serviceimpl;

import com.bjj.detect.dao.DetectMeterDao;
import com.bjj.detect.entity.DetectMeter;
import com.bjj.detect.service.DetectMeterService;
import com.syzx.framework.dao.condition.ConditionFactory;
import com.syzx.framework.dao.condition.IQueryCondition;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.query.QueryResult;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DetectMeter服务接口实现 <br/>
 * <p>
 * CreateTime 2024/09/03 22:48
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class DetectMeterServiceImpl implements DetectMeterService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private DetectMeterDao detectMeterDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param detectMeter 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DetectMeter detectMeter) {
        detectMeterDao.insert(detectMeter);
    }

    /**
     * 插入多个实体.
     * @param detectMeters 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<DetectMeter> detectMeters) {
        detectMeterDao.insertAll(detectMeters);
    }

    /**
     * 更新单个实体.
     * @param detectMeter 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DetectMeter detectMeter) {
        detectMeterDao.update(detectMeter);
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        detectMeterDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DetectMeter> get() {
        return detectMeterDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DetectMeter getById(long id) {
        return detectMeterDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DetectMeter> getByConditions(IQueryCondition... conditions) {
        return detectMeterDao.getByQuery(ConditionFactory.buildSimpleQuery(DetectMeter.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DetectMeter> getByQuery(IEntityQuery entityQuery) {
        return detectMeterDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<DetectMeter> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<DetectMeter> result = new QueryResult<>();
        result.setEntities(detectMeterDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(detectMeterDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }


    //</editor-fold>

}
