package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.PhysicalDistributionListDao;

@Repository
public class PhysicalDistributionListDaoImpl implements PhysicalDistributionListDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map<String, Object>> queryPhysicalDistributionInfo(String sequence) {
		
		return sqlSessionTemplate.selectList(NAMESPACE + "queryPhysicalDistributionInfo", sequence);
	}

	public int getUserCountByIdToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserCountByIdToken", param);
	}

}
