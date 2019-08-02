package com.yhb.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

public class CommonUtil {
	public static String genUUID() {
		return UUID.randomUUID().toString().replaceAll("-","");  
	}
	
	
	public static String getExcelContanctStr(Map<String, String> fieldMap) {
		StringBuffer sb = new StringBuffer("");
		
		for(String fieldName : fieldMap.keySet()) {
			String type = fieldMap.get(fieldName);
			if(sb.length()<=0) {
				sb.append("\"start '\"");
			}
			sb.append(',');
			if("varchar".equals(type)) {
				sb.append("\"','\",A:A,\"','\"");
			}else {
				
			}
		}
		return sb.toString();
	}
	
	public static String getJsonStr(Object obj, Map<String, String> changetField, Map<String, Class<?>> type) {
		
		
		return "";
	}
	
	public static String collectionToString(Collection<String> coll, String reg) {
		StringBuffer sb = new StringBuffer("");
		if(CollectionUtils.isNotEmpty(coll)) {
			for(String str : coll) {
				if(sb.length()>0) {
					sb.append(reg);
				}
				sb.append(str);
			}
		}
		return sb.toString();
	}
	
	
	public static String formatToInSqlStr(Collection<String> coll) {
		StringBuffer sb = new StringBuffer("");
		if(CollectionUtils.isNotEmpty(coll)) {
			for(String str : coll) {
				if(sb.length()>0) {
					sb.append(',');
				}
				sb.append('\'');
				sb.append(str);
				sb.append('\'');
			}
		}
		return sb.toString();
	}
	
	public static String formatToInSqlStr(String strs, String reg) {
		return formatToInSqlStr(Arrays.asList(strs.split(reg)));
	}
	
	public static List<Field> getFields(Class<?> clazz) {
		List<Field> result = new ArrayList<Field>();
		result.addAll(Arrays.asList(clazz.getDeclaredFields()));
		if(clazz.getSuperclass()==null) {
			return result; 
		}else {
			result.addAll(getFields(clazz.getSuperclass()));
		}
		return result;
	}
	
	public static <T, S> void copyProperties(T t, S s) { 
		List<String> tField = CommonUtil.getFields(s.getClass()).stream().map(Field::getName).collect(Collectors.toList());
		List<String> sField = CommonUtil.getFields(t.getClass()).stream().map(Field::getName).collect(Collectors.toList());
		Iterator<String> it =  tField.iterator();
		while(it.hasNext()) {
			String field = it.next();
			if(sField.contains(field)) {
				it.remove();
			}
		}
		String [] arr = new String[tField.size()];
		tField.toArray(arr);
		BeanUtils.copyProperties(t, s, arr);
	}
	
	public static <T, S> List<S> copyProperties(List<T> tList, Class<S> sClass) throws InstantiationException, IllegalAccessException {
		List<S> sList = new ArrayList<S>(tList.size());
		if(CollectionUtils.isNotEmpty(tList)) {
			for(T t : tList) {
				S s = sClass.newInstance();
				CommonUtil.copyProperties(t, s);
				sList.add(s);
			}
		}
		return sList;
	}
	
	public static <T> T getValByWeight(Map<T, Integer> data) {
		List<Integer> index = new ArrayList<Integer>();
		Map<Integer, T> indexToData = new HashMap<Integer, T>();
		int ide = 0;
		for(T t : data.keySet()) {
			indexToData.put(ide, t);
			for(int i=0; i<data.get(t); i++) {
				index.add(ide);
			}
			ide++;
		}
		
		return indexToData.get(index.get(new Random().nextInt(index.size())));
	}
	
	public static void main(String[] args) {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("a", 1);
		data.put("b", 2);
		data.put("c", 7);
		
		int times = 10000;
		
		int a = 0;
		int b = 0;
		int c = 0;
		
		for(int i=0; i<times; i++) {
			String val = getValByWeight(data);
			if("a".equals(val)) {
				a++;
			}else if("b".equals(val)) {
				b++;
			}else if("c".equals(val)) {
				c++;
			}
		}
		
		System.out.println((double)a/times);
		System.out.println((double)b/times);
		System.out.println((double)c/times);
	}
}
