package com.yhb.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhb.dao.entity.Order;

public class QueryUtiL {

	

	public static Field[] getAllFields(Object object) {
		Class<?> clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;

	}
	public static <T> Map<String, Object> convertMap(T t) throws IllegalArgumentException, IllegalAccessException{
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = getAllFields(t);
		for(Field field : fields) {
			field.setAccessible(true);
			Object obj = field.get(t);
			if(null!=obj) {
				map.put(field.getName(), field.get(t));
			}
			field.setAccessible(false);
		}
		return map;
	}
	
	
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Order order = new Order();
		order.setId("111");
		order.setOrderCode("sss");
		System.out.println(convertMap(order));
	}
}
