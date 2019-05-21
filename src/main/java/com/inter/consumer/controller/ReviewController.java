package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.ReviewService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@RequestMapping("/registerReview")
	@ResponseBody
	public String registerReview(HttpServletRequest request) {
		
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = reviewService.registerReview(param);
		return result;
	}
	
	@RequestMapping("/getReviewList")
	@ResponseBody
	public String getReviewList(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		RequestParamUtil.putUrlHeader(request, param);
		
		String result = reviewService.getReviewList(param);
		return result;
	}
}
