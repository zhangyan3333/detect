package com.bjj.detect.controller;

import com.bjj.detect.dao.AuthorityDaoNew;
import com.bjj.detect.entity.MyAuth;
import com.bjj.detect.entity.UserInfo;
import com.syzx.framework.controller.AbstractTokenController;

import com.syzx.framework.dao.condition.ConditionFactory;
import com.syzx.framework.dto.LoginDto;
import com.syzx.framework.dto.TokenInfo;
import com.syzx.framework.entity.Authority;
import com.syzx.framework.entity.UserEntity;
import com.syzx.framework.query.EntityQuery;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
//import com.bjj.detect.entity.Authority;
import com.bjj.detect.entity.RoleAuthority;
import com.bjj.detect.entity.UserInfoRole;
import com.bjj.detect.dto.DefaultTokenInfo;
import com.bjj.detect.service.UserInfoService;
//import com.bjj.detect.dao.AuthorityDaoNew;

@RestController
@RequestMapping("/api/tokens")
public class TokenController extends AbstractTokenController {

    /**
     * 获取登录用户权限.
     * @param userId 登录用户id
     * @return 登录用户权限
     */
    private Set<String> getAccess(long userId) {
        //todo 这里作权限的构造
        List<MyAuth> authorities = authorityDao.getByQuery(
                new EntityQuery(MyAuth.class)
                        .leftJoin(MyAuth.class, "id", RoleAuthority.class, "authorityId")
                        .leftJoin(RoleAuthority.class, "roleId", UserInfoRole.class, "roleId")
                        .whereEqual(UserInfoRole.class, "userId", userId)
                        .getQueryMap());

        Set<String> accesses = new HashSet<>();
         for (MyAuth authority : authorities) {
            accesses.add(authority.getAccess());
        }

        return accesses;
    }


    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthorityDaoNew authorityDao;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 获取登录用户.
     * @param loginDto 登录信息
     * @return 登录用户
     */
    @Override
    protected UserEntity getLoginUser(LoginDto loginDto) {
        List<UserInfo> UserInfoes = userInfoService.getByConditions(ConditionFactory.equal("username", loginDto.getUsername()));
        if (UserInfoes.size() > 0) {
            return UserInfoes.get(0);
        } else {
            return null;
        }
    }

    /**
     * 创建登录token.
     * @param loginUser 登录用户
     * @return 登录token
     */
    @Override
    protected TokenInfo getTokenInfo(UserEntity loginUser) {
        UserInfo userInfo = (UserInfo) loginUser;
        DefaultTokenInfo tokenInfo = new DefaultTokenInfo();
        tokenInfo.setName(loginUser.getName());
        tokenInfo.setAvatar(loginUser.getAvatar());
        tokenInfo.setCompanyId(userInfo.getCompanyId());
        tokenInfo.setOrganizationId(userInfo.getOrganizationId());
        Set<String> auths = getAccess(loginUser.getId());
        tokenInfo.setAccess(auths);
        return tokenInfo;
    }

    //</editor-fold>

}
