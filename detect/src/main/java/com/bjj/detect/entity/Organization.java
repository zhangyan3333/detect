package com.bjj.detect.entity;

import lombok.Getter;
import lombok.Setter;
import com.syzx.framework.orm.annotation.DeleteCheck;
import com.syzx.framework.entity.TreeEntity;

/**
 * 组织机构实体类 <br/>
 * <p>
 * CreateTime 2024/07/05 23:27
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Getter
@Setter
@DeleteCheck(checkClass = UserInfo.class, checkClassForeignKeyName = "organizationId", errorMessage = "当前机构下还有关联的用户，请先取消关联")
@DeleteCheck(checkClass = Organization.class, checkClassForeignKeyName = "parentId", errorMessage = "当前机构下还有未删除的子机构，请先删除子机构")
public class Organization extends TreeEntity {

}
