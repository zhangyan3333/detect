package com.bjj.detect.serviceimpl;

import com.bjj.detect.dao.GitTestDao;
import com.bjj.detect.entity.GitTest;
import com.bjj.detect.service.GitTestService;
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
 * GitTest服务接口实现 <br/>
 * <p>
 * CreateTime 2024/09/03 21:21
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class GitTestServiceImpl implements GitTestService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private GitTestDao gitTestDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param gitTest 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(GitTest gitTest) {
        gitTestDao.insert(gitTest);
    }

    /**
     * 插入多个实体.
     * @param gitTests 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<GitTest> gitTests) {
        gitTestDao.insertAll(gitTests);
    }

    /**
     * 更新单个实体.
     * @param gitTest 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GitTest gitTest) {
        gitTestDao.update(gitTest);
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        gitTestDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GitTest> get() {
        return gitTestDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GitTest getById(long id) {
        return gitTestDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GitTest> getByConditions(IQueryCondition... conditions) {
        return gitTestDao.getByQuery(ConditionFactory.buildSimpleQuery(GitTest.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GitTest> getByQuery(IEntityQuery entityQuery) {
        return gitTestDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<GitTest> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<GitTest> result = new QueryResult<>();
        result.setEntities(gitTestDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(gitTestDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }


    //</editor-fold>

}
