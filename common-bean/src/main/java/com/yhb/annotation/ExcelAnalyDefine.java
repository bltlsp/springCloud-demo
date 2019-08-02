package com.yhb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * value excel 表头名称
 * type 值类型  string、int、double、date、boolean、timestamp(时间戳)
 * format 时间格式化
 * @author HaibinYang
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAnalyDefine {
	public String value();
	public String type() default "string";
	public String format() default "yyyy-MM-dd HH:mm:ss";
}

