package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.MainPageDao;

@Repository
public class MainPageDaoImpl implements MainPageDao {

	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public Map<String, Object> getLatestNotice(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getLatestNotice", param);
	}

	@Override
	public List<Map<String, Object>> getRandomCorpImgList(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getRandomCorpImgList", param);
	}
	
	@Override
	public String getCorpNm(Map<String, Object> paramObj) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getCorpNm", paramObj);
	}

	@Override
	public List<Map<String, Object>> getProdListByCorp(Map<String, Object> paramObj) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getProdListByCorp", paramObj);
	}

	@Override
	public int getProdTotalPageNo(Map<String, Object> paramObj) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getProdTotalPageNo", paramObj);
	}

	@Override
	public Integer getReviewTotalPageNo(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getReviewTotalPageNo", param);
	}
	
	@Override
	public List<Map<String, Object>> getProdImgListByReview(Map<String, Object> paramObj) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getProdImgListByReview", paramObj);
	}

	@Override
	public Integer getUserIdByToken(String token) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserIdByToken", token);
	}

	@Override
	public Integer getDetectCount(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getDetectCount", param);
	}

	@Override
	public List<Map<String, Object>> getProdImgListByDetectRecord(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getProdImgListByDetectRecord", param);
	}

	@Override
	public List<Map<String, Object>> getReviewProdImgListPaging(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getReviewProdImgListPaging", param);
	}

	@Override
	public List<Map<String, Object>> getNoticeList(Map<String, Object> paramObj) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getNoticeList", paramObj);
	}

	@Override
	public Integer getNoticeTotalPage(Map<String, Object> paramObj) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getNoticeTotalPage", paramObj);
	}

}
