package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.MainPageService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class MainPageController {

	@Autowired
	private MainPageService mainPageService;
	
	@RequestMapping("/getMainPageInfo")
	@ResponseBody
	public String getMainPageInfo(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		RequestParamUtil.putUrlHeader(request, param);
		
		String result = mainPageService.getMainPageInfo(param);
		return result;
	}
	
	@RequestMapping("/getProdImgsByReview")
	@ResponseBody
	public String getProdImgsByReview(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		RequestParamUtil.putUrlHeader(request, param);
		
		String result = mainPageService.getProdImgsByReview(param);
		return result;
	}
	
	@RequestMapping("/getProdImgsByDetectRecord")
	@ResponseBody
	public String getProdImgsByDetectRecord(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		RequestParamUtil.putUrlHeader(request, param);
		
		String result = mainPageService.getProdImgsByDetectRecord(param);
		return result;
	}
	
	@RequestMapping("/getNoticeList")
	@ResponseBody
	public String getNoticeList(HttpServletRequest request) {
		
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		RequestParamUtil.putUrlHeader(request, param);
		
		String result = mainPageService.getNoticeList(param);
		return result;
	}
}
