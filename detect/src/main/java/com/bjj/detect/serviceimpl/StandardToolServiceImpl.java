package com.bjj.detect.serviceimpl;

import com.bjj.detect.dao.StandardToolDao;
import com.bjj.detect.entity.StandardTool;
import com.bjj.detect.service.StandardToolService;
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
 * StandardTool服务接口实现 <br/>
 * <p>
 * CreateTime 2024/07/30 11:42
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class StandardToolServiceImpl implements StandardToolService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private StandardToolDao standardToolDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param standardTool 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(StandardTool standardTool) {
        standardToolDao.insert(standardTool);
    }

    /**
     * 插入多个实体.
     * @param standardTools 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<StandardTool> standardTools) {
        standardToolDao.insertAll(standardTools);
    }

    /**
     * 更新单个实体.
     * @param standardTool 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StandardTool standardTool) {
        standardToolDao.update(standardTool);
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        standardToolDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StandardTool> get() {
        return standardToolDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StandardTool getById(long id) {
        return standardToolDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StandardTool> getByConditions(IQueryCondition... conditions) {
        return standardToolDao.getByQuery(ConditionFactory.buildSimpleQuery(StandardTool.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StandardTool> getByQuery(IEntityQuery entityQuery) {
        return standardToolDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<StandardTool> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<StandardTool> result = new QueryResult<>();
        result.setEntities(standardToolDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(standardToolDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }


    //</editor-fold>

}
