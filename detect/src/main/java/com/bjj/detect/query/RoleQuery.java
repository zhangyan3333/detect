package com.bjj.detect.query;

import com.bjj.detect.entity.MyAuth;
import com.bjj.detect.entity.Role;
import com.bjj.detect.entity.RoleAuthority;
import com.syzx.framework.entity.Authority;
import com.syzx.framework.query.AbstractEntityQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * Role查询 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class RoleQuery extends AbstractEntityQuery {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    private String fullSearch;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public RoleQuery() {
        super(Role.class);
    }

    /**
     * 配置查询语句
     */
    @Override
    protected void configSql() {
        select(Role.class)
                .select("GROUP_CONCAT", MyAuth.class, "id", "authorityIds")
                .leftJoin(Role.class, "id", RoleAuthority.class, "roleId")
                .leftJoin(RoleAuthority.class, "authorityId", MyAuth.class, "id")
                .group(Role.class, "id")
                .fullSearch(fullSearch,"name")
                .sort(Role.class, sortKey, sortOrder);
    }

    //</editor-fold>

}
