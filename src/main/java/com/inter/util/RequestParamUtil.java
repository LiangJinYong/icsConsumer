package com.inter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class RequestParamUtil {

	private RequestParamUtil() {}
	
	// convert request parameter type from Map<String, String[]> to Map<String, Stirng>
	public static Map<String, String> getParamMap(Map<String, String[]> param) {
		Map<String, String> paramMap = new HashMap<String, String>();
		Set<Entry<String,String[]>> entrySet = param.entrySet();
		for(Entry<String, String[]> entry : entrySet) {
			paramMap.put(entry.getKey(), entry.getValue()[0]);
		}
		
		return paramMap;
	}
	
	public static void putUrlHeader(HttpServletRequest request, Map<String, String> param) {
		
		String requestURL = request.getRequestURL().toString();
		int serverPort = request.getServerPort();
		
		int index = requestURL.indexOf(String.valueOf(serverPort));
		String urlHeader = requestURL.substring(0,index) + serverPort;
		param.put("urlHeader", urlHeader);
	}
}
