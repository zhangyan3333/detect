package com.bjj.detect.serviceimpl;

import com.bjj.detect.dao.PgRecordDao;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.service.PgRecordService;
import com.bjj.detect.util.DataTransfer;
import com.bjj.detect.util.RunnerSelf;
import com.syzx.framework.dao.condition.ConditionFactory;
import com.syzx.framework.dao.condition.IQueryCondition;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.query.QueryResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.syzx.framework.utils.PrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PgRecord服务接口实现 <br/>
 * <p>
 * CreateTime 2024/07/10 01:44
 *
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
@Slf4j
public class PgRecordServiceImpl implements PgRecordService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private PgRecordDao pgRecordDao;


    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param pgRecord 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(PgRecord pgRecord) {
        pgRecordDao.insert(pgRecord);
    }

    /**
     * 插入多个实体.
     * @param pgRecords 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<PgRecord> pgRecords) {
        pgRecordDao.insertAll(pgRecords);
    }

    /**
     * 更新单个实体.
     * @param pgRecord 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PgRecord pgRecord) {
        pgRecordDao.update(pgRecord);
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        pgRecordDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PgRecord> get() {
        return pgRecordDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PgRecord getById(long id) {
        return pgRecordDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PgRecord> getByConditions(IQueryCondition... conditions) {
        return pgRecordDao.getByQuery(ConditionFactory.buildSimpleQuery(PgRecord.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PgRecord> getByQuery(IEntityQuery entityQuery) {
        return pgRecordDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<PgRecord> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<PgRecord> result = new QueryResult<>();
        result.setEntities(pgRecordDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(pgRecordDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }


    //</editor-fold>

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @Autowired
    private DataTransfer dataTransfer;


    /**
     * @param :
     * @return: void
     * @description:  每隔 一定时间进行一次同步
     * @author: zhangyan
     * @date: 2024/7/14 1:21
    **/
    @Scheduled(fixedRate = 1800000)
    public void detectRecordSync(){
        dataTransfer.detectRecordSqlToMysql();
        PrintUtil.info("自动同步数据"+ "条--[" + sdf.format(new Date()) + "]", new Object[0]);
    }

}
