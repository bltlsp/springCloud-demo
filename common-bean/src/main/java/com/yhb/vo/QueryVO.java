package com.yhb.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryVO<T> implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -5810516101629965744L;
	
	
	private int pageNum;
	private int pageSize;
	private String orderBy;
	private T T;
}
