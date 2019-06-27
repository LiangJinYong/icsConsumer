package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.VersionCheckDao;

@Repository
public class VersionCheckDaoImpl implements VersionCheckDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public Map<String, Object> getVersionCheckInfo(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getVersionCheckInfo", param);
	}

	public String getEncrptedKey() {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getEncrptedKey");
	}

	@Override
	public List<String> getQrExclusiveUrlList() {
		return sqlSessionTemplate.selectList(NAMESPACE + "getQrExclusiveUrlList");
	}

}
