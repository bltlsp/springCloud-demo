package com.yhb.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseVO<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5672725017513737L;
	
	/**
	 * 1 成功  其他失败
	 */
	private String code;
	
	/**
	 * 描述
	 */
	private String msg;
	
	/**
	 * 数据
	 */
	private T data;
}
