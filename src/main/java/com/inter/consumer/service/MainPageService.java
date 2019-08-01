package com.inter.consumer.service;

import java.util.Map;

public interface MainPageService {

	String getMainPageInfo(Map<String, String> param);

	String getProdsByCorp(Map<String, String> param);
	
	String getProdImgsByReview(Map<String, String> param);

	String getProdImgsByDetectRecord(Map<String, String> param);

	String getNoticeList(Map<String, String> param);
}
