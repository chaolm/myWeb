package com.dripop.core.util;

import com.dripop.core.exception.SystemException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class BeanUtils {

	private final static Set<Class> baseClass = new HashSet<Class>() {
		{
			add(Integer.class);
			add(BigInteger.class);
			add(Double.class);
			add(BigDecimal.class);
			add(Long.class);
			add(String.class);
			add(Date.class);
		}
	};

	/**
	 * 利用反射实现对象之间属性复制
	 * @param from
	 * @param to
	 */
	public static void copyProperties(Object from, Object to) {
		copyPropertiesExclude(from, to, null);
	}
	
	/**
	 * 复制对象属性
	 * @param from
	 * @param to
	 * @param excludsArray 排除属性列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void copyPropertiesExclude(Object from, Object to, String[] excludsArray) {
		List<String> excludesList = null;
		if(excludsArray != null && excludsArray.length > 0) {
			excludesList = Arrays.asList(excludsArray);	//构造列表对象
		}
		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;
		for (int i = 0; i < fromMethods.length; i++) {
			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get"))
				continue;
			//排除列表检测
			if(excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if (toMethod == null)
				continue;
			Object value = null;
			try {
				value = fromMethod.invoke(from, new Object[0]);
			} catch (Exception e) {
				throw new SystemException(e.getMessage(), e);
			}
			if(value == null)
				continue;
			if(value instanceof String && StringUtil.isEmpty(String.valueOf(value)))
				continue;
			if(value instanceof Integer && ((Integer) value).intValue() == 0) {
				continue;
			}
			//集合类判空处理
			if(value instanceof Collection) {
				Collection newValue = (Collection)value;
				if(newValue.size() <= 0)
					continue;
			}
			try {
				toMethod.invoke(to, new Object[] {value});
			} catch (Exception e) {
				throw new SystemException(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 对象属性值复制，仅复制指定名称的属性值
	 * @param from
	 * @param to
	 * @param includsArray
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void copyPropertiesInclude(Object from, Object to, String[] includsArray) throws Exception {
		List<String> includesList = null;
		if(includsArray != null && includsArray.length > 0) {
			includesList = Arrays.asList(includsArray);	//构造列表对象
		} else {
			return;
		}
		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;
		for (int i = 0; i < fromMethods.length; i++) {
			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get"))
				continue;
			//排除列表检测
			String str = fromMethodName.substring(3);
			if(!includesList.contains(str.substring(0,1).toLowerCase() + str.substring(1))) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if (toMethod == null)
				continue;
			Object value = fromMethod.invoke(from, new Object[0]);
			if(value == null)
				continue;
			//集合类判空处理
			if(value instanceof Collection) {
				Collection newValue = (Collection)value;
				if(newValue.size() <= 0)
					continue;
			}
			toMethod.invoke(to, new Object[] {value});
		}
	}
	
	

	/**
	 * 从方法数组中获取指定名称的方法
	 * 
	 * @param methods
	 * @param name
	 * @return
	 */
	public static Method findMethodByName(Method[] methods, String name) {
		for (int j = 0; j < methods.length; j++) {
			if (methods[j].getName().equals(name))
				return methods[j];
		}
		return null;
	}

	/**
	 * Map转为Bean
	 * @param map
	 * @param clz
	 * @param <T>
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public static <T> T  convertMapToBean(Map<String, Object> map, Class<T> clz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
		T t = clz.newInstance();

		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new ShortConverter(null), Short.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		ConvertUtils.register(new DateTimeConverter(), Date.class);
		org.apache.commons.beanutils.BeanUtils.populate(t, map);

		return t;
	}

	public static Object convertBeanToObj(Object obj) throws IllegalAccessException {
		if(obj == null){
			return null;
		}
		if(baseClass.contains(obj.getClass())) {
			return obj;
		}
		List list = null;
		List objList = new ArrayList();
		if(obj instanceof List) {
			list = (List)obj;
			for (Object o : list) {
				if(baseClass.contains(o.getClass())) {
					objList.add(o);
				}else {
					objList.add(convertBeanToMap(o));
				}
			}
			return objList;
		}
		return convertBeanToMap(obj);
	}

	public static Map<String, Object> convertBeanToMap(Object obj) throws IllegalAccessException {
		if(obj == null){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}
		return map;
	}

	/**
	 * Map转为Bean
	 * @param mapList
	 * @param clz
	 * @param <T>
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public static <T> List<T>  convertMapToBean(List<Map<String, Object>> mapList, Class<T> clz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
		List list = new ArrayList(mapList.size());

		try {
			for (Map map : mapList) {
				list.add(BeanUtils.convertMapToBean(map, clz));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}
}
