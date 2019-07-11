package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface MainPageDao {

	Map<String, Object> getLatestNotice(Map<String, String> param);

	List<Map<String, Object>> getRandomCorpImgList(Map<String, String> param);

	Integer getReviewTotalPageNo(Map<String, String> param);
	
	List<Map<String, Object>> getProdImgListByReview(Map<String, Object> paramObj);

	Integer getUserIdByToken(String token);

	Integer getDetectCount(Map<String, String> param);

	List<Map<String, Object>> getProdImgListByDetectRecord(Map<String, String> param);

	List<Map<String, Object>> getReviewProdImgListPaging(Map<String, String> param);

	List<Map<String, Object>> getNoticeList(Map<String, Object> paramObj);

	Integer getNoticeTotalPage(Map<String, Object> paramObj);

}
