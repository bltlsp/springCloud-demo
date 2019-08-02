package com.yhb.constant;

public interface CommonConstant {
	//id字段
	public static final String COLUMN_ID = "id";
	//软删除字段
	public static final String COLUMN_IS_DEL = "isDel";
	
	public static final String COLUMN_CREATE_BY = "create_by";
	
	public static final String COLUMN_CREATE_TIME = "create_time";
	
	public static final String COLUMN_UPDATE_BY = "update_by";
	
	public static final String COLUMN_UPDATE_TIME = "update_time";
	
	//正常数据
	public static final int STATUS_ACTIVE = 0;
	//标记为删除的数据
	public static final int STATUS_DEL = 1;
	
	public static final String RESPONSE_CODE_SUCCESS = "1";
}
