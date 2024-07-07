package com.bjj.detect.serviceimpl;

import com.bjj.detect.entity.Role;
import com.bjj.detect.service.RoleService;
import com.bjj.detect.dao.RoleDao;
import com.syzx.framework.dao.condition.ConditionFactory;
import java.util.List;
import java.util.ArrayList;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.dao.condition.IQueryCondition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.bjj.detect.entity.RoleAuthority;
import com.bjj.detect.dao.RoleAuthorityDao;
import com.bjj.detect.dao.UserInfoRoleDao;
import com.syzx.framework.exceptions.BusinessException;
import com.syzx.framework.controller.ApiResultCode;
import com.syzx.framework.query.QueryResult;

/**
 * Role服务接口实现 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class RoleServiceImpl implements RoleService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleAuthorityDao roleAuthorityDao;

    @Autowired
    private UserInfoRoleDao userInfoRoleDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param role 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Role role) {
        roleDao.insert(role);

        if (role.getAuthorityIds().size() > 0) {
            roleAuthorityDao.insertAll(getRoleAuthorityJoinEntities(role));
        }
    }

    /**
     * 插入多个实体.
     * @param roles 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<Role> roles) {
        roleDao.insertAll(roles);

        List<RoleAuthority> roleAuthorities = new ArrayList<>();
        for (Role role : roles) {
            roleAuthorities.addAll(getRoleAuthorityJoinEntities(role));
        }
        if (roles.size() > 0) {
            roleAuthorityDao.insertAll(roleAuthorities);
        }
    }

    /**
     * 更新单个实体.
     * @param role 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Role role) {
        roleDao.update(role);

        List<RoleAuthority> oldRoleAuthorities = roleAuthorityDao.getByProperty("roleId", role.getId());
        if (isNeedUpdateRoleAuthorityJoinEntities(oldRoleAuthorities, role)) {
            roleAuthorityDao.deleteByProperty("roleId", role.getId());
            if (role.getAuthorityIds().size() > 0) {
                roleAuthorityDao.insertAll(getRoleAuthorityJoinEntities(role));
            }
        }
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        if (userInfoRoleDao.countByProperty("roleId", id) > 0) {
            throw new BusinessException(ApiResultCode.ReferencedByEntity, "当前角色还有关联的用户，请先取消关联");
        }

        roleDao.deleteById(id);

        roleAuthorityDao.deleteByProperty("roleId", id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Role> get() {
        return roleDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getById(long id) {
        return roleDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Role> getByConditions(IQueryCondition... conditions) {
        return roleDao.getByQuery(ConditionFactory.buildSimpleQuery(Role.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Role> getByQuery(IEntityQuery entityQuery) {
        return roleDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<Role> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<Role> result = new QueryResult<>();
        result.setEntities(roleDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(roleDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }

    /**
     * 生成连接实体.
     * @param entity 
     * @return 连接实体集合
     */
    private List<RoleAuthority> getRoleAuthorityJoinEntities(Role entity) {
        List<RoleAuthority> joinEntities = new ArrayList<>();
        for (long otherEntityId : entity.getAuthorityIds()) {
            joinEntities.add(new RoleAuthority(entity.getId(), otherEntityId));
        }
        return joinEntities;
    }
    /**
     * 是否需要更新连接实体.
     * @param oldJoinEntities 旧的关联连接实体
     * @param newEntity 
     * @return 是：需要更新；否：不需要更新；
     */
    private boolean isNeedUpdateRoleAuthorityJoinEntities(List<RoleAuthority> oldJoinEntities, Role newEntity) {
        if (oldJoinEntities.size() != newEntity.getAuthorityIds().size()) {
            return true;
        }

        for (RoleAuthority oldJoinEntity : oldJoinEntities) {
            if (!newEntity.getAuthorityIds().contains(oldJoinEntity.getAuthorityId())) {
                return true;
            }
        }

        return false;
    }

    //</editor-fold>

}
