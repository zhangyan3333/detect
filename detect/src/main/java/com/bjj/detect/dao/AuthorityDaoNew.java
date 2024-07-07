package com.bjj.detect.dao;

import com.syzx.framework.entity.Authority;

import java.util.List;
import java.util.Map;

public interface AuthorityDaoNew {
	void insert(Authority authority);

	void insertAll(List<Authority> authorities);

	int count();

	List<Authority> get();

	List<Authority> getByQuery(Map<String, Object> query);
}
