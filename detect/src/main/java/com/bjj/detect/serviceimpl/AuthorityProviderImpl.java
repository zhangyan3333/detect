package com.bjj.detect.serviceimpl;

import com.syzx.framework.entity.Authority;
import com.syzx.framework.service.AuthorityProvider;
import com.syzx.framework.utils.AuthorityBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityProviderImpl implements AuthorityProvider {

	@Override
	public List<Authority> getAuthorities() {
		return new AuthorityBuilder()
				.addGroup("系统管理")
				.addAuthority("组织结构", "organization")
				.addAuthority("用户管理", "user")
				.addAuthority("角色管理", "role")
				.build();
	}
}
