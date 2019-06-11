package com.inter.consumer.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.FileUploadDao;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {
	
	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public String getGroupUUID() {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getGroupUUID");
	}

}
