package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface DetailInfoDao {

	String getUserAuthByToken(String token);

	Map<String, Object> getCurrentSeqInfo(Map<String, String> param);

	List<Map<String, Object>> getDetailInfo(Map<String, String> param);

	Map<String, Object> getReviewInfo(Map<String, String> param);

	String getImgPathByGroupUUID(String groupUUID);

}
