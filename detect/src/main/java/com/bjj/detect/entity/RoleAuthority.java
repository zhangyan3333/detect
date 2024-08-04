package com.bjj.detect.entity;

import com.syzx.framework.entity.Authority;
import lombok.Getter;
import lombok.Setter;
import com.syzx.framework.orm.annotation.JoinColumn;
import com.syzx.framework.entity.AbstractJoinEntity;

/**
 * 角色权限连接实体 <br/>
 * <p>
 * CreateTime 2024/07/05 23:27
 *
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
public class RoleAuthority extends AbstractJoinEntity {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @JoinColumn(joinClass = Role.class)
    private long roleId;
    @JoinColumn(joinClass = MyAuth.class)
    private long authorityId;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    public RoleAuthority() {
    }

    public RoleAuthority(long roleId, long authorityId) {
        this.roleId = roleId;
        this.authorityId = authorityId;
    }

    //</editor-fold>

}
