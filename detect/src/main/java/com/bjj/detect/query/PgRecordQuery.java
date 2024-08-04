package com.bjj.detect.query;

import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.entity.StandardTool;
import com.syzx.framework.query.AbstractEntityQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * PgRecord查询 <br/>
 * <p>
 * CreateTime 2024/07/10 01:44
 *
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class PgRecordQuery extends AbstractEntityQuery {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    private String fullSearch;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public PgRecordQuery() {
        super(PgRecord.class);
    }

//    /**
//     * 配置查询语句
//     */
//    @Override
//    protected void configSql() {
//        select(PgRecord.class)
//                .select(PgCertificate.class, "detectTime", "detectTime")
//                .select(PgCertificate.class, "overTime", "overTime")
//                .select(StandardTool.class, "sname", "standardName")
//                .select(StandardTool.class, "mname", "standardToolName")
//                .leftJoin(PgRecord.class, "resultId", PgCertificate.class, "id")
//                .leftJoin(PgRecord.class, "standardToolId", StandardTool.class, "id")
//                .fullSearch(fullSearch,"meterName","meterCustomer")
//                .sort(PgRecord.class, sortKey, sortOrder);
//    }

    //</editor-fold>

    private int[] checkStep;
    private String overTime;

    /**
     * 配置查询语句
     */
    @Override
    protected void configSql() {
        select(PgRecord.class)
                .select(PgCertificate.class, "detectTime", "detectTime")
                .select(PgCertificate.class, "overTime", "overTime")
                .select(StandardTool.class, "sname", "standardName")
                .select(StandardTool.class, "mname", "standardToolName")
                .leftJoin(PgRecord.class, "resultId", PgCertificate.class, "id")
                .leftJoin(PgRecord.class, "standardToolId", StandardTool.class, "id")
                .ifWhereBetween(checkStep.length == 2,PgRecord.class,"checkStep",checkStep[0],checkStep[1])
                .ifWhereLess(overTime!=null&&!overTime.equals(""),PgCertificate.class,"overTime",overTime)
                .fullSearch(fullSearch,"meterName","meterCustomer")
                .sort(PgRecord.class, sortKey, sortOrder);
    }
}
