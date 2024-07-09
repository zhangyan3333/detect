package com.bjj.detect.serviceimpl;

import com.bjj.detect.entity.PgInfo;
import com.bjj.detect.service.PgInfoService;
import com.bjj.detect.dao.PgInfoDao;
import com.syzx.framework.dao.condition.ConditionFactory;
import java.util.List;
import java.util.ArrayList;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.dao.condition.IQueryCondition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.syzx.framework.query.QueryResult;

/**
 * PgInfo服务接口实现 <br/>
 * <p>
 * CreateTime 2024/07/10 01:44
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class PgInfoServiceImpl implements PgInfoService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private PgInfoDao pgInfoDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param pgInfo 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(PgInfo pgInfo) {
        pgInfoDao.insert(pgInfo);
    }

    /**
     * 插入多个实体.
     * @param pgInfoes 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<PgInfo> pgInfoes) {
        pgInfoDao.insertAll(pgInfoes);
    }

    /**
     * 更新单个实体.
     * @param pgInfo 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PgInfo pgInfo) {
        pgInfoDao.update(pgInfo);
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        pgInfoDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PgInfo> get() {
        return pgInfoDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PgInfo getById(long id) {
        return pgInfoDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PgInfo> getByConditions(IQueryCondition... conditions) {
        return pgInfoDao.getByQuery(ConditionFactory.buildSimpleQuery(PgInfo.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PgInfo> getByQuery(IEntityQuery entityQuery) {
        return pgInfoDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<PgInfo> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<PgInfo> result = new QueryResult<>();
        result.setEntities(pgInfoDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(pgInfoDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }


    //</editor-fold>

}
