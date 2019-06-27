package com.inter.consumer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.DetailInfoDao;
import com.inter.consumer.service.DetailInfoService;
import com.inter.util.CommonCodeUtil;
import com.inter.util.ResultMessageUtil;

@Service
public class DetailInfoServiceImpl implements DetailInfoService {

	@Autowired
	private DetailInfoDao detailInfoDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Autowired
	private CommonCodeUtil commonCodeUtil;
	
	public String detailInfo(Map<String, String> param) {

		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();

		String token = param.get("token");
		
		String auth = "AU06";
		if (token != null) {
			auth = detailInfoDao.getUserAuthByToken(token);
 			if (auth == null) {
				auth = "AU06";
			}
		}
		
		param.put("auth", auth);
		
		Map<String, Object> currentSeqInfo = detailInfoDao.getCurrentSeqInfo(param);
		
		if (currentSeqInfo == null) {
			result.put("resultCode", 405);
			messageUtil.addResultMsg(param, result);
			return  gson.toJson(result);
		} else {
			int orderSeq = (int) currentSeqInfo.get("ORDER_SEQ");
			
			param.put("orderSeq", String.valueOf(orderSeq));
			
			Map<String, Object> data = new HashMap<>();
			
			List<Map<String, Object>> topList = new ArrayList<>();
			List<Map<String, Object>> defList = new ArrayList<>();
			List<Map<String, Object>> manList = new ArrayList<>();
			
			List<Map<String, Object>> detailInfo = detailInfoDao.getDetailInfo(param);
			
			Map<String, Object> reviewInfo = detailInfoDao.getReviewInfo(param);
			
			Iterator<Map<String, Object>> iterator = detailInfo.iterator();
			
			String urlHeader = param.get("urlHeader");
			
			String blockChainYn = "N";
			while (iterator.hasNext()) {
				Map<String, Object> detailItem = iterator.next();
				
				String typeCode = (String) detailItem.get("DETAIL_TYP_CD");
				
				String detailVal = (String) detailItem.get("DETAIL_VAL");
				
				Set<String> infoKeySet = currentSeqInfo.keySet();
				
				if ("N99".equals(typeCode)) {
						
					if (!infoKeySet.contains(detailVal) || ("DISTRIBUTION_YN".equals(detailVal) && "N".equals(currentSeqInfo.get("DISTRIBUTION_YN")))) {
						iterator.remove();
						continue;
					} else if ("PROD_EXP_DT".equals(detailVal)) {
						String prodExpDt = currentSeqInfo.get("REMAIN_DT") + "|" + currentSeqInfo.get(detailVal);
						detailItem.put("DETAIL_VAL", prodExpDt);
					} else {
						detailItem.put("DETAIL_VAL", currentSeqInfo.get(detailVal));
					}
				}
				
				if ("M00".equals(typeCode)) {
					result.put("colorCode", detailVal);
				} else if ("M01".equals(typeCode)) {
					result.put("productName", detailVal);
				} else if ("M02".equals(typeCode)) {
					String prodImgPath = getImgPath(urlHeader, detailVal);
					result.put("prodImgPath", prodImgPath);
				} else if ("M03".equals(typeCode)) {
					result.put("reviewCount", reviewInfo.get("REVIEW_CNT"));
				} else if ("M04".equals(typeCode)) {
					String reviewScore = (String) reviewInfo.get("REVIEW_SCORE");
					if (reviewScore != null) {
						result.put("reviewScore", reviewScore);
					}
				} else if ("M05".equals(typeCode)) {
					blockChainYn = detailVal;
					result.put("blockChainYn", detailVal);
				} else if ("M06".equals(typeCode) && "Y".equals(blockChainYn)) {
					param.put("codeId", "CHAIN_URL");
					param.put("codeValue", "GET_DIST_DATA");
					
					String blockChainURL = commonCodeUtil.getCommonCodeValueName(param);
					
					result.put("blockChainURL", blockChainURL + param.get("sequence") + "/");
				} else if ("M07".equals(typeCode) && "Y".equals(blockChainYn)) {
					param.put("codeValue", "GET_DELIVERY_DATA");
					String blockChainDeliveryURL = commonCodeUtil.getCommonCodeValueName(param);
					
					result.put("blockChainDeliveryURL", blockChainDeliveryURL);
				}
				String groupCode = (String) detailItem.get("DETAIL_GRP_CD");
				
				detailItem.remove("DETAIL_GRP_CD");
				
				if ("TOP".equals(groupCode)) {
					
					if ("N03".equals(typeCode)) {
						String imgPath = getImgPath(urlHeader, detailVal);
						detailItem.put("DETAIL_VAL", imgPath);
					} else if ("N06".equals(typeCode)) {
						if (!infoKeySet.contains(detailVal)) {
							iterator.remove();
							continue;
						} else {
							detailItem.put("DETAIL_VAL", currentSeqInfo.get(detailVal));
						}
					}
					param.put("codeId", "APP_ICON_FILE_DIR");
					param.put("codeValue", (String) detailItem.get("ICON_NM"));

					String iconPath = commonCodeUtil.getCommonCodeValueName(param);
					detailItem.put("ICON_NM", urlHeader + iconPath);
					
					topList.add(detailItem);
				} else if ("DEF".equals(groupCode)) {
					defList.add(detailItem);
				} else if ("MAN".equals(groupCode)) {
					if ("N03".equals(typeCode)) {
						String imgPath = getImgPath(urlHeader, detailVal);
						detailItem.put("DETAIL_VAL", imgPath);
					}
					manList.add(detailItem);
				}
			}
			
			Map<String, Object> detailContent = detailInfoDao.getDetailContent(param);
			
			if (detailContent != null) {
				for(int i=1; i<5; i++) {
					String content = (String) detailContent.get("detail_ctt" + i);
					
					if (content != null) {
						String[] split = content.split("\\^");
						
						if (split.length == 2) {
							Map<String, Object> contentMap = new HashMap<>();
							contentMap.put("DETAIL_TITLE", split[0]);
							contentMap.put("DETAIL_VAL", split[1]);
							contentMap.put("DETAIL_TYP_CD", "N01");
							defList.add(contentMap);
						}
					}
				}
			}
			
			data.put("TOP", topList);
			data.put("DEF", defList);
			data.put("MAN", manList);
			
			result.put("data", data);
		}
		
		result.put("resultCode", 200);
		
		messageUtil.addResultMsg(param, result);

		return gson.toJson(result);
	}

	private String getImgPath(String urlHeader, String groupUUID) {
		
		String serverPath = detailInfoDao.getImgPathByGroupUUID(groupUUID).get(0);
		
		return urlHeader + serverPath;
	}
}
