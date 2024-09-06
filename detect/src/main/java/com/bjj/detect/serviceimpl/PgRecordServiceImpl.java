package com.bjj.detect.serviceimpl;

import com.bjj.detect.dao.PgInfoDao;
import com.bjj.detect.dao.PgRecordDao;
import com.bjj.detect.entity.PgInfo;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.service.PgRecordService;
import com.bjj.detect.util.DataTransfer;
import com.bjj.detect.util.RunnerSelf;
import com.syzx.framework.dao.condition.ConditionFactory;
import com.syzx.framework.dao.condition.IQueryCondition;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.query.QueryResult;
import com.syzx.framework.utils.PrintUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
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
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void update(PgRecord pgRecord) {
//        pgRecordDao.update(pgRecord);
//    }


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
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public QueryResult<PgRecord> pageByQuery(IEntityQuery entityQuery) {
//        QueryResult<PgRecord> result = new QueryResult<>();
//        result.setEntities(pgRecordDao.getByQuery(entityQuery.getQueryMap()));
//        result.setCount(pgRecordDao.countByQuery(entityQuery.getCountMap()));
//        return result;
//    }


    //</editor-fold>

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    @Autowired
    private DataTransfer dataTransfer;
    @Autowired
    private PgInfoDao pgInfoDao;
    /**
     * @param :
     * @return: void
     * @description:  每隔 一定时间进行一次同步
     * @author: zhangyan
     * @date: 2024/7/14 1:21
    **/
    @Scheduled(fixedRate = 1800000)
    public void detectRecordSync(){
//        dataTransfer.detectRecordSqlToMysql();
//        PrintUtil.info("自动同步数据"+ "条--[" + sdf.format(new Date()) + "]", new Object[0]);
    }
    /**
     * @param :
     * @return: void
     * @description: 同步标准器
     * @author: zhangyan
     * @date: 2024/7/14 22:52
    **/
//    @Scheduled(fixedRate = 3600000)
//    public void standardMeterSync(){
//        dataTransfer.readStandardMeter();
//        PrintUtil.info("自动同步数据"+ "条--[" + sdf.format(new Date()) + "]", new Object[0]);
//    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<PgRecord> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<PgRecord> result = new QueryResult<>();
        List<PgRecord> records = pgRecordDao.getByQuery(entityQuery.getQueryMap());
        for (int i = 0; i < records.size(); i++) {
            PgRecord record = records.get(i);
            List<PgInfo> infos = pgInfoDao.getByRecordId(record.getId());
            record.setInfos(sortInfos(infos));
        }
        result.setEntities(records);
        result.setCount(pgRecordDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }
    private List<PgInfo> sortInfos(List<PgInfo> infos){
        List<PgInfo> resultInfos = new ArrayList<>();
        int flag = 1;
        for (int i = flag; flag < infos.size() +1; i++) {
            for (int j = 0; j < infos.size(); j++) {
                if (infos.get(j).getIndex() == i){
                    resultInfos.add(infos.get(j));
                    flag += 1;
                }
            }
        }
        return resultInfos;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PgRecord pgRecord) {
        pgRecordDao.update(pgRecord);
        List<PgInfo> infos = pgRecord.getInfos();
        DecimalFormat df = new DecimalFormat("#.00");
        for (int i = 0; infos != null && i < infos.size(); i++) {
            PgInfo info = infos.get(i);
            info.setIndicationError(Float.valueOf(df.format(Math.abs(info.getStrikeUp()-info.getStrikeDown()))));
            info.setReturnError(Float.valueOf(df.format(Math.abs(info.getStrikeUp()-info.getStrikeDown()))));
            pgInfoDao.insertOrUpdate(info);
        }
    }
    @Override
    public void printWord(String path){
//        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
//        PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
//        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
//        PrintService service = ServiceUI.printDialog(null, 200, 200, pservices,
//                defaultService, flavor, aset);
//        if (service != null) {
//            try {
//                DocPrintJob pj = service.createPrintJob();
//                FileInputStream fis = new FileInputStream("G:" + File.separator + "检定证书.docx");//打印D盘zhidao.txt文档。
//                DocAttributeSet das = new HashDocAttributeSet();
//                Doc doc = new SimpleDoc(fis, flavor, das);
//                pj.print(doc, aset);
//            } catch (FileNotFoundException fe) {
//                fe.printStackTrace();
//            } catch (PrintException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("打印失败");
//        }
    }

}
