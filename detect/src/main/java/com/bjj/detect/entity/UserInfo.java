package com.bjj.detect.entity;

import lombok.Getter;
import lombok.Setter;
import com.syzx.framework.orm.annotation.FullSearch;
import com.syzx.framework.orm.annotation.Index;
import com.syzx.framework.orm.annotation.ManyToOne;
import com.syzx.framework.orm.annotation.ManyToMany;
import com.syzx.framework.orm.annotation.QueryInTree;
import java.util.Set;
import com.syzx.framework.entity.UserEntity;

/**
 * 用户实体类 <br/>
 * <p>
 * CreateTime 2024/07/05 23:27
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
@FullSearch(propertyNames = {"name","organizationName","roles"})
@QueryInTree(pathForeignKeys = {"organizationId"}, pathEntityClasses = {Organization.class})
public class UserInfo extends UserEntity {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 人员级别.
     * 0: 正级
     * 1: 副级
     * >=2: 其他（可以继续自定义）
     */
    private int level;

    @Index
    private long companyId;

    @Index
    private long organizationId;

    @ManyToOne(foreignKeyName = "organizationId", joinClass = Organization.class, joinPropertyName = "name")
    private String organizationName;

    @ManyToMany(middleClass = UserInfoRole.class, joinClass = Role.class, joinPropertyName = "id", cascade = true)
    private Set<Long> roleIds;

    @ManyToMany(middleClass = UserInfoRole.class, joinClass = Role.class, joinPropertyName = "name")
    private String roles;

    //</editor-fold>

}
