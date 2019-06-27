package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface DetailInfoDao {

	String getUserAuthByToken(String token);

	Map<String, Object> getCurrentSeqInfo(Map<String, String> param);

	List<Map<String, Object>> getDetailInfo(Map<String, String> param);

	Map<String, Object> getReviewInfo(Map<String, String> param);

	List<String> getImgPathByGroupUUID(String groupUUID);

	Map<String, Object> getDetailContent(Map<String, String> param);

	// CN
	Map<String, Object> getCurrentSeqInfoCN(Map<String, String> param);

	List<Map<String, Object>> getIconColorInfo(Map<String, String> param);

	List<Map<String, Object>> getDetailinfoCN(Map<String, String> param);

}
