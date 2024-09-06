package com.bjj.detect.query;

import com.bjj.detect.entity.DetectMeter;
import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.StandardTool;
import com.syzx.framework.query.AbstractEntityQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * DetectMeter查询 <br/>
 * <p>
 * CreateTime 2024/09/03 22:48
 *
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class DetectMeterQuery extends AbstractEntityQuery {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    private String fullSearch;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public DetectMeterQuery() {
        super(DetectMeter.class);
    }

    /**
     * 配置查询语句
     */
//    @Override
//    protected void configSql() {
//        select(DetectMeter.class)
//                .select(PgCertificate.class, "detectTime", "detectTime")
//                .select(PgCertificate.class, "overTime", "overTime")
//                .select(StandardTool.class, "sname", "standardName")
//                .select(StandardTool.class, "mname", "standardToolName")
//                .leftJoin(DetectMeter.class, "resultId", PgCertificate.class, "id")
//                .leftJoin(DetectMeter.class, "standardToolId", StandardTool.class, "id")
//                .fullSearch(fullSearch,"meterName","meterType","meterCustomer","standardToolId")
//                .sort(DetectMeter.class, sortKey, sortOrder);
//    }

    //</editor-fold>

    private Long standardToolId;


    @Override
    protected void configSql() {
        select(DetectMeter.class)
                .select(PgCertificate.class, "detectTime", "detectTime")
                .select(PgCertificate.class, "overTime", "overTime")
                .select(StandardTool.class, "sname", "standardName")
                .select(StandardTool.class, "mname", "standardToolName")
                .ifWhereEqual(standardToolId !=null && standardToolId > 0,DetectMeter.class,"standardToolId",standardToolId)
                .leftJoin(DetectMeter.class, "resultId", PgCertificate.class, "id")
                .leftJoin(DetectMeter.class, "standardToolId", StandardTool.class, "id")
                .fullSearch(fullSearch,"meterName","meterType","meterCustomer")
                .sort(DetectMeter.class, sortKey, sortOrder);
    }
}
