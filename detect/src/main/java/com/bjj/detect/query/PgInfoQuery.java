package com.bjj.detect.query;

import com.bjj.detect.entity.PgInfo;
import lombok.Getter;
import lombok.Setter;
import com.syzx.framework.query.AbstractEntityQuery;

/**
 * PgInfo查询 <br/>
 * <p>
 * CreateTime 2024/07/10 01:44
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class PgInfoQuery extends AbstractEntityQuery {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public PgInfoQuery() {
        super(PgInfo.class);
    }

    /**
     * 配置查询语句
     */
    @Override
    protected void configSql() {
        select(PgInfo.class)
                .sort(PgInfo.class, sortKey, sortOrder);
    }

    //</editor-fold>

}
