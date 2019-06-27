package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.ServiceCenterService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class ServiceCenterController {

	@Autowired
	private ServiceCenterService serviceCenterService;
	
	@RequestMapping("/reportAndQuestion")
	@ResponseBody
	public String reportAndQuestion(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		RequestParamUtil.putUrlHeader(request, param);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = serviceCenterService.reportAndQuestion(param, request);
		return result;
	}
	
	@RequestMapping("/getMyQuestionList")
	@ResponseBody
	public String getMyQuestionList(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = serviceCenterService.getMyQuestionList(param);
		return result;
	}
	
	@RequestMapping("/getAnswer")
	@ResponseBody
	public String getAnswer(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		RequestParamUtil.putUrlHeader(request, param);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = serviceCenterService.getAnswer(param, request);
		return result;
	}
}
