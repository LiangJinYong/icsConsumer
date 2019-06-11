package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.MessageDao;

@Repository
public class MessageDaoImpl implements MessageDao {

	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public String getResultMessage(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getResultMessage", param);
	}

}
