package com.bjj.detect.query;

import com.bjj.detect.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;
import com.syzx.framework.query.AbstractEntityQuery;
import com.bjj.detect.entity.Organization;
import com.bjj.detect.entity.Role;
import com.bjj.detect.entity.Role;
import com.bjj.detect.entity.UserInfoRole;

/**
 * UserInfo查询 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class UserInfoQuery extends AbstractEntityQuery {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    private String fullSearch;
    private long organizationId;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public UserInfoQuery() {
        super(UserInfo.class);
    }

    /**
     * 配置查询语句
     */
    @Override
    protected void configSql() {
        select(UserInfo.class)
                .select(Organization.class, "name", "organizationName")
                .select("GROUP_CONCAT", Role.class, "id", "roleIds")
                .select("GROUP_CONCAT", Role.class, "name", "roles")
                .leftJoin(UserInfo.class, "organizationId", Organization.class, "id")
                .leftJoin(UserInfo.class, "id", UserInfoRole.class, "userId")
                .leftJoin(UserInfoRole.class, "roleId", Role.class, "id")
                .ifWhereContains(organizationId > 0, Organization.class, "path", organizationId)
                .group(UserInfo.class, "id")
                .fullSearch(fullSearch,"name","organizationName","roles")
                .sort(UserInfo.class, sortKey, sortOrder);
    }

    //</editor-fold>

}
