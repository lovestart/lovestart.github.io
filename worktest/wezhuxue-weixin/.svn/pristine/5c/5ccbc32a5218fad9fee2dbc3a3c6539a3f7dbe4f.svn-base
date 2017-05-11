package com.zhuxueup.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @note：数字计算帮助类
 * 
 * @date:2016-1-18
 */
public class NumberUtils {

	protected static Log logger = LogFactory.getLog(NumberUtils.class);

	private NumberUtils() {

	}

	/**
	 * 精确的加法运算.
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).doubleValue();
	}

	/**
	 * 
	 * 精确的减法运算.
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 */
	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算.
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算.
	 */
	public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
		return v1.multiply(v2);
	}

	public static BigDecimal dataPow(int data, int pownum) {

		double temp = Math.pow(data, pownum);

		return new BigDecimal(temp);
	}

	/**
	 * 提供精确的乘法运算，并对运算结果截位.
	 * 
	 * @param scale
	 *            运算结果小数后精确的位数
	 */
	public static double multiply(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算.
	 * 
	 * @see #divide(double, double, int)
	 */
	public static double divide(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算. 由scale参数指定精度，以后的数字四舍五入.
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位
	 */
	public static double divide(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static float divide(float v1, float v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理.
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 提供精确的小数位四舍五入处理.
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 */
	public static float round(float v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 
	 *  将字符串转化为Int类型，转化出错默认为defaultVal 值
	 * 
	 * @param value
	 * @param defaultVal
	 */
	public static int stringToInt(String value, int defaultVal) {
		if(StringUtil.isBlank(value)){
			return defaultVal;
		}
		int rs = defaultVal;
		try {
			rs = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
		return rs;

	}

	/**
	 * 字符串改成long型 
	 * 
	 * @param value
	 * @param defaultVal
	 * @return:
	 * @date:2011-3-4 上午11:37:13
	 * @author:Dane.wang
	 */
	public static long stringToLong(String value, long defaultVal) {
		if(StringUtil.isBlank(value)){
			return defaultVal;
		}
		long rs = defaultVal;
		try {
			rs = Long.parseLong(StringUtil.toTrim(value));
		} catch (NumberFormatException e) {
			return defaultVal;
		}
		return rs;

	}

	/**
	 * 
	 *  将字符串转化为double类型，转化出错默认为defaultVal 值
	 * 
	 * @param value
	 * @param defaultVal
	 */
	public static double stringToDouble(String value, double defaultVal) {
		if(StringUtil.isBlank(value)){
			return defaultVal;
		}
		double rs = defaultVal;
		try {
			rs = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
		return rs;

	}
	
	/**
	 * 
	 *  将字符串转化为float类型，转化出错默认为defaultVal 值
	 * 
	 * @param value
	 * @param defaultVal
	 */
	public static float stringToFloat(String value, float defaultVal) {
		if(StringUtil.isBlank(value)){
			value = "0";
		}
		float rs = defaultVal;
		try {
			rs = Float.parseFloat(value);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
		return rs;

	}

	/**
	 * 
	 * 验证当前字符串或字符是否为数字
	 * 
	 * @param str
	 */
	public static boolean isNumber(String str) {

		if (StringUtil.isBlank(str)) {
			logger.error("传入的str为null");
			return false;
		}

		try {
			Long.parseLong(str);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * 获取两个数值的最小公倍数
	 * 
	 * @param a
	 * @param b
	 * @return:
	 */
	public static int minGongbei(int a, int b) {
		if (a == 0 || b == 0) {
			logger.info("取两个数的最小公倍数的时候，传入的参数为0");
			return 0;
		}

		int result = 0;

		// 将两个数的乘积/两个数的最大公约数 ：即为最小公倍数
		if (maxGongyue(a, b) == 0) {
			logger.info("取两个数的最小公倍数的时候，获取的最大公约数为0");
			return 0;
		}
		result = a * b / maxGongyue(a, b);

		return result;
	}

	/**
	 * 
	 * 获取两个数值的最大公约数
	 * 
	 * @param a
	 * @param b
	 * @return:
	 */
	public static int maxGongyue(int a, int b) {

		if (a == 0 || b == 0) {
			logger.info("取两个数的最大公约数的时候，传入的参数为0");
			return 0;
		}
		int temp = 1;

		// 如果a<b,则交换位置
		if (a < b) {
			temp = a;
			a = b;
			b = temp;
		}

		// 循环获取最大公约数
		while (temp != 0) {
			temp = a % b;
			a = b;
			b = temp;
		}

		return a;
	}

	/**
	 * 
	 * 获取两个数及两个数以上的最大公约数
	 * 
	 * @param datas
	 * @return:
	 */
	public static int maxGongyue(int[] datas) {
		if (datas == null || datas.length < 2) {
			logger.error("获取最大公约数时，传入的datas格式有误");
			return 0;
		}
		// 取第一个数值
		int result = datas[0];
		// 偶数个数的数据
		for (int i = 1; i < datas.length; i++) {
			if (datas[i] == 0)
				continue;
			result = maxGongyue(result, datas[i]);
		}

		return result;
	}

	/**
	 * 
	 * 获取两个数及两个数以上的最小公倍数
	 * 
	 * @param datas
	 * @return:
	 */
	public static int minGongbei(int[] datas) {
		if (datas == null || datas.length < 2) {
			logger.error("获取最小公倍数时，传入的datas格式有误");
			return 0;
		}
		// 取第一个数值
		int result = datas[0];
		// 偶数个数的数据
		for (int i = 1; i < datas.length; i++) {
			if (datas[i] == 0)
				continue;
			result = minGongbei(result, datas[i]);
		}

		return result;
	}

	/**
	 * 
	 * 将金额转化为货币格式：10000.00 ——>10,000.00
	 * 
	 * @param strValue
	 * @return:
	 */
	public static String formatMoney(String strValue) {
		if (strValue == null || strValue.equals("")) {
			logger.error("传入的strValue为null");
			return "0.000";
		}
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.000");
		String outStr = null;
		double d;
		try {
			d = Double.parseDouble(strValue);
			outStr = fmt.format(d);
		} catch (Exception e) {
		}
		return outStr;
	}

	/**
	 * 
	 * 将金额货币格式转化正常小数格式：10,000.00 ——>10000.00
	 * 
	 * @param strValue
	 * @return:
	 */
	public static String formatResult(String strValue) {
		if (strValue == null || strValue.equals("")) {
			logger.error("传入的strValue为null");
			return "0.000";
		}
		strValue = strValue.replaceAll("，", ",");
		strValue = strValue.replaceAll(",", "");

		return strValue;
	}

	/**
	 * BigDecimal格式化，保留num为小数
	 * 
	 * @param bignum
	 * @param num
	 * @return
	 */
	public static BigDecimal changeBigDecimal(BigDecimal bignum, int num) {
		if (bignum == null) {
			bignum = new BigDecimal(0);
		}
		// 设置精度，以及舍入规则
		bignum = bignum.setScale(num, BigDecimal.ROUND_HALF_UP);
		return bignum;
	}

	/**
	 * BigDecimal格式化，保留num为小数
	 * 
	 * @param bignum
	 *            字符串
	 * @param num
	 * @return
	 */
	public static BigDecimal changeBigDecimal(String bignum, int num) {
		BigDecimal numtemp = null;
		if (StringUtil.isBlank(bignum)) {
			numtemp = new BigDecimal(0);
		} else {
			numtemp = new BigDecimal(bignum);
		}

		// 设置精度，以及舍入规则
		numtemp = numtemp.setScale(num, BigDecimal.ROUND_HALF_UP);
		return numtemp;
	}
	/**
	 * 保留两位数
	 * @param d
	 * @return
	 */
	public static 	String getMoneyNumber(Double d){
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(d);
	}

	public static void main(String[] args) {
		//System.out.println(isNumber("09882772"));
		System.out.println(getMoneyNumber(10.9882772));
	}
}
