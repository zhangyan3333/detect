package com.bjj.detect.serviceimpl;

import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.service.PgCertificateService;
import com.bjj.detect.dao.PgCertificateDao;
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
 * PgCertificate服务接口实现 <br/>
 * <p>
 * CreateTime 2024/07/10 01:44
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class PgCertificateServiceImpl implements PgCertificateService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private PgCertificateDao pgCertificateDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param pgCertificate 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(PgCertificate pgCertificate) {
        pgCertificateDao.insert(pgCertificate);
    }

    /**
     * 插入多个实体.
     * @param pgCertificates 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<PgCertificate> pgCertificates) {
        pgCertificateDao.insertAll(pgCertificates);
    }

    /**
     * 更新单个实体.
     * @param pgCertificate 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PgCertificate pgCertificate) {
        pgCertificateDao.update(pgCertificate);
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        pgCertificateDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PgCertificate> get() {
        return pgCertificateDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PgCertificate getById(long id) {
        return pgCertificateDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PgCertificate> getByConditions(IQueryCondition... conditions) {
        return pgCertificateDao.getByQuery(ConditionFactory.buildSimpleQuery(PgCertificate.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PgCertificate> getByQuery(IEntityQuery entityQuery) {
        return pgCertificateDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<PgCertificate> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<PgCertificate> result = new QueryResult<>();
        result.setEntities(pgCertificateDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(pgCertificateDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }


    //</editor-fold>

}
