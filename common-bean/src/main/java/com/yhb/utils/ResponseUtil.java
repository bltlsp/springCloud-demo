package com.yhb.utils;

import com.yhb.constant.CommonConstant;
import com.yhb.vo.ResponseVO;

public class ResponseUtil {

	public static <T> ResponseVO<T> success(T t) {
		ResponseVO<T> response = new ResponseVO<T>();
		response.setCode(CommonConstant.RESPONSE_CODE_SUCCESS);
		response.setData(t);
		return response;
	}
}
