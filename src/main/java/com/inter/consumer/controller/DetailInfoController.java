package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.DetailInfoService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class DetailInfoController {

	@Autowired
	private DetailInfoService detailInfoService;

	@RequestMapping("/detailInfo")
	@ResponseBody
	public String detailInfo(HttpServletRequest request) {
		
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		RequestParamUtil.putUrlHeader(request, param);

		String token = request.getHeader("token");
		if (token != null) {
			param.put("token", token);
		}
		
		String result = detailInfoService.detailInfo(param);
		
		return result;
	}
	
	@RequestMapping("/detailInfoByOrder")
	@ResponseBody
	public String detailInfoByOrder(HttpServletRequest request) {
		
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		RequestParamUtil.putUrlHeader(request, param);

		String token = request.getHeader("token");
		if (token != null) {
			param.put("token", token);
		}
		
		String result = detailInfoService.detailInfoByOrder(param);
		
		return result;
	}
}
