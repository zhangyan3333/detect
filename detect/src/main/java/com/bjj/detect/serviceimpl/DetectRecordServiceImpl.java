package com.bjj.detect.serviceimpl;

import com.bjj.detect.dao.DetectMeterDao;
import com.bjj.detect.dao.DetectRecordDao;
import com.bjj.detect.entity.DetectMeter;
import com.bjj.detect.entity.DetectRecord;
import com.bjj.detect.entity.StandardTool;
import com.bjj.detect.entity.VO.DetectRecordVO;
import com.bjj.detect.service.DetectMeterService;
import com.bjj.detect.service.DetectRecordService;
import com.bjj.detect.service.StandardToolService;
import com.syzx.framework.dao.condition.ConditionFactory;
import com.syzx.framework.dao.condition.IQueryCondition;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.query.QueryResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DetectRecord服务接口实现 <br/>
 * <p>
 * CreateTime 2024/09/03 22:48
 *
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class DetectRecordServiceImpl implements DetectRecordService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private DetectRecordDao detectRecordDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param detectRecord 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DetectRecord detectRecord) {
        detectRecordDao.insert(detectRecord);
    }

    /**
     * 插入多个实体.
     * @param detectRecords 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<DetectRecord> detectRecords) {
        detectRecordDao.insertAll(detectRecords);
    }

    /**
     * 更新单个实体.
     * @param detectRecord 需要更新的实体
     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void update(DetectRecord detectRecord) {
//        detectRecordDao.update(detectRecord);
//    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        detectRecordDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DetectRecord> get() {
        return detectRecordDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DetectRecord getById(long id) {
        return detectRecordDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DetectRecord> getByConditions(IQueryCondition... conditions) {
        return detectRecordDao.getByQuery(ConditionFactory.buildSimpleQuery(DetectRecord.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DetectRecord> getByQuery(IEntityQuery entityQuery) {
        return detectRecordDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public QueryResult<DetectRecord> pageByQuery(IEntityQuery entityQuery) {
//        QueryResult<DetectRecord> result = new QueryResult<>();
//        result.setEntities(detectRecordDao.getByQuery(entityQuery.getQueryMap()));
//        result.setCount(detectRecordDao.countByQuery(entityQuery.getCountMap()));
//        return result;
//    }


    //</editor-fold>

    @Autowired
    private DetectMeterService detectMeterService;
    @Autowired
    private StandardToolService standardToolService;
    @Autowired
    private DetectMeterDao detectMeterDao;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<DetectRecordVO> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<DetectRecordVO> result = new QueryResult<>();
        List<DetectRecord> records = detectRecordDao.getByQuery(entityQuery.getQueryMap());
        // 补全显示信息
        List<DetectRecordVO> recordVOS = new ArrayList<DetectRecordVO>();
        for (int i = 0; records.size()>0 && i < records.size() ; i++) {
            DetectRecordVO recordVO = changeToVo(records.get(i));
            recordVOS.add(recordVO);
        }
        result.setEntities(recordVOS);
        result.setCount(detectRecordDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }

    /***
     * @param detectRecord:
     * @return: void
     * @description:更新状态，如果从checkstep从3转为0，则要更新被检表中的文件，检定时间等信息
     * @author: zhangyan
     * @date: 2024/9/7 3:00
    **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DetectRecord detectRecord) {
        if (detectRecord.getCheckStep() == 0){
            DetectMeter detectMeter = detectMeterDao.getById(detectRecord.getMeterId());
            detectMeter.setDetectTime(new Date());
            detectMeter.setRecordFile(detectRecord.getRecordFile());
            detectMeter.setResultFile(detectRecord.getResultFile());
            detectMeter.setDetectCode(detectRecord.getDetectCode());
            detectMeterDao.update(detectMeter);
        }

        detectRecordDao.update(detectRecord);
    }



    /***
     * @param detectRecord:
     * @return: DetectRecordVO
     * @description: 视图转换
     * @author: zhangyan
     * @date: 2024/9/7 0:10
    **/
    public DetectRecordVO changeToVo(DetectRecord detectRecord){
        DetectRecordVO detectRecordVO = new DetectRecordVO();
        detectRecordVO.setDetectCode(detectRecord.getDetectCode());
        StandardTool standardTool = standardToolService.getById(detectRecord.getStandardToolId());
        DetectMeter detectMeter = detectMeterService.getById(detectRecord.getMeterId());
        detectRecordVO.setMeterId(detectRecord.getMeterId());
        detectRecordVO.setMeterName(detectMeter.getMeterName());
        detectRecordVO.setMeterType(detectMeter.getMeterType());
        detectRecordVO.setMeterCustomer(detectMeter.getMeterCustomer());
        detectRecordVO.setMeterCode(detectMeter.getMeterCode());
        detectRecordVO.setMeterRangeL(detectMeter.getMeterRangeL());
        detectRecordVO.setMeterRangeH(detectMeter.getMeterRangeH());
        detectRecordVO.setMeterResolution(detectMeter.getMeterResolution());
        detectRecordVO.setMeterDivisionNo(detectMeter.getMeterDivisionNo());
        detectRecordVO.setMeterFactory(detectMeter.getMeterFactory());
        detectRecordVO.setSname(standardTool.getSname());
        detectRecordVO.setScode(standardTool.getSRegulateCode());
        // 鉴定结果及通知中的 标准器量程 多行值
        detectRecordVO.setSRangeL(standardTool.getSRange());
        // 检测记录中的 标准器量程 0~60
        detectRecordVO.setSRangeH(standardTool.getMRange());
        detectRecordVO.setSResolution(standardTool.getSResolution());
        detectRecordVO.setStandardMedium(standardTool.getLocation());
        detectRecordVO.setStandardLoc(standardTool.getLocation());
        detectRecordVO.setSEdate(standardTool.getSEdate());
        detectRecordVO.setSBasis(standardTool.getSBasis());
        detectRecordVO.setSTemperature(standardTool.getModifyUserId());
        detectRecordVO.setSHumidity(standardTool.getModifyUserId());
        detectRecordVO.setSRegulateCode(standardTool.getSRegulateCode());
        detectRecordVO.setSFactory(standardTool.getSFactory());
        detectRecordVO.setSRegulateBcode(standardTool.getSRegulateBcode());
        detectRecordVO.setSBdate(standardTool.getSBdate());
        detectRecordVO.setAppearance(detectRecord.getAppearance());
        detectRecordVO.setPointer(detectRecord.getPointer());
        detectRecordVO.setIndicationErrorMax(detectRecord.getIndicationErrorMax());
        detectRecordVO.setReturnErrorMax(detectRecord.getReturnErrorMax());
        detectRecordVO.setPositionMax(detectRecord.getPositionMax());
        detectRecordVO.setIndicationErrorPermit(detectRecord.getIndicationErrorPermit());
        detectRecordVO.setReturnErrorPermit(detectRecord.getReturnErrorPermit());
        detectRecordVO.setPositionPermit(detectRecord.getPositionPermit());
        detectRecordVO.setIndicationErrorUpper(detectRecord.getIndicationErrorUpper());
        detectRecordVO.setReturnErrorUpper(detectRecord.getReturnErrorUpper());
        detectRecordVO.setPositionUpper(detectRecord.getPositionUpper());
        detectRecordVO.setIndicationErrorUpperPermit(detectRecord.getIndicationErrorUpperPermit());
        detectRecordVO.setReturnErrorUpperPermit(detectRecord.getReturnErrorUpperPermit());
        detectRecordVO.setPositionUpperPermit(detectRecord.getPositionUpperPermit());
        detectRecordVO.setDetectResult(detectRecord.getDetectResult());
        detectRecordVO.setDetectLevel(detectRecord.getDetectLevel());
        detectRecordVO.setRemark(detectRecord.getRemark());
        detectRecordVO.setInspector(detectRecord.getInspector());
        detectRecordVO.setVerifier(detectRecord.getVerifier());
        detectRecordVO.setApprover(detectRecord.getApprover());
        detectRecordVO.setDetectTime(detectRecord.getDetectTime());
        detectRecordVO.setOverTime(detectRecord.getOverTime());
        detectRecordVO.setCheckStep(detectRecord.getCheckStep());
        detectRecordVO.setResultId(detectRecord.getResultId());
        detectRecordVO.setResultFile(detectRecord.getResultFile());
        detectRecordVO.setRecordFile(detectRecord.getRecordFile());
        detectRecordVO.setImgPath1(detectRecord.getImgPath1());
        detectRecordVO.setImgPath2(detectRecord.getImgPath2());
        detectRecordVO.setStandardToolId(detectRecord.getStandardToolId());
        detectRecordVO.setStandardName(detectRecord.getStandardName());
        detectRecordVO.setStandardToolName(detectRecord.getStandardToolName());
        detectRecordVO.setSPressure(detectRecord.getSPressure());
        detectRecordVO.setStrikeUp(detectRecord.getStrikeUp());
        detectRecordVO.setStrikeDown(detectRecord.getStrikeDown());
        detectRecordVO.setPositionUp(detectRecord.getPositionUp());
        detectRecordVO.setPositionDown(detectRecord.getPositionDown());
        detectRecordVO.setIndicationError(detectRecord.getIndicationError());
        detectRecordVO.setReturnError(detectRecord.getReturnError());
        detectRecordVO.setUpImage1(detectRecord.getUpImage1());
        detectRecordVO.setDownImage1(detectRecord.getDownImage1());
        detectRecordVO.setUpImage2(detectRecord.getUpImage2());
        detectRecordVO.setDownImage2(detectRecord.getDownImage2());
        detectRecordVO.setUpImage3(detectRecord.getUpImage3());
        detectRecordVO.setDownImage3(detectRecord.getDownImage3());
        detectRecordVO.setUpImage4(detectRecord.getUpImage4());
        detectRecordVO.setDownImage4(detectRecord.getDownImage4());
        detectRecordVO.setUpImage5(detectRecord.getUpImage5());
        detectRecordVO.setDownImage5(detectRecord.getDownImage5());
        detectRecordVO.setUpImage6(detectRecord.getUpImage6());
        detectRecordVO.setDownImage6(detectRecord.getDownImage6());
        detectRecordVO.setUpImage7(detectRecord.getUpImage7());
        detectRecordVO.setDownImage7(detectRecord.getDownImage7());
        detectRecordVO.setId(detectRecord.getId());
        detectRecordVO.setCreateTime(detectRecord.getCreateTime());
        return detectRecordVO;
    }
    public DetectRecord changeToPo(DetectRecordVO detectRecordVO){
        DetectRecord detectRecord = new DetectRecord();
        detectRecord.setMeterId(detectRecordVO.getMeterId());
        detectRecord.setSPressure(detectRecordVO.getSPressure());
        detectRecord.setStrikeUp(detectRecordVO.getStrikeUp());
        detectRecord.setStrikeDown(detectRecordVO.getStrikeDown());
        detectRecord.setPositionUp(detectRecordVO.getPositionUp());
        detectRecord.setPositionDown(detectRecordVO.getPositionDown());
        detectRecord.setIndicationError(detectRecordVO.getIndicationError());
        detectRecord.setReturnError(detectRecordVO.getReturnError());
        detectRecord.setUpImage1(detectRecordVO.getUpImage1());
        detectRecord.setDownImage1(detectRecordVO.getDownImage1());
        detectRecord.setUpImage2(detectRecordVO.getUpImage2());
        detectRecord.setDownImage2(detectRecordVO.getDownImage2());
        detectRecord.setUpImage3(detectRecordVO.getUpImage3());
        detectRecord.setDownImage3(detectRecordVO.getDownImage3());
        detectRecord.setUpImage4(detectRecordVO.getUpImage4());
        detectRecord.setDownImage4(detectRecordVO.getDownImage4());
        detectRecord.setUpImage5(detectRecordVO.getUpImage5());
        detectRecord.setDownImage5(detectRecordVO.getDownImage5());
        detectRecord.setUpImage6(detectRecordVO.getUpImage6());
        detectRecord.setDownImage6(detectRecordVO.getDownImage6());
        detectRecord.setUpImage7(detectRecordVO.getUpImage7());
        detectRecord.setDownImage7(detectRecordVO.getDownImage7());
        detectRecord.setAppearance(detectRecordVO.getAppearance());
        detectRecord.setPointer(detectRecordVO.getPointer());
        detectRecord.setIndicationErrorMax(detectRecordVO.getIndicationErrorMax());
        detectRecord.setReturnErrorMax(detectRecordVO.getReturnErrorMax());
        detectRecord.setPositionMax(detectRecordVO.getPositionMax());
        detectRecord.setIndicationErrorPermit(detectRecordVO.getIndicationErrorPermit());
        detectRecord.setReturnErrorPermit(detectRecordVO.getReturnErrorPermit());
        detectRecord.setPositionPermit(detectRecordVO.getPositionPermit());
        detectRecord.setIndicationErrorUpper(detectRecordVO.getIndicationErrorUpper());
        detectRecord.setReturnErrorUpper(detectRecordVO.getReturnErrorUpper());
        detectRecord.setPositionUpper(detectRecordVO.getPositionUpper());
        detectRecord.setIndicationErrorUpperPermit(detectRecordVO.getIndicationErrorUpperPermit());
        detectRecord.setReturnErrorUpperPermit(detectRecordVO.getReturnErrorUpperPermit());
        detectRecord.setPositionUpperPermit(detectRecordVO.getPositionUpperPermit());
        detectRecord.setRemark(detectRecordVO.getRemark());
        detectRecord.setDetectResult(detectRecordVO.getDetectResult());
        detectRecord.setDetectLevel(detectRecordVO.getDetectLevel());
        detectRecord.setCheckStep(detectRecordVO.getCheckStep());
        detectRecord.setInspector(detectRecordVO.getInspector());
        detectRecord.setVerifier(detectRecordVO.getVerifier());
        detectRecord.setApprover(detectRecordVO.getApprover());
        detectRecord.setResultId(detectRecordVO.getResultId());
        detectRecord.setDetectCode(detectRecordVO.getDetectCode());
        detectRecord.setDetectTime(detectRecordVO.getDetectTime());
        detectRecord.setOverTime(detectRecordVO.getOverTime());
        detectRecord.setStandardToolId(detectRecordVO.getStandardToolId());
        detectRecord.setStandardName(detectRecordVO.getStandardName());
        detectRecord.setStandardToolName(detectRecordVO.getStandardToolName());
        detectRecord.setResultFile(detectRecordVO.getResultFile());
        detectRecord.setRecordFile(detectRecordVO.getRecordFile());
        detectRecord.setImgPath1(detectRecordVO.getImgPath1());
        detectRecord.setImgPath2(detectRecordVO.getImgPath2());
        detectRecord.setId(detectRecordVO.getId());
        detectRecord.setCreateTime(detectRecordVO.getCreateTime());
        return detectRecord;
    }

}
