package com.inter.consumer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.DetailInfoDao;
import com.inter.consumer.dao.MemberLoginDao;
import com.inter.consumer.dao.ServiceCenterDao;
import com.inter.consumer.service.ServiceCenterService;
import com.inter.util.FileUtil;
import com.inter.util.ResultMessageUtil;

@Service
public class ServiceCenterServiceImpl implements ServiceCenterService {

	@Autowired
	private ServiceCenterDao serviceCenterDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Autowired
	private MemberLoginDao memberLoginDao;

	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private DetailInfoDao detailInfoDao;
	
	@Override
	public String reportAndQuestion(Map<String, String> param, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		Map<String, Object> paramObj = new HashMap<>();
		paramObj.putAll(param);
		
		String token = param.get("token");
		Integer userId = memberLoginDao.getUserIdByToken(token);
		
		if (userId == null) {
			
			result.put("resultCode", 403);

			messageUtil.addResultMsg(param, result);
			return gson.toJson(result);
		}
		
		paramObj.put("appUserId", userId);
		
		String type = param.get("type");
		
		String serviceType =  "01".equals(type) ? "QUES" : "REPO";
		
		try {
			List<Map<String, Object>> imgInfoList = fileUtil.parseInsertImgInfo(request, serviceType);
			
			if (imgInfoList.size() > 0) {
				paramObj.put("groupUUID", imgInfoList.get(0).get("groupUUID"));
				paramObj.put("imgInfoList", imgInfoList);
				
				serviceCenterDao.insertImg(paramObj);
			}
			
			serviceCenterDao.insertReportOrQuestion(paramObj);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("resultCode", 430);
			messageUtil.addResultMsg(param, result);
			return gson.toJson(result);
		}
		
		result.put("resultCode", 200);
		
		messageUtil.addResultMsg(param, result);
		return gson.toJson(result);
	}

	@Override
	public String getMyQuestionList(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String token = param.get("token");
		Integer userId = memberLoginDao.getUserIdByToken(token);
		
		if (userId == null) {
			
			result.put("resultCode", 403);

			messageUtil.addResultMsg(param, result);
			return gson.toJson(result);
		}
		param.put("appUserId", String.valueOf(userId));
		
		List<Map<String, Object>> myQuestionList = serviceCenterDao.getMyQuestionList(param);
		result.put("data", myQuestionList);
		
		result.put("resultCode", 200);
		messageUtil.addResultMsg(param, result);
		return gson.toJson(result);
	}

	@Override
	public String getAnswer(Map<String, String> param, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		Map<String, Object> paramObj = new HashMap<>();
		paramObj.putAll(param);
		
		String token = param.get("token");
		Integer userId = memberLoginDao.getUserIdByToken(token);
		
		if (userId == null) {
			
			result.put("resultCode", 403);

			messageUtil.addResultMsg(param, result);
			return gson.toJson(result);
		}
		
		Map<String, Object> answer = serviceCenterDao.getAnswerById(param);
		
		String groupUUID = (String) answer.get("groupUUID");
		if (groupUUID != null) {
			List<String> imgList = new ArrayList<>();
			
			List<String> imgPathList = detailInfoDao.getImgPathByGroupUUID(groupUUID);
			
			for(String imgPath : imgPathList) {
				imgList.add(param.get("urlHeader") + imgPath);
			}
			
			answer.remove("groupUUID");
			answer.put("imgList", imgList);
		}
		
		result.put("resultCode", 200);
		result.putAll(answer);
		
		messageUtil.addResultMsg(param, result);
		return gson.toJson(result);
	}

}
