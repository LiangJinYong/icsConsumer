package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface ServiceCenterDao {

	void insertReportOrQuestion(Map<String, Object> paramObj);

	void insertImg(Map<String, Object> paramObj);

	List<Map<String, Object>> getMyQuestionList(Map<String, String> param);

}
