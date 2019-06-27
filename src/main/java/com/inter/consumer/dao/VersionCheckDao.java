package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface VersionCheckDao {

	Map<String, Object> getVersionCheckInfo(Map<String, String> param);

	String getEncrptedKey();

	List<String> getQrExclusiveUrlList();

}
