package com.bjj.detect.entity;

import com.syzx.framework.entity.Authority;
import lombok.Getter;
import lombok.Setter;
import com.syzx.framework.orm.annotation.DeleteCheck;
import com.syzx.framework.orm.annotation.FullSearch;
import com.syzx.framework.orm.annotation.Column;
import com.syzx.framework.orm.annotation.ManyToMany;
import java.util.Set;
import com.syzx.framework.entity.AbstractEntity;

/**
 * 角色实体类 <br/>
 * <p>
 * CreateTime 2024/07/05 23:27
 *
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
@FullSearch(propertyNames = {"name"})
@DeleteCheck(checkClass = UserInfoRole.class, checkClassForeignKeyName = "roleId", errorMessage = "当前角色还有关联的用户，请先取消关联")
public class Role extends AbstractEntity {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Column(length = 30)
    private String name;

//    @ManyToMany(middleClass = RoleAuthority.class, joinClass = Authority.class, joinPropertyName = "id", cascade = true)
//    private Set<Long> authorityIds;

    //</editor-fold>

    @ManyToMany(middleClass = RoleAuthority.class, joinClass = MyAuth.class, joinPropertyName = "id", cascade = true)
    private Set<Long> authorityIds;
}
