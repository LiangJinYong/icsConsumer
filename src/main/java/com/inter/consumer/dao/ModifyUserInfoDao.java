package com.inter.consumer.dao;

import java.util.Map;

public interface ModifyUserInfoDao {

	void updateUserDtlInfo(Map<String, String> param);

	void updateUserImgId(Map<String, Object> paramObj);

	void insertImg(Map<String, Object> paramObj);

}
