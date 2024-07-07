package com.bjj.detect.serviceimpl;

import com.bjj.detect.entity.UserInfo;
import com.bjj.detect.service.UserInfoService;
import com.bjj.detect.dao.UserInfoDao;
import com.syzx.framework.dao.condition.ConditionFactory;
import java.util.List;
import java.util.ArrayList;
import com.syzx.framework.query.IEntityQuery;
import com.syzx.framework.dao.condition.IQueryCondition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.bjj.detect.entity.UserInfoRole;
import com.bjj.detect.dao.UserInfoRoleDao;
import com.syzx.framework.query.QueryResult;
import com.syzx.framework.manager.TokenManager;
import com.syzx.framework.dto.Password;
import com.syzx.framework.exceptions.BusinessException;
import com.syzx.framework.manager.FileManager;
import com.syzx.framework.utils.Md5Util;
import com.syzx.framework.controller.ApiResultCode;

/**
 * UserInfo服务接口实现 <br/>
 * <p>
 * CreateTime 2024/07/05 23:28
 * 
 * @version 1.0.0
 * @author 代码生成器
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserInfoRoleDao userInfoRoleDao;


    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 插入单个实体.
     * @param userInfo 需要插入的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(UserInfo userInfo) {
        userInfo.setPassword(Md5Util.encode(userInfo.getPassword(),userInfo.getUsername()));
        userInfoDao.insert(userInfo);

        if (userInfo.getRoleIds().size() > 0) {
            userInfoRoleDao.insertAll(getUserInfoRoleJoinEntities(userInfo));
        }
    }

    /**
     * 插入多个实体.
     * @param userInfoes 需要插入的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(List<UserInfo> userInfoes) {
        for (UserInfo userInfo : userInfoes) {
            userInfo.setPassword(Md5Util.encode(userInfo.getPassword(),userInfo.getUsername()));
        }
        userInfoDao.insertAll(userInfoes);

        List<UserInfoRole> userInfoRoles = new ArrayList<>();
        for (UserInfo userInfo : userInfoes) {
            userInfoRoles.addAll(getUserInfoRoleJoinEntities(userInfo));
        }
        if (userInfoes.size() > 0) {
            userInfoRoleDao.insertAll(userInfoRoles);
        }
    }

    /**
     * 更新单个实体.
     * @param userInfo 需要更新的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserInfo userInfo) {
        UserInfo oldUserInfo = userInfoDao.getById(userInfo.getId());
        if(!oldUserInfo.getPassword().equals(userInfo.getPassword())){
            userInfo.setPassword(Md5Util.encode(userInfo.getPassword(), userInfo.getUsername()));
        }
        userInfoDao.update(userInfo);

        List<UserInfoRole> oldUserInfoRoles = userInfoRoleDao.getByProperty("userId", userInfo.getId());
        if (isNeedUpdateUserInfoRoleJoinEntities(oldUserInfoRoles, userInfo)) {
            userInfoRoleDao.deleteByProperty("userId", userInfo.getId());
            if (userInfo.getRoleIds().size() > 0) {
                userInfoRoleDao.insertAll(getUserInfoRoleJoinEntities(userInfo));
            }
        }
    }

    /**
     * 更新密码.
     * @param password 需要更新的密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Password password) {
        UserInfo userInfo = userInfoDao.getById(password.getUserId());

        //判断旧密码是否正确
        if(!userInfo.getPassword().equals(Md5Util.encode(password.getOldPassword(), userInfo.getUsername()))){
            throw new BusinessException(ApiResultCode.Fail, "密码错误");
        }

        //设置新密码
        userInfo.setPassword(Md5Util.encode(password.getNewPassword(), userInfo.getUsername()));
        userInfoDao.updatePassword(userInfo);

        //删除token，强制重新登录
        TokenManager.remove(password.getUserId());
    }

    /**
     * 更新名字和头像.
     * @param userInfo 需要更新的名字和头像
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNameAndAvatar(UserInfo userInfo) {
        UserInfo oldUserInfo = userInfoDao.getById(userInfo.getId());
        userInfoDao.updateNameAndAvatar(userInfo);

        FileManager.addFile(oldUserInfo.getAvatar());
    }


    /**
     * 删除指定Id的实体.
     * @param id 需要删除的实体Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        userInfoDao.deleteById(id);

        userInfoRoleDao.deleteByProperty("userId", id);
    }

    /**
     * 获取所有实体.
     * @return 所有实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UserInfo> get() {
        return userInfoDao.get();
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 指定ID的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo getById(long id) {
        return userInfoDao.getById(id);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param conditions 查询条件集合
     * @return 符合查询条件的实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UserInfo> getByConditions(IQueryCondition... conditions) {
        return userInfoDao.getByQuery(ConditionFactory.buildSimpleQuery(UserInfo.class,conditions));
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UserInfo> getByQuery(IEntityQuery entityQuery) {
        return userInfoDao.getByQuery(entityQuery.getQueryMap());
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param entityQuery 查询条件
     * @return 符合查询条件的分页结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QueryResult<UserInfo> pageByQuery(IEntityQuery entityQuery) {
        QueryResult<UserInfo> result = new QueryResult<>();
        result.setEntities(userInfoDao.getByQuery(entityQuery.getQueryMap()));
        result.setCount(userInfoDao.countByQuery(entityQuery.getCountMap()));
        return result;
    }

    /**
     * 生成连接实体.
     * @param entity 
     * @return 连接实体集合
     */
    private List<UserInfoRole> getUserInfoRoleJoinEntities(UserInfo entity) {
        List<UserInfoRole> joinEntities = new ArrayList<>();
        for (long otherEntityId : entity.getRoleIds()) {
            joinEntities.add(new UserInfoRole(entity.getId(), otherEntityId));
        }
        return joinEntities;
    }
    /**
     * 是否需要更新连接实体.
     * @param oldJoinEntities 旧的关联连接实体
     * @param newEntity 
     * @return 是：需要更新；否：不需要更新；
     */
    private boolean isNeedUpdateUserInfoRoleJoinEntities(List<UserInfoRole> oldJoinEntities, UserInfo newEntity) {
        if (oldJoinEntities.size() != newEntity.getRoleIds().size()) {
            return true;
        }

        for (UserInfoRole oldJoinEntity : oldJoinEntities) {
            if (!newEntity.getRoleIds().contains(oldJoinEntity.getRoleId())) {
                return true;
            }
        }

        return false;
    }

    //</editor-fold>

}
