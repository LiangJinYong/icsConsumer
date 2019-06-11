package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface MemberLoginDao {

	Integer getUserIdByToken(String token);

	Integer getUserIdByIdPw(Map<String, String> param);

	Map<String, Object> getUserInfoById(Map<String, String> param);

	void deleteTokenByUserId(Integer userId);

	void insertTokenInfo(Map<String, String> param);

	Map<String, Object> checkUserExistence(Map<String, String> param);

	List<String> getImgPathByGroupUUID(String groupUUID);

}
