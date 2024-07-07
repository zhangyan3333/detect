package com.bjj.detect.query;

import com.bjj.detect.entity.Organization;
import lombok.Getter;
import lombok.Setter;
import com.syzx.framework.query.AbstractEntityQuery;

/**
 * Organization查询 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class OrganizationQuery extends AbstractEntityQuery {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public OrganizationQuery() {
        super(Organization.class);
    }

    /**
     * 配置查询语句
     */
    @Override
    protected void configSql() {
        select(Organization.class)
                .sort(Organization.class, sortKey, sortOrder);
    }

    //</editor-fold>

}
