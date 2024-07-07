package com.bjj.detect.dao;

import com.bjj.detect.entity.RoleAuthority;
import java.util.List;
import java.util.Map;

/**
 * RoleAuthority仓储接口 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
public interface RoleAuthorityDao {

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param roleAuthority 需要插入的实体
     */
    void insert(RoleAuthority roleAuthority);
    /**
     * 插入多个实体.
     * @param roleAuthorities 需要插入的实体集合
     */
    void insertAll(List<RoleAuthority> roleAuthorities);



    /**
     * 删除属性匹配的实体集合.
     * @param propertyName 匹配属性名称
     * @param value 匹配属性值
     */
    void deleteByProperty(String propertyName, Object value);

    /**
     * 获取实体总数.
     * @return 实体总数
     */
    int count();
    /**
     * 获取属性匹配的实体总数.
     * @param propertyName 匹配属性名称
     * @param value 匹配属性值
     * @return 属性匹配的实体总数
     */
    int countByProperty(String propertyName, Object value);

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    List<RoleAuthority> get();
    /**
     * 获取属性匹配的实体集合.
     * @param propertyName 匹配属性名称
     * @param value 匹配属性值
     * @return 属性匹配的实体集合
     */
    List<RoleAuthority> getByProperty(String propertyName, Object value);

    //</editor-fold>

}
