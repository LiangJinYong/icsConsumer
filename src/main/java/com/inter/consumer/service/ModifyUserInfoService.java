package com.inter.consumer.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ModifyUserInfoService {

	String modifyUserInfo(Map<String, String> param, HttpServletRequest request);

	String modifyUserProfile(Map<String, String> param, HttpServletRequest request);

}
