package com.inter.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inter.consumer.dao.CommonCodeDao;

@Component
public class CommonCodeUtil {

	@Autowired
	private CommonCodeDao commonCodeDao;

	public String getCommonCodeValueName(Map<String, String> param) {

		return commonCodeDao.getCommonCodeValueName(param);
	}
}
