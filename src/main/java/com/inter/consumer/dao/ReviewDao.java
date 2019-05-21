package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface ReviewDao {

	Integer getUserIdByToken(String token);

	Integer getOrderNumberBySequence(Long sequence);

	void insertProdReview(Map<String, String> param);

	String getBizNameByOrderSeq(Map<String, String> param);
	
	String getProdNameByOrderSeq(Map<String, String> param);

	List<Map<String, Object>> getReviewList(Map<String, String> param);

}
