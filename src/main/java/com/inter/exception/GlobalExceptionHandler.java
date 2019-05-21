package com.inter.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseBody
	public String fileSizeExceeded() {
		
		return "fileSizeExceeded";
	}
}
