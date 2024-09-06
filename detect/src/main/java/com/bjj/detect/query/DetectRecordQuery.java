package com.bjj.detect.query;

import com.bjj.detect.entity.DetectRecord;
import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.entity.StandardTool;
import com.syzx.framework.query.AbstractEntityQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * DetectRecord查询 <br/>
 * <p>
 * CreateTime 2024/09/03 22:48
 *
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class DetectRecordQuery extends AbstractEntityQuery {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public DetectRecordQuery() {
        super(DetectRecord.class);
    }

    /**
     * 配置查询语句
     */
//    @Override
//    protected void configSql() {
//        select(DetectRecord.class)
//                .select(PgCertificate.class, "detectTime", "detectTime")
//                .select(PgCertificate.class, "overTime", "overTime")
//                .select(StandardTool.class, "sname", "standardName")
//                .select(StandardTool.class, "mname", "standardToolName")
//                .leftJoin(DetectRecord.class, "resultId", PgCertificate.class, "id")
//                .leftJoin(DetectRecord.class, "standardToolId", StandardTool.class, "id")
//                .sort(DetectRecord.class, sortKey, sortOrder);
//    }

    //</editor-fold>

    private int[] checkStep;
    private String overTime;
    private Long meterId; // 被检表ID

    @Override
    protected void configSql() {
        select(DetectRecord.class)
                .select(PgCertificate.class, "detectTime", "detectTime")
                .select(PgCertificate.class, "overTime", "overTime")
                .select(StandardTool.class, "sname", "standardName")
                .select(StandardTool.class, "mname", "standardToolName")
                .leftJoin(DetectRecord.class, "resultId", PgCertificate.class, "id")
                .leftJoin(DetectRecord.class, "standardToolId", StandardTool.class, "id")
                .ifWhereBetween(checkStep.length == 2, DetectRecord.class,"checkStep",checkStep[0],checkStep[1])
                .ifWhereEqual(meterId!=null && meterId>0, DetectRecord.class,"meterId",meterId)
                .ifWhereLess(overTime!=null&&!overTime.equals(""),PgCertificate.class,"overTime",overTime)
                .sort(DetectRecord.class, sortKey, sortOrder);
    }
}
