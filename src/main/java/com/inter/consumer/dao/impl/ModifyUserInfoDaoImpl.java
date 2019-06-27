package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.ModifyUserInfoDao;

@Repository("modifyUserInfoDaoForConsumer")
public class ModifyUserInfoDaoImpl implements ModifyUserInfoDao {

	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public void updateUserDtlInfo(Map<String, String> param) {
		sqlSessionTemplate.update(NAMESPACE + "updateUserDtlInfo", param);
	}

	@Override
	public void updateUserImgId(Map<String, Object> paramObj) {
		sqlSessionTemplate.update(NAMESPACE + "updateUserImgId", paramObj);
	}

	@Override
	public void insertImg(Map<String, Object> paramObj) {
		sqlSessionTemplate.insert(NAMESPACE + "insertImg", paramObj);
	}

}
