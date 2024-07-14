package com.bjj.detect.serviceimpl;

import com.bjj.detect.dao.StandardMeterDao;
import com.bjj.detect.entity.StandardMeter;
import com.bjj.detect.service.StandardMeterService;
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
 * StandardMeter服务接口实现 <br/>
 * <p>
 * CreateTime 2024/07/14 22:11
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class StandardMeterServiceImpl implements StandardMeterService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private StandardMeterDao standardMeterDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param standardMeter 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(StandardMeter standardMeter) {
        standardMeterDao.insert(standardMeter);
    }

    /**
     * 插入多个实体.
     * @param standardMeters 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<StandardMeter> standardMeters) {
        standardMeterDao.insertAll(standardMeters);
    }

    /**
     * 更新单个实体.
     * @param standardMeter 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StandardMeter standardMeter) {
        standardMeterDao.update(standardMeter);
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        standardMeterDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StandardMeter> get() {
        return standardMeterDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StandardMeter getById(long id) {
        return standardMeterDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StandardMeter> getByConditions(IQueryCondition... conditions) {
        return standardMeterDao.getByQuery(ConditionFactory.buildSimpleQuery(StandardMeter.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StandardMeter> getByQuery(IEntityQuery entityQuery) {
        return standardMeterDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<StandardMeter> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<StandardMeter> result = new QueryResult<>();
        result.setEntities(standardMeterDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(standardMeterDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }


    //</editor-fold>

}
