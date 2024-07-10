package com.bjj.detect.query;

import com.bjj.detect.entity.PgInfo;
import com.syzx.framework.query.AbstractEntityQuery;
import lombok.Getter;
import lombok.Setter;

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
public class DetectQuery {

    protected String sortKey;
    protected String sortOrder;
    private int pageIndex;
    private int pageSize;

    private String fullSearch;

    public DetectQuery() {

    }


}
