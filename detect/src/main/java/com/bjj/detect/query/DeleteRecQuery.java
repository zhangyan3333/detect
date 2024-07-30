package com.bjj.detect.query;

import com.bjj.detect.entity.DeleteRec;
import lombok.Getter;
import lombok.Setter;
import com.syzx.framework.query.AbstractEntityQuery;

/**
 * DeleteRec查询 <br/>
 * <p>
 * CreateTime 2024/07/30 13:47
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class DeleteRecQuery extends AbstractEntityQuery {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public DeleteRecQuery() {
        super(DeleteRec.class);
    }

    /**
     * 配置查询语句
     */
    @Override
    protected void configSql() {
        select(DeleteRec.class)
                .sort(DeleteRec.class, sortKey, sortOrder);
    }

    //</editor-fold>

}
