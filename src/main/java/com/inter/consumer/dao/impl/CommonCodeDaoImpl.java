package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.CommonCodeDao;

@Repository
public class CommonCodeDaoImpl implements CommonCodeDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public String getCommonCodeValueName(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getCommonCodeValueName", param);
	}

}
