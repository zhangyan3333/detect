package com.bjj.detect.serviceimpl;

import com.bjj.detect.entity.Organization;
import com.bjj.detect.service.OrganizationService;
import com.bjj.detect.dao.OrganizationDao;
import com.syzx.framework.dao.condition.ConditionFactory;
import java.util.List;
import java.util.ArrayList;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.dao.condition.IQueryCondition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.bjj.detect.dao.UserInfoDao;
import com.bjj.detect.dao.OrganizationDao;
import com.syzx.framework.exceptions.BusinessException;
import com.syzx.framework.controller.ApiResultCode;
import com.syzx.framework.controller.ApiResultCode;
import com.syzx.framework.exceptions.BusinessException;
import com.syzx.framework.utils.TreeUtil;

/**
 * Organization服务接口实现 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private UserInfoDao userInfoDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param organization 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Organization organization) {
        if (organization.getParentId() > 0) {
            Organization parent = organizationDao.getById(organization.getParentId());
            organization.updatePath(parent.getPath());
        }
        if (organization.getChildren().size() > 0) {
            organizationDao.insertAll(TreeUtil.toList(organization));
        } else {
            organizationDao.insert(organization);
        }
    }

    /**
     * 更新单个实体.
     * @param organization 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Organization organization) {
        Organization old = organizationDao.getById(organization.getId());

        //若parentId无变化，则只更新当前节点的信息
        if (old.getParentId() == organization.getParentId()) {
            organization.setPath(old.getPath());
            organizationDao.update(organization);
            return;
        }

        //否则，加载所有子节点
        old = TreeUtil.toTree(organizationDao.getByQuery(ConditionFactory.buildSimpleQuery(Organization.class, ConditionFactory.contains("path", organization.getId())))).get(0);
        //获取父节点
        Organization parent = organizationDao.getById(organization.getParentId());
        if(parent==null && organization.getParentId()!=0) {
            throw new BusinessException(ApiResultCode.NotExistParent, "指定的父节点不存在");
        }
        //更新当前及所有子节点的path
        old.updatePath(parent == null ? null : parent.getPath());
        organization.setPath(old.getPath());
        //更新ParentId
        if (parent == null) {
            organization.setParentId(0);
        }
        old.setParentId(organization.getParentId());

        //更新所有节点
        organizationDao.updateAll(TreeUtil.toList(old));
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        if (userInfoDao.countByProperty("organizationId", id) > 0) {
            throw new BusinessException(ApiResultCode.ReferencedByEntity, "当前机构下还有关联的用户，请先取消关联");
        }

        if (organizationDao.countByProperty("parentId", id) > 0) {
            throw new BusinessException(ApiResultCode.ReferencedByEntity, "当前机构下还有未删除的子机构，请先删除子机构");
        }

        organizationDao.deleteById(id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Organization> get() {
        return TreeUtil.toTree(organizationDao.get());
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Organization getById(long id) {
        return organizationDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Organization> getByConditions(IQueryCondition... conditions) {
        return organizationDao.getByQuery(ConditionFactory.buildSimpleQuery(Organization.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Organization> getByQuery(IEntityQuery entityQuery) {
        return organizationDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 获取指定ID的实体及其子孙.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体及其子孙
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Organization getByIdWithChildren(long id) {
        return TreeUtil.toTree(organizationDao.getByQuery(ConditionFactory.buildSimpleQuery(Organization.class, (ConditionFactory.contains("path", id))))).get(0);
    }

    /**
     * 获取指定父节点的所有子节点.
     * @param parentId 父节点Id
     * @return 指定父节点的所有子节点集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Organization> getChildren(long parentId) {
        return organizationDao.getByProperty("parentId", parentId);
    }


    //</editor-fold>

}
