package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.DetailInfoDao;

@Repository
public class DetailInfoDaoImpl implements DetailInfoDao {

	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate orderSqlSession;

	@Override
	public String getUserAuthByToken(String token) {
		return orderSqlSession.selectOne(NAMESPACE + "getUserAuthByToken", token);
	}

	@Override
	public Map<String, Object> getCurrentSeqInfo(Map<String, String> param) {
		return orderSqlSession.selectOne(NAMESPACE + "getCurrentSeqInfo", param);
	}

	@Override
	public List<Map<String, Object>> getDetailInfo(Map<String, String> param) {
		return orderSqlSession.selectList(NAMESPACE + "getDetailInfo", param);
	}

	@Override
	public Map<String, Object> getReviewInfo(Map<String, String> param) {
		return orderSqlSession.selectOne(NAMESPACE + "getReviewInfo", param);
	}

	@Override
	public String getImgPathByGroupUUID(String groupUUID) {
		return orderSqlSession.selectOne(NAMESPACE + "getImgPathByGroupUUID", groupUUID);
	}

}
