package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.ReviewDao;

@Repository
public class ReviewDaoImpl implements ReviewDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public Integer getUserIdByToken(String token) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserIdByToken", token);
	}

	@Override
	public Integer getOrderNumberBySequence(Long sequence) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getOrderNumberBySequence", sequence);
	}

	@Override
	public void insertProdReview(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertProdReview", param);
	}

	@Override
	public String getProdNameByOrderSeq(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getProdNameByOrderSeq", param);
	}
	
	@Override
	public String getBizNameByOrderSeq(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getBizNameByOrderSeq", param);
	}

	@Override
	public List<Map<String, Object>> getReviewList(Map<String, Object> paramObj) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getReviewList", paramObj);
	}

	@Override
	public Integer getReviewTotalPageByOrder(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getReviewTotalPageByOrder", param);
	}

}
