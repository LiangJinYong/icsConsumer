package com.inter.consumer.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ServiceCenterService {

	String reportAndQuestion(Map<String, String> param, HttpServletRequest request);

	String getMyQuestionList(Map<String, String> param);

}
