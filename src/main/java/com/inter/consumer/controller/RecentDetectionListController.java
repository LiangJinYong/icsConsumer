package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.RecentDetectionListService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class RecentDetectionListController {

	@Autowired
	private RecentDetectionListService recentDetectionListService;

	@RequestMapping("/recentDetectionList")
	@ResponseBody
	public String recentDetectionList(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);

		RequestParamUtil.putUrlHeader(request, param);
		
		String token = request.getHeader("token");
		param.put("token", token);

		String result = recentDetectionListService.recentDetectionList(param);
		return result;
	}
}
