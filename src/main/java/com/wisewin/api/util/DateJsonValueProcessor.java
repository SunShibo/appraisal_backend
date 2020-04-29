package com.wisewin.api.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String LONG_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private DateFormat dateFormat;

	/**
	 * 构造方法
	 * 
	 * @param datePattern
	 *            日期格式
	 */
	public DateJsonValueProcessor(String datePattern) {

		if (null == datePattern)
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		else
			dateFormat = new SimpleDateFormat(datePattern);

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang
	 * .Object, net.sf.json.JsonConfig)
	 */
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		// TODO 自动生成方法存根
		return process(arg0);
	}

	/*
	 * Javadoc）
	 * 
	 * @see
	 * net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang
	 * .String, java.lang.Object, net.sf.json.JsonConfig)
	 */
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		// TODO 自动生成方法存根
		return process(arg1);
	}

	private Object process(Object value) {
		if(null == value) return "";
		return dateFormat.format((Date) value);
	}

}
