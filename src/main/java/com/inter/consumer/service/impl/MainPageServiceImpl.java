package com.inter.consumer.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.DetailInfoDao;
import com.inter.consumer.dao.MainPageDao;
import com.inter.consumer.service.MainPageService;
import com.inter.util.ResultMessageUtil;

@Service
public class MainPageServiceImpl implements MainPageService {

	@Autowired
	private MainPageDao mainPageDao;
	
	@Autowired
	private DetailInfoDao detailInfoDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	private static final String[] SUPPORTED_LANGS = {"KR", "CN", "US", "VN"};
	
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
			brand.put("corpImgUrl", param.get("urlHeader") + brand.get("corpImgUrl"));
		}
		
		result.put("brandList", brandList);
		
		Map<String, Object> paramObj = new HashMap<>();
		paramObj.putAll(param);
		
		List<Map<String, Object>> reviewList = mainPageDao.getProdImgListByReview(paramObj);
		for(Map<String, Object> review : reviewList) {
			review.put("prodImgUrl", param.get("urlHeader") + review.get("prodImgUrl"));
		}
		
		result.put("reviewList", reviewList);
		
		
		Integer appUserId = mainPageDao.getUserIdByToken(param.get("token"));
		
		if (appUserId != null) {
			
			param.put("appUserId", String.valueOf(appUserId));
			
			String countryCode = param.get("countryCode");
			boolean contains = Arrays.asList(SUPPORTED_LANGS).contains(countryCode);
			
			if (!contains) {
				param.put("countryCode", "US");
			}
			
			Integer detectCount = mainPageDao.getDetectCount(param);
			result.put("detectCount", detectCount);
			
			List<Map<String, Object>> detectList = mainPageDao.getProdImgListByDetectRecord(param);
			for(Map<String, Object> detect : detectList) {
				detect.put("prodImgUrl", param.get("urlHeader") + detect.get("prodImgUrl"));
			}
			result.put("detectList", detectList);
		}
		
		result.put("resultCode", 200);
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}
	
	@Override
	public String getProdsByCorp(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		int currentPageNo = Integer.parseInt(param.get("currentPageNo"));
		int offset = (currentPageNo - 1) * 20;
		
		Map<String, Object> paramObj = new HashMap<>();
		paramObj.putAll(param);
		paramObj.put("offset", offset);
		
		String corpNm = mainPageDao.getCorpNm(paramObj);
		result.put("corpNm", corpNm);
		
		List<Map<String, Object>> prodList = mainPageDao.getProdListByCorp(paramObj);
		result.put("data", prodList);
		
		for(Map<String, Object> prod : prodList) {
			prod.put("prodImgUrl", param.get("urlHeader") + prod.get("prodImgUrl"));
		}
		
		int prodTotalPageNo = mainPageDao.getProdTotalPageNo(paramObj);
		result.put("prodTotalPageNo", prodTotalPageNo);
		
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
			review.put("prodImgUrl", param.get("urlHeader") + review.get("prodImgUrl"));
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
				detect.put("prodImgUrl", param.get("urlHeader") + detect.get("prodImgUrl"));
			}
			result.put("detectList", detectList);
		}
		
		result.put("resultCode", 200);
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

	@Override
	public String getNoticeList(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		Map<String, Object> paramObj = new HashMap<>();
		paramObj.putAll(param);
		
		String currentPageNo = param.get("currentPageNo");
		
		if (currentPageNo != null) {
			Integer currentPageNoInt =  Integer.parseInt(param.get("currentPageNo"));
			if (currentPageNoInt != null) {
				paramObj.put("offset", (currentPageNoInt - 1) * 20);
			}
		}
		
		Integer noticeTotalPageNo = mainPageDao.getNoticeTotalPage(paramObj);
		result.put("noticeTotalPageNo", noticeTotalPageNo);
		
		List<Map<String, Object>> noticeList = mainPageDao.getNoticeList(paramObj);
		
		for(Map<String, Object> notice : noticeList) {
			String groupUUID = (String) notice.get("groupUUID");
			
			if (groupUUID != null) {
				List<String> imgList = new ArrayList<>();
				
				List<String> imgPathList = detailInfoDao.getImgPathByGroupUUID(groupUUID);
				
				for(String imgPath : imgPathList) {
					imgList.add(param.get("urlHeader") + imgPath);
				}
				
				notice.remove("groupUUID");
				notice.put("imgList", imgList);
			}
		}
		
		result.put("noticeList", noticeList);
		result.put("resultCode", 200);
		return gson.toJson(result);
	}

}
