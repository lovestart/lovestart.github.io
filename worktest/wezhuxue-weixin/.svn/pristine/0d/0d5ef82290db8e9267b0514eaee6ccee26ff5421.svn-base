package com.zhuxueup.common.util;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReflectUtil {
	private static final Log logger = LogFactory.getLog(ReflectUtil.class);

	/**
	 * 根据ognl表达式（例如address.name），从对象obj中取值。
	 * 以address.name为例，实际执行操作为obj.getAdderss().getName();
	 * @param obj
	 * @param ognl
	 * @return
	 */
	public static Object getOgnlValueFromObject(Object obj, String ognl) {
		if(obj == null || ognl == null || ognl.trim().equals(""))
			return null;

		ognl = ognl.trim();
		
		int index = ognl.indexOf(".");
		if(index == -1) return getValueFromObject(obj, ognl);
		
		String name = ognl.substring(0, index);
		Object childObj = getValueFromObject(obj, name);
		
		return getOgnlValueFromObject(childObj, ognl.substring(index+1));
	}
	
	
	
	/**
	 * 从对象obj中取得name的值，此时，obj中应该有"get" + name所对应的方法。
	 * @param obj
	 * @param name
	 * @return
	 */
	public static Object getValueFromObject(Object obj, String name) {
		if(obj == null || name == null || name.trim().equals(""))
			return null;
		
		name = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
		
		return getMethodReturnValue(obj, name);
	}
	
	
	/**
	 * 取得对象obj的methodName方法的返回值。
	 * @param obj
	 * @param methodName
	 * @return
	 */
	public static Object getMethodReturnValue(Object obj, String methodName) {
		if(obj == null || methodName == null || methodName.trim().equals(""))
			return null;
		
		try{
			Class c = obj.getClass();
			Method m = c.getMethod(methodName, new Class[]{});
			return m.invoke(obj, new Object[]{});
		}catch(Exception e) {
			logger.warn("Get object property value failed: " + e.getMessage());
			return null;
		}
	}

	
	/**
	 * 根据ognl表达式（例如address.name），往对象obj中存值value。
	 * 以address.name为例，实际执行操作为obj.getDefaultAddress().setName(value);
	 * @param obj
	 * @param ognl
	 * @param value
	 */
	public static void setOgnlValueToObject(Object obj, String ognl, Object value) {
		if(obj == null || ognl == null || ognl.trim().equals("")) return;

		ognl = ognl.trim();
		
		int index = ognl.indexOf(".");
		if(index == -1) setValueToObject(obj, ognl, value);
		
		String name = ognl.substring(0, index);
		Object childObj = getValueFromObject(obj, name);
		
		setOgnlValueToObject(childObj, ognl.substring(index+1), value);
	}
	
	
	/**
	 * 往对象obj中写name的值，此时，obj中应该有"set" + name所对应的方法。
	 * @param obj
	 * @param name
	 * @param value
	 */
	public static void setValueToObject(Object obj, String name, Object value) {
		if(obj == null || name == null || name.trim().equals("")) return;
		
		name = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
		
		setObjectPropertyValue(obj, name, value);
	}
	
	
	/**
	 * 执行obj的methodName方法，methodName方法以value为参数。
	 * @param obj
	 * @param methodName
	 * @param value
	 */
	public static void setObjectPropertyValue(Object obj, String methodName, Object value) {
		if(obj == null || methodName == null || methodName.trim().equals("")) return;
		
		try{
			Class c = obj.getClass();
			Method m = c.getMethod(methodName, new Class[]{Object.class});
			m.invoke(obj, new Object[]{value});
		}catch(Exception e) {
			logger.warn("Set object property value failed: " + e.getMessage());
			return;
		}
	}
	
	
	/**
	 * 判断obj是否有methodName方法
	 * @param obj
	 * @param methodName
	 * @return
	 */
	public static boolean isMethodOf(Object obj, String methodName) {
		if(obj == null || methodName == null || methodName.trim().equals(""))
			return false;
		
		methodName = methodName.trim();	
		try{
			Class c = obj.getClass();
			c.getMethod(methodName, new Class[]{});
			return true;
		}catch(Exception e) {
			logger.warn("Relection error: " + e.getMessage());
			return false;
		}
	}

	
	/**
	 * 判断类c是否有methodName方法
	 * @param obj
	 * @param methodName
	 * @return
	 */
	public static boolean isMethodOf(Class c, String methodName) {
		if(c == null || methodName == null || methodName.trim().equals(""))
			return false;
		
		methodName = methodName.trim();
		try{
			c.getMethod(methodName, new Class[]{});
			return true;
		}catch(Exception e) {
			logger.warn("Relection error: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 *set empty string to property who's value is null
	 */
	public static void cleanNull(Object obj)
	{
		Method[] mar=obj.getClass().getMethods();
		for(int i=0;i<mar.length;i++)
		{
			String methodName=mar[i].getName();
			if(methodName.startsWith("get"))
			{
				try{
					Object result=mar[i].invoke(obj,new Object[]{});
					if(result==null)
					{
						String setMethodName=methodName.replaceFirst("g","s");
						Method setMethod=obj.getClass().getMethod(setMethodName,new Class[]{String.class});
						if(setMethod!=null)
						{
							setMethod.invoke(obj,new Object[]{""});
						}						
					}
				}catch(NoSuchMethodException e1)
				{
					continue;
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}	
}
