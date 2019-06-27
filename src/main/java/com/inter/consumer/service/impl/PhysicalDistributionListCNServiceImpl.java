package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.PhysicalDistributionListDao;
import com.inter.consumer.service.PhysicalDistributionListCNService;
import com.inter.util.ResultMessageUtil;

@Service
public class PhysicalDistributionListCNServiceImpl implements PhysicalDistributionListCNService {

	@Autowired
	private PhysicalDistributionListDao physicalDistributionListDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Override
	@Transactional
	public String physicalDistributionList(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String sequence = param.get("sequence");
		List<Map<String, Object>> physicalDistributionInfo = physicalDistributionListDao.queryPhysicalDistributionInfo(sequence);
		result.put("resultCode", 200);
		result.put("data", physicalDistributionInfo);
		
		Gson gson = new Gson();
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}
}
