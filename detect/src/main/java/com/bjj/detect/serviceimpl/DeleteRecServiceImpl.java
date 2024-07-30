package com.bjj.detect.serviceimpl;

import com.bjj.detect.entity.DeleteRec;
import com.bjj.detect.service.DeleteRecService;
import com.bjj.detect.dao.DeleteRecDao;
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
 * DeleteRec服务接口实现 <br/>
 * <p>
 * CreateTime 2024/07/30 13:47
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class DeleteRecServiceImpl implements DeleteRecService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private DeleteRecDao deleteRecDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param deleteRec 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DeleteRec deleteRec) {
        deleteRecDao.insert(deleteRec);
    }

    /**
     * 插入多个实体.
     * @param deleteRecs 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<DeleteRec> deleteRecs) {
        deleteRecDao.insertAll(deleteRecs);
    }

    /**
     * 更新单个实体.
     * @param deleteRec 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DeleteRec deleteRec) {
        deleteRecDao.update(deleteRec);
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        deleteRecDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DeleteRec> get() {
        return deleteRecDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteRec getById(long id) {
        return deleteRecDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DeleteRec> getByConditions(IQueryCondition... conditions) {
        return deleteRecDao.getByQuery(ConditionFactory.buildSimpleQuery(DeleteRec.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DeleteRec> getByQuery(IEntityQuery entityQuery) {
        return deleteRecDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<DeleteRec> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<DeleteRec> result = new QueryResult<>();
        result.setEntities(deleteRecDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(deleteRecDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }


    //</editor-fold>

}
