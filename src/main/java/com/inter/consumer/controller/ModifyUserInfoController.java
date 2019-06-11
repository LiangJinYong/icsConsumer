package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.ModifyUserInfoService;
import com.inter.util.RequestParamUtil;

@Controller("modifyUserInfoControllerForConsumer")
@RequestMapping("/consumer")
public class ModifyUserInfoController {

	@Autowired
	private ModifyUserInfoService modifyUserInfoService;
	
	@RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public String modifyUserInfo(HttpServletRequest request) {
		
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		RequestParamUtil.putUrlHeader(request, param);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = modifyUserInfoService.modifyUserInfo(param);
		return result;
	}
	
	@RequestMapping(value = "/modifyUserProfile", method = RequestMethod.POST)
	@ResponseBody
	public String modifyUserProfile(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		RequestParamUtil.putUrlHeader(request, param);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = modifyUserInfoService.modifyUserProfile(param, request);
		return result;
	}
}
