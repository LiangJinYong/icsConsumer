package com.inter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.inter.consumer.dao.FileIdDao;

@Component
public class FileUtil {

	private static final String dirPath = "c:/dev/updateImg";
//	private static final String dirPath = "/var/www/html/Images/detail/profile";
	
	@Autowired
	private FileIdDao fileIdDao;

	public List<Map<String, Object>> parseInsertImgInfo(HttpServletRequest request) throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		Iterator<String> fileNames = multipartRequest.getFileNames();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> fileItemMap = null;

		int fileUUID = 0;
		while (fileNames.hasNext()) {
			
			MultipartFile multipartFile = multipartRequest.getFile(fileNames.next());

			String groupUUID = null;
			
			if (!multipartFile.isEmpty()) {
				
				if (fileUUID == 0) {
					groupUUID = fileIdDao.getGroupUUID();
				}
				
				String fileNameMap = groupUUID + "_" + fileUUID;
				
				String fileName = multipartFile.getOriginalFilename();
				long size = multipartFile.getSize();
				String serverFullPath = dirPath + makeImgUploadDir(groupUUID);
				
				File dir = new File(serverFullPath);
				if (!dir.exists()) {
					boolean mkdirs = dir.mkdirs();
				}
				
				File file = new File(serverFullPath  + fileName);
				multipartFile.transferTo(file);
				copyFile(serverFullPath  + fileName, serverFullPath + fileNameMap);
						
				fileItemMap = new HashMap<>();
				
				fileItemMap.put("fileName", fileName);
				fileItemMap.put("fileSize", size);
				fileItemMap.put("serverFullName", serverFullPath + fileNameMap);
				fileItemMap.put("fileNameMap", fileNameMap);
				fileItemMap.put("fileUUID", fileUUID);
				fileItemMap.put("groupUUID", groupUUID);
				
				result.add(fileItemMap);
			}

			fileUUID++;
		}
		
		return result;
	}

	private String makeImgUploadDir(String groupUUID) {
		StringBuilder dir = new StringBuilder();
		dir.append("/").append(groupUUID.substring(0, 4)) //
				.append("/").append(groupUUID.substring(4, 6)) //
				.append("/").append(groupUUID.substring(6, 8)) //
				.append("/").append(groupUUID.substring(9)) //
				.append("/");

		return dir.toString();
	}
	
	private void copyFile(String oldAddress, String newAddress) throws Exception {
        FileInputStream input=new FileInputStream(oldAddress);
        FileOutputStream output=new FileOutputStream(newAddress);
        int in=input.read();
        while(in!=-1){
            output.write(in);
            in=input.read();
        }
        input.close();
        output.close();
    }
}