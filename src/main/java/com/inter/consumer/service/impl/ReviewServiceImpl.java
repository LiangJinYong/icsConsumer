package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.DetailInfoDao;
import com.inter.consumer.dao.ReviewDao;
import com.inter.consumer.service.ReviewService;
import com.inter.util.ResultMessageUtil;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Autowired
	private DetailInfoDao detailInfoDao;
	
	@Override
	public String registerReview(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String token = param.get("token");
		
		Integer appUserId = reviewDao.getUserIdByToken(token);
		
		if (appUserId != null) {
			param.put("appUserId", String.valueOf(appUserId));
			Long sequence = Long.parseLong(param.get("sequence"));
			Integer orderSeq = reviewDao.getOrderNumberBySequence(sequence);
			
			param.put("orderSeq", String.valueOf(orderSeq));
			
			try {
				reviewDao.insertProdReview(param);
				result.put("resultCode", 200);
			} catch (Exception e) {
				result.put("resultCode", 500);
			}
		} else {
			result.put("resultCode", 403);
		}
		
		messageUtil.addResultMsg(param, result);
		return gson.toJson(result);
	}

	@Override
	public String getReviewList(Map<String, String> param) {

		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String sequence = param.get("sequence");
		if (!"0".equals(sequence)) {
			Long sequenceLong = Long.parseLong(sequence);
			Integer orderSeq = reviewDao.getOrderNumberBySequence(sequenceLong);
			param.put("orderSeq", String.valueOf(orderSeq));
		}
		
		String prodName = reviewDao.getProdNameByOrderSeq(param);
		result.put("prodName", prodName);
		
		String bizName = reviewDao.getBizNameByOrderSeq(param);
		result.put("bizName", bizName);
		
		// Count & Score
		Map<String, Object> reviewInfo = detailInfoDao.getReviewInfo(param);
		result.putAll(reviewInfo);
		
		List<Map<String, Object>> reviewList = reviewDao.getReviewList(param);
		
		// substitute from groupUUID to URL for profile image
		for(Map<String, Object> review : reviewList) {
			String groupUUID = (String) review.get("profileURL");
			
			if (groupUUID != null) {
				String imgPath = detailInfoDao.getImgPathByGroupUUID(groupUUID).get(0);
				review.put("profileURL", param.get("urlHeader") + imgPath);
			}
		}
		
		result.put("reviewList", reviewList);
		
		result.put("resultCode", 200);
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
