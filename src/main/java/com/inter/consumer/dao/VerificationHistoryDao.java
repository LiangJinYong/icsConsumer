package com.inter.consumer.dao;

import java.util.Map;

public interface VerificationHistoryDao {

	Map<String, Object> getOrderInfo(String sequence);

	Integer getUserNoByPhoneNumber(String mobilePhoneNumber);

	void insertFailLog(Map<String, String> param);

	void insertSuccessLog(Map<String, String> param);

	void insertExtendedDetailInfo(Map<String, String> param);

	String getTokenByUserId(String appUserId);

	Map<String, String> getGenderBirthById(String appUserId);

	String getRuleCheckCode(Map<String, String> param);

	String getRuleCheckMsg(Map<String, String> param);

	void insertFailureReportInfo(Map<String, Object> reportMap);

}
