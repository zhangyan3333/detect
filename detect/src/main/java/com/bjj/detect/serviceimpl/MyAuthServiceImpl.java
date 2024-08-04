package com.bjj.detect.serviceimpl;

import com.bjj.detect.dao.MyAuthDao;
import com.bjj.detect.entity.MyAuth;
import com.bjj.detect.service.MyAuthService;
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
 * MyAuth服务接口实现 <br/>
 * <p>
 * CreateTime 2024/08/01 19:20
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class MyAuthServiceImpl implements MyAuthService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private MyAuthDao myAuthDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param myAuth 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(MyAuth myAuth) {
        myAuthDao.insert(myAuth);
    }

    /**
     * 插入多个实体.
     * @param myAuths 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<MyAuth> myAuths) {
        myAuthDao.insertAll(myAuths);
    }

    /**
     * 更新单个实体.
     * @param myAuth 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MyAuth myAuth) {
        myAuthDao.update(myAuth);
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        myAuthDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MyAuth> get() {
        return myAuthDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyAuth getById(long id) {
        return myAuthDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MyAuth> getByConditions(IQueryCondition... conditions) {
        return myAuthDao.getByQuery(ConditionFactory.buildSimpleQuery(MyAuth.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MyAuth> getByQuery(IEntityQuery entityQuery) {
        return myAuthDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<MyAuth> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<MyAuth> result = new QueryResult<>();
        result.setEntities(myAuthDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(myAuthDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }


    //</editor-fold>

}
