package com.bjj.detect.query;

import com.bjj.detect.entity.StandardTool;
import com.bjj.detect.entity.UserInfo;
import com.syzx.framework.query.AbstractEntityQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * StandardTool查询 <br/>
 * <p>
 * CreateTime 2024/07/30 11:42
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class StandardToolQuery extends AbstractEntityQuery {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    private String fullSearch;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public StandardToolQuery() {
        super(StandardTool.class);
    }

    /**
     * 配置查询语句
     */
    @Override
    protected void configSql() {
        select(StandardTool.class)
                .fullSearch(fullSearch,"sname","mname","sFactory","sResolution","sRegulateBcode","modifyUserName","organizationName","location")
                .sort(StandardTool.class, sortKey, sortOrder);
    }

    //</editor-fold>

}
