package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.DetailInfoDao;

@Repository
public class DetailInfoDaoImpl implements DetailInfoDao {

	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public String getUserAuthByToken(String token) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserAuthByToken", token);
	}

	@Override
	public Map<String, Object> getCurrentSeqInfo(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getCurrentSeqInfo", param);
	}
	
	@Override
	public String getMainLanguage(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getMainLanguage", param);
	}

	@Override
	public List<Map<String, Object>> getDetailInfo(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getDetailInfo", param);
	}
	
	@Override
	public List<Map<String, Object>> getDetailInfoByOrder(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + " ", param);
	}

	@Override
	public Map<String, Object> getReviewInfo(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getReviewInfo", param);
	}

	@Override
	public List<String> getImgPathByGroupUUID(String groupUUID) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getImgPathByGroupUUID", groupUUID);
	}
	
	@Override
	public Map<String, Object> getDetailContent(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getDetailContent", param);
	}

	// CN
	@Override
	public Map<String, Object> getCurrentSeqInfoCN(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getCurrentSeqInfoCN", param);
	}

	@Override
	public List<Map<String, Object>> getIconColorInfo(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getIconColorInfo", param);
	}

	@Override
	public List<Map<String, Object>> getDetailinfoCN(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getDetailinfoCN", param);
	}

	@Override
	public Integer getUserIdByToken(String token) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserIdByToken", token);
	}

	@Override
	public String getReviewRegistableYn(Map<String, Object> reviewRestrictMap) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getReviewRegistableYn", reviewRestrictMap);
	}

}
