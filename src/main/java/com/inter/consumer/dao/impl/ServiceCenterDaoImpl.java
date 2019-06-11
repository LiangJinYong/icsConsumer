package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.ServiceCenterDao;

@Repository
public class ServiceCenterDaoImpl implements ServiceCenterDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public void insertReportOrQuestion(Map<String, Object> paramObj) {
		sqlSessionTemplate.insert(NAMESPACE + "insertReportOrQuestion", paramObj);
	}

	@Override
	public void insertImg(Map<String, Object> paramObj) {
		sqlSessionTemplate.insert(NAMESPACE + "insertImg", paramObj);
	}

	@Override
	public List<Map<String, Object>> getMyQuestionList(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getMyQuestionList", param);
	}

}
