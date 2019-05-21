package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.MainPageDao;
import com.inter.consumer.service.MainPageService;
import com.inter.util.ResultMessageUtil;

@Service
public class MainPageServiceImpl implements MainPageService {

	@Autowired
	private MainPageDao mainPageDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Override
	public String getMainPageInfo(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		Map<String, Object> notice = mainPageDao.getLatestNotice(param);
		result.put("notice", notice);

		Integer reviewTotalPageNo = mainPageDao.getReviewTotalPageNo(param);
		result.put("reviewTotalPageNo", reviewTotalPageNo);
		
		List<Map<String, Object>> brandList = mainPageDao.getRandomCorpImgList(param);
		
		for(Map<String, Object> brand : brandList) {
			brand.put("corpImgURL", param.get("urlHeader") + brand.get("corpImgURL"));
		}
		
		result.put("brandList", brandList);
		
		Map<String, Object> paramObj = new HashMap<>();
		paramObj.putAll(param);
		
		List<Map<String, Object>> reviewList = mainPageDao.getProdImgListByReview(paramObj);
		for(Map<String, Object> review : reviewList) {
			review.put("prodImgURL", param.get("urlHeader") + review.get("prodImgURL"));
		}
		
		result.put("reviewList", reviewList);
		
		
		Integer appUserId = mainPageDao.getUserIdByToken(param.get("token"));
		
		if (appUserId != null) {
			
			param.put("appUserId", String.valueOf(appUserId));
			
			Integer detectCount = mainPageDao.getDetectCount(param);
			result.put("detectCount", detectCount);
			
			List<Map<String, Object>> detectList = mainPageDao.getProdImgListByDetectRecord(param);
			for(Map<String, Object> detect : detectList) {
				detect.put("prodImgURL", param.get("urlHeader") + detect.get("prodImgURL"));
			}
			result.put("detectList", detectList);
		}
		
		result.put("resultCode", 200);
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

	@Override
	public String getProdImgsByReview(Map<String, String> param) {

		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		int currentPageNo = Integer.parseInt(param.get("currentPageNo"));
		int offset = (currentPageNo - 1) * 20;
		
		Map<String, Object> paramObj = new HashMap<>();
		paramObj.putAll(param);
		paramObj.put("offset", offset);
		
		List<Map<String, Object>> reviewList = mainPageDao.getProdImgListByReview(paramObj);
		
		for(Map<String, Object> review : reviewList) {
			review.put("prodImgURL", param.get("urlHeader") + review.get("prodImgURL"));
		}
		
		result.put("reviewList", reviewList);
		
		result.put("resultCode", 200);
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

	@Override
	public String getProdImgsByDetectRecord(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		Integer appUserId = mainPageDao.getUserIdByToken(param.get("token"));
		
		if (appUserId != null) {
			param.put("appUserId", String.valueOf(appUserId));
			
			List<Map<String, Object>> detectList = mainPageDao.getProdImgListByDetectRecord(param);
			for(Map<String, Object> detect : detectList) {
				detect.put("prodImgURL", param.get("urlHeader") + detect.get("prodImgURL"));
			}
			result.put("detectList", detectList);
		}
		
		result.put("resultCode", 200);
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

	@Override
	public String getNoticeList(Map<String, String> param) {
		
		List<Map<String, Object>> noticeList = mainPageDao.getNoticeList(param);
		
		for(Map<String, Object> notice : noticeList) {
			String groupUUID = (String) notice.get("groupUUID");
			
			if (notice.get("groupUUID") != null) {
				
			}
		}
		return null;
	}

}
