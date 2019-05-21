package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.MemberLoginDao;
import com.inter.consumer.dao.ModifyUserInfoDao;
import com.inter.consumer.service.ModifyUserInfoService;
import com.inter.util.FileUtil;
import com.inter.util.ResultMessageUtil;

@Service("modifyUserInfoServiceForConsumer")
public class ModifyUserInfoServiceImpl implements ModifyUserInfoService {

	@Autowired
	private ModifyUserInfoDao modifyUserInfoDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Autowired
	private MemberLoginDao memberLoginDao;

	@Autowired
	private FileUtil fileUtil;
	
	@Transactional
	public String modifyUserInfo(Map<String, String> param, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String token = param.get("token");
		Integer userId = memberLoginDao.getUserIdByToken(token);
		
		if (userId == null || !String.valueOf(userId).equals(param.get("appUserId"))) {
			result.put("resultCode", 403);

			messageUtil.addResultMsg(param, result);
			return gson.toJson(result);
		}
		
		modifyUserInfoDao.updateUserDtlInfo(param);
		
		Map<String, Object> userInfo = memberLoginDao.getUserInfoById(param);
		
		String groupUUID = (String) userInfo.get("groupUUID");
		
		if (groupUUID != null) {
			String urlHeader = param.get("urlHeader");
			String serverPath = memberLoginDao.getImgPathByGroupUUID(groupUUID);
			
			userInfo.put("img", urlHeader + serverPath);
		}
		
		result.putAll(userInfo);
		result.put("token", token);
		
		result.put("resultCode", 200);
		messageUtil.addResultMsg(param, result);
		return gson.toJson(result);
	}

	@Transactional
	public String modifyUserProfile(Map<String, String> param, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		Map<String, Object> paramObj = new HashMap<>();
		paramObj.putAll(param);
		
		String token = param.get("token");
		Integer userId = memberLoginDao.getUserIdByToken(token);
		
		if (userId == null || !String.valueOf(userId).equals(param.get("appUserId"))) {
			result.put("resultCode", 403);

			messageUtil.addResultMsg(param, result);
			return gson.toJson(result);
		}
		
		try {
			List<Map<String, Object>> imgInfoList = fileUtil.parseInsertImgInfo(request);
			
			if (imgInfoList.size() > 0) {
				paramObj.put("groupUUID", imgInfoList.get(0).get("groupUUID"));
				paramObj.put("imgInfoList", imgInfoList);
				
				modifyUserInfoDao.updateUserImgId(paramObj);
				modifyUserInfoDao.insertImg(paramObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("resultCode", 430);
			messageUtil.addResultMsg(param, result);
			return gson.toJson(result);
		}
		
		Map<String, Object> userInfo = memberLoginDao.getUserInfoById(param);
		
		String groupUUID = (String) userInfo.get("groupUUID");
		
		if (groupUUID != null) {
			String urlHeader = param.get("urlHeader");
			String serverPath = memberLoginDao.getImgPathByGroupUUID(groupUUID);
			
			userInfo.put("img", urlHeader + serverPath);
		}
		
		result.putAll(userInfo);
		result.put("token", token);
		
		result.put("resultCode", 200);
		
		messageUtil.addResultMsg(param, result);
		return gson.toJson(result);
	}

}
