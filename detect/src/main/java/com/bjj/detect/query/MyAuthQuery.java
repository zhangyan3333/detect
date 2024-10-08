package com.bjj.detect.query;

import com.bjj.detect.entity.MyAuth;
import com.syzx.framework.query.AbstractEntityQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * MyAuth查询 <br/>
 * <p>
 * CreateTime 2024/08/01 19:20
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class MyAuthQuery extends AbstractEntityQuery {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public MyAuthQuery() {
        super(MyAuth.class);
    }

    /**
     * 配置查询语句
     */
    @Override
    protected void configSql() {
        select(MyAuth.class)
                .sort(MyAuth.class, sortKey, sortOrder);
    }

    //</editor-fold>

}
