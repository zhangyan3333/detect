package com.bjj.detect.dao;

import com.bjj.detect.entity.MyAuth;
import com.syzx.framework.entity.Authority;

import java.util.List;
import java.util.Map;

public interface AuthorityDaoNew {
	void insert(MyAuth authority);

	void insertAll(List<MyAuth> authorities);

	int count();

	List<MyAuth> get();

	List<MyAuth> getByQuery(Map<String, Object> query);
}
