package com.dripop.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

/**
 * 自定义json工具类
 */
public class JsonUtil {

	public static String toFullJson(Object object) {
		String result = JSON.toJSONString(object,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
		return result;
	}

	public static String toAllJson(Object object) {
		Object obj = null;
		try {
			obj = BeanUtils.convertBeanToObj(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String result = JSON.toJSONString(obj);
		return result;
	}

	public static String toJsonString(Object object) {
		String result = JSON.toJSONString(object, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteDateUseDateFormat);
		result = result.replace("\r\n", "\\r\\n").replace("\t", "\\t");
		return result;
	}

	/**
	 * 转换JSON
	 *
	 * @param json
	 * @param cls
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T fromJson(String json, Class<T> cls) {
		return JSON.parseObject(json, cls);
	}

	public static <T> T fromJson(String json, TypeReference<T> type) {
		return JSON.parseObject(json, type);
	}

	public static <T> T fromJson(JSONObject json, Class<T> cls) {
		return JSONObject.toJavaObject(json, cls);
	}

	public static Map fromJson(String json) {
		return JSON.parseObject(json, Map.class);
	}

	/**
	 * 转化为JSON
	 *
	 * @param t
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> String toJson(T t) {
		return JSON.toJSONString(t, false);
	}

	public static <T> String toJson(T t, boolean isSerializer) {
		if (isSerializer) {
			return JSON.toJSONString(t, SerializerFeature.BrowserCompatible);
		} else {
			return JSON.toJSONString(t);
		}
	}

}
