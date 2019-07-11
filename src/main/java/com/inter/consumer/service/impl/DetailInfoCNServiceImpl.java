package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.DetailInfoDao;
import com.inter.consumer.dao.MessageDao;
import com.inter.consumer.service.DetailInfoCNService;
import com.inter.util.ResultMessageUtil;

@Service
public class DetailInfoCNServiceImpl implements DetailInfoCNService {

	@Autowired
	private DetailInfoDao detailInfoDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Autowired
	private MessageDao messageDao;

	@Override
	public String detailInfoCN(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		param.put("countryCode", "CN");
		
		String sequence = param.get("sequence");
		
		Map<String, Object> currentSeqInfoCN = detailInfoDao.getCurrentSeqInfoCN(param);
		
		if (currentSeqInfoCN != null) {

			putIntoResult(currentSeqInfoCN, "detectCount", result);
			putIntoResult(currentSeqInfoCN, "lastDetectTime", result);
			putIntoResult(currentSeqInfoCN, "lastAddress", result);
			putIntoResult(currentSeqInfoCN, "logisticsCount", result);
			
			// Handle paramColor and logos
			param.put("orderSeq", String.valueOf(currentSeqInfoCN.get("orderSeq")));
			
			List<Map<String, Object>> iconColorInfo = detailInfoDao.getIconColorInfo(param);
			
			if (iconColorInfo == null || iconColorInfo.size() == 0) {
				String mainLanguage = detailInfoDao.getMainLanguage(param);
				param.put("countryCode", mainLanguage);
				
				iconColorInfo = detailInfoDao.getIconColorInfo(param);
			}
			
			for(Map<String, Object> iconColor : iconColorInfo) {
				if ("MATR".equals(iconColor.get("iconName"))) {
					String groupUUID = (String) iconColor.get("value");
					
					if (groupUUID != null) {
						List<String> imgList = detailInfoDao.getImgPathByGroupUUID(groupUUID);
						
						if (imgList.size() > 0) {
							String imgPath = imgList.get(0);
							result.put("rawMaterialPath", param.get("urlHeader") + imgPath);
						}
					}
				} else if ("NUTR".equals(iconColor.get("iconName"))) {
					String groupUUID = (String) iconColor.get("value");
					
					if (groupUUID != null) {
						List<String> imgList = detailInfoDao.getImgPathByGroupUUID(groupUUID);
						
						if (imgList.size() > 0) {
							String imgPath = imgList.get(0);
							result.put("foodNutritionPath", param.get("urlHeader") + imgPath);
						}
					}
				} else if ("COLOR_VAL".equals(iconColor.get("title"))) {
					result.put("paramColor", iconColor.get("value"));
					result.put("textColor", "1");
				} else if ("M02".equals(iconColor.get("typeCode"))) {
					String groupUUID = (String) iconColor.get("value");
					
					if (groupUUID != null) {
						List<String> imgList = detailInfoDao.getImgPathByGroupUUID(groupUUID);
						
						if (imgList.size() > 0) {
							String imgPath = imgList.get(0);
							result.put("bizLogoPath", param.get("urlHeader") + imgPath);
						}
					}
				}
			}
			
			// Detail List
			List<Map<String, Object>> detailInfo = detailInfoDao.getDetailinfoCN(param);
			
			String prodMakeDt = (String) currentSeqInfoCN.get("PROD_MAKE_DT");
			String prodOutDt = (String) currentSeqInfoCN.get("PROD_OUT_DT");
			String prodExpDt = (String) currentSeqInfoCN.get("PROD_EXP_DT");
			
			ListIterator<Map<String, Object>> iterator = detailInfo.listIterator();
			
			while(iterator.hasNext()) {
				Map<String, Object> detailItem = iterator.next();
				Object content = detailItem.get("content");
				
				if ("PROD_MAKE_DT".equals(content)) {
					if (prodMakeDt != null) {
						detailItem.put("content", prodMakeDt);
					} else {
						detailItem.put("content", null);
					}
				} else if ("PROD_OUT_DT".equals(content)) {
					if (prodOutDt != null) {
						detailItem.put("content", prodOutDt);
					} else {
						detailItem.put("content", null);
					}
				} else if ("PROD_EXP_DT".equals(content)) {
					if (prodExpDt != null) {
						detailItem.put("content", prodExpDt);
						Map<String, Object> remainDt = new HashMap<>();
						
						param.put("resultCode", "M02");
						String prodRemainDays = messageDao.getResultMessage(param);
						remainDt.put("title", prodRemainDays);// "保质期剩余时间"
						long remainDays = (long) currentSeqInfoCN.get("REMAIN_DT");
						
						if (remainDays <= 0) {
							param.put("resultCode", "M03");
							String expired = messageDao.getResultMessage(param);
							remainDt.put("content", expired); //"已过保质期"
						} else {
							remainDt.put("content", currentSeqInfoCN.get("REMAIN_DT"));
						}
						
						iterator.add(remainDt);
					} else {
						detailItem.put("content", null);
					}
				} else if ("SEQUENCE".equals(content)) {
					detailItem.put("content", sequence);
				}
			}
			
			result.put("detail", detailInfo);
			
			result.put("resultCode", 200);
			
		} else {
			result.put("resultCode", 415);
		}
		
		messageUtil.addResultMsg(param, result);
		return gson.toJson(result);
	}

	private void putIntoResult(Map<String, Object> seqInfo, String resultKey, Map<String, Object> result) {
		Object value = seqInfo.get(resultKey);
		if (value != null) {
			result.put(resultKey, value);
		}
	}
}
