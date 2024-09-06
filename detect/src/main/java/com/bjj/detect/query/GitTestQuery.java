package com.bjj.detect.query;

import com.bjj.detect.entity.GitTest;
import com.syzx.framework.query.AbstractEntityQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * GitTest查询 <br/>
 * <p>
 * CreateTime 2024/09/03 21:21
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class GitTestQuery extends AbstractEntityQuery {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public GitTestQuery() {
        super(GitTest.class);
    }

    /**
     * 配置查询语句
     */
    @Override
    protected void configSql() {
        select(GitTest.class)
                .sort(GitTest.class, sortKey, sortOrder);
    }

    //</editor-fold>

}
