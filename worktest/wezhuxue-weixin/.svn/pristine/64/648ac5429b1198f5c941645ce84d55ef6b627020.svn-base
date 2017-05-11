package com.zhuxueup.common.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @note：String对象帮助类
 * 
 * @date:1/18, 2016
 */
public class StringUtil {

	protected static Log logger = LogFactory.getLog(StringUtil.class);

	/**
	 * 
	 * 验证字符串是否为空
	 * 
	 * @param s
	 * @return:
	 * @date:2009-8-3 上午11:15:42
	 * @author:zhanglijun
	 */
	public static boolean isBlank(String s) {
		if (s == null || ("").equals(s.trim()))
			return true;
		return false;
	}

	/**
	 * 判断字符串对象是否为空值或者空字符串， 如果是则返回<code>true</code>,反之则<code>false</code>。
	 * 
	 * @param str
	 *            字符串对象
	 * @return 布尔值
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 
	 * 除空格
	 * 
	 * @param s
	 */
	public static String toTrim(String s) {
		if (s == null || s.equals(""))
			return "";
		return s.trim();
	}

	public static String toTrim(Object s) {
		if (s == null)
			return "";
		return s.toString().trim();
	}

	public static boolean isTel(String tel) {
		tel = toTrim(tel);
		if (!NumberUtils.isNumber(tel)) {
			logger.error("电话号码有误");
			return false;
		}
		// 手机号码验证
		if (tel.startsWith("13") || tel.startsWith("15")
				|| tel.startsWith("18")) {
			if (tel.length() != 11) {
				logger.error("手机号码有误");
				return false;
			}
		} else {// 固定电话验证
			if (!(tel.length() == 7 || tel.length() == 8 || tel.length() == 11 || tel
					.length() == 12)) {
				logger.error("固定电话号码有误");
				return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * 在字符串中间加空格进行格式化
	 * 
	 * @param s
	 * @param bankNum
	 *            空格数
	 */
	public static String addBlank(String s, int blankNum) {
		if (isBlank(s)) {
			return "";
		}
		if (blankNum <= 0) {
			return s;
		}
		String blank = "";
		for (int i = 0; i < blankNum; i++) {
			blank += " ";
		}
		StringBuffer result = new StringBuffer();
		char[] cs = s.toCharArray();
		for (int k = 0; k < cs.length; k++) {
			result.append(cs[k]).append(blank);
		}

		return result.toString();
	}

	/**
	 * 把字符串src以字符串s分割开，并把结果保存到数组中返回
	 * 
	 * @param src
	 * @param s
	 * @return
	 */
	public static String[] split(String src, String delimiter) {

		int index, startIndex = 0;
		ArrayList list = new ArrayList();
		while ((index = src.indexOf(delimiter, startIndex)) >= 0) {
			list.add(src.substring(startIndex, index));
			startIndex = index + delimiter.length();
		}

		if (startIndex < src.length())
			list.add(src.substring(startIndex, src.length()));

		String[] array = new String[] {};
		return (String[]) list.toArray(array);
	}

	/**
	 * 如果top为真，则在src头部添加字符串fillStr，直到src的长度超过length
	 * 如果top为假，则在src尾部添加字符串fillStr，直到src的长度超过length
	 * 
	 * @param src
	 * @param length
	 * @param fillStr
	 * @param top
	 * @return
	 */
	public static String fill(String src, int length, String fillStr,
			boolean top) {
		if (src.length() >= length)
			return src;
		if (fillStr == null || fillStr.length() == 0)
			return src;

		String buf = src;
		while (buf.length() < length) {
			if (top) {
				buf = fillStr + buf;
			} else {
				buf = buf + fillStr;
			}
		}

		return buf;
	}

	/**
	 * 对source进行格式化，格式化规则是： source中最后一个"."后面保留len位字符，若长度不够，则补"0"； 若len <
	 * 1则source的最后一个"."后没有字符，同时去除最后一个"."。
	 * 
	 * @param source
	 *            需要格式化的字符串
	 * @param len
	 *            "."后需要保留的字符的个数
	 * @return
	 */
	public static String formatFraction(String source, int len) {
		if (source == null || source.trim().equals(""))
			return null;

		int index = source.lastIndexOf(".");
		if (index == -1) {
			if (len > 0) {
				source += ".";
				for (int i = 0; i < len; i++) {
					source += "0";
				}
			}
		} else {
			if (len < 1) {
				source = source.substring(0, index);
			} else {
				int fractionLen = source.length() - 1 - index;
				if (fractionLen < len) {
					for (int i = 0; i < len - fractionLen; i++) {
						source += "0";
					}
				} else if (fractionLen > len) {
					source = source.substring(0, index + len + 1);
				}
			}
		}

		return source;
	}

	/**
	 * 该函数对一列对象进行格式化，格式化规则为： 每个对象的prop属性保留len位小数，若len<=0,则无小数位。
	 * 
	 * @param data
	 *            一列需要格式化的对象
	 * @param prop
	 *            需要格式化的属性
	 * @param len
	 *            prop属性需要保留的小数位数
	 * @return
	 */
	public static void formatFraction(List data, String prop, int len) {
		if (data == null || prop == null || prop.trim().equals(""))
			return;

		Object value;
		Object changeValue = null;
		Object obj;
		String valueStr;

		for (Iterator it = data.iterator(); it.hasNext();) {
			obj = it.next();
			value = ReflectUtil.getOgnlValueFromObject(obj, prop);
			if (value == null)
				continue;

			valueStr = value.toString();
			valueStr = formatFraction(valueStr, len);
			try {
				if (value instanceof String) {
					// DO NOTHING
				} else if (value instanceof BigDecimal) {
					changeValue = new BigDecimal(valueStr);
				} else if (value instanceof BigInteger) {
					changeValue = new BigInteger(valueStr);
				} else if (value instanceof Byte) {
					changeValue = new Byte(valueStr);
				} else if (value instanceof Double) {
					changeValue = new Double(valueStr);
				} else if (value instanceof Float) {
					changeValue = new Float(valueStr);
				} else if (value instanceof Integer) {
					changeValue = new Integer(valueStr);
				} else if (value instanceof Long) {
					changeValue = new Long(valueStr);
				} else if (value instanceof Short) {
					changeValue = new Short(valueStr);
				} else {
					continue;
				}
			} catch (Exception e) {
				continue;
			}

			ReflectUtil.setOgnlValueToObject(obj, prop, changeValue);
		}
	}

	/**
	 * 该函数对一列对象进行格式化
	 * 
	 * @param data
	 *            需要格式化的对象
	 * @param patterns
	 *            格式化模式，patterns中key为属性，对应的值为该属性需要保留的小数位数
	 */
	public static void formateFraction(List data, Map patterns) {
		if (data == null || patterns == null)
			return;
		Object key;
		String keyStr;
		Object value;
		int valueInt;

		for (Iterator it = patterns.keySet().iterator(); it.hasNext();) {
			key = it.next();
			if (key == null)
				continue;
			keyStr = key.toString();
			value = patterns.get(key);
			if (value == null)
				continue;
			if (value instanceof Integer) {
				valueInt = ((Integer) value).intValue();
			} else if (value instanceof String) {
				try {
					valueInt = Integer.parseInt((String) value);
				} catch (NumberFormatException nfe) {
					continue;
				}
			} else
				continue;

			formatFraction(data, keyStr, valueInt);
		}
	}

	public static int parseInt(String str, int defaultVal) {
		int rs = defaultVal;
		try {
			rs = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
		return rs;
	}

	public static int parseInt(String str) {
		return parseInt(str, 0);
	}

	public static String chgNull(String in) {
		String out = null;
		out = in;
		if (out == null || (out.trim()).equals("null")) {
			return "";
		} else {
			return out.trim();
		}
	}

	/**
	 * 
	 * 将字符串转化为数字
	 * 
	 * @param word
	 */
	public static long letterTonum(String word) {
		long result = 0;
		char[] letters = word.toCharArray();
		for (int i = 0; i < letters.length; i++) {
			result *= 26;
			result += letters[i] - 'A';
		}
		return result;
	}

	/**
	 * 
	 * 将数字转化为字母
	 */
	public static String numToLetter(int num) {

		char[] letters = "A".toCharArray();
		String result = "";
		for (int i = 0; i < letters.length; i++) {
			letters[i] += num;
		}
		result = new String(letters);

		return result;
	}

	/**
	 * 半角转全角
	 */
	public static final String BQchange(String QJstr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b[3] != -1) {
				b[2] = (byte) (b[2] - 32);
				b[3] = -1;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}
		return outStr;
	}

	/**
	 * 全角转半角
	 */
	public static final String QBchange(String QJstr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}
		return outStr;
	}

	/**
	 * 
	 * 将十六进制数转为十进制
	 * 
	 * @param hex
	 */
	public static String hexToString(String hex) {
		if (StringUtil.isBlank(hex)) {
			return "";
		}

		return Integer.valueOf(hex, 16).toString();
	}

	/**
	 * 
	 * 
	 * 
	 * @param str
	 * @param num
	 *            保留字节位数 偶数位,不足位数则在前面拼0 ，最小为2位
	 */
	public static String stringToHex(String str, int num) {
		if (StringUtil.isBlank(str) || !(num > 0 && num % 2 == 0)) {
			// logger.error("传入的需要转换为十六进制的数为无效参数");
			return "";
		}
		String result = toUnsignedString(Integer.parseInt(str), 4);
		if (result.length() < num) {
			for (int i = 0; i < num; i++) {
				if (result.length() == num)
					break;
				result = "0" + result;
			}
		}

		return result;
	}

	public static String toUnsignedString(int i, int shift) {
		// 定义一个32长度的字符数组，因为java的Integer最大长度32位
		char[] buf = new char[32];
		// 字符索引
		int charPos = 32;
		// 转换的范围(1左移4位就是16)
		int radix = 1 << shift;
		// 定义标记
		int mask = radix - 1;
		do {

			buf[--charPos] = digits[i & mask];
			i >>>= shift;// 转换的数字除以16，因为左移4位是除16
		} while (i != 0);// 当转换的数字被16除尽时结束循环

		return new String(buf, charPos, (32 - charPos));
	}

	// 数字显示成员
	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z' };

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	public static int[] hexStringToInt(String hex) {
		int len = (hex.length() / 2);
		int[] result = new int[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (int) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	// 低位
	public byte lowBit(short num) {
		return (byte) (num & 0x00ff);
	}

	// 高位
	public byte hiBit(short num) {
		return (byte) (num >> 8);
	}

	// 组合
	public short combine(byte lowBit, byte hiBit) {
		return (short) (lowBit + (hiBit << 8));
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/** */
	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/** */
	/**
	 * @函数功能: BCD码转为10进制串(阿拉伯数据)
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static String bcd2Str(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int h = ((b[i] & 0xff) >> 4) + 48;
			sb.append((char) h);
			int l = (b[i] & 0x0f) + 48;
			sb.append((char) l);
		}
		return sb.toString();
	}

	/** */
	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输出结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {

		if (asc.length() % 2 != 0) {
			asc = "0" + asc;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		char[] cs = asc.toCharArray();
		for (int i = 0; i < cs.length; i += 2) {
			int high = cs[i] - 48;
			int low = cs[i + 1] - 48;
			baos.write(high << 4 | low);
		}
		return baos.toByteArray();

	}

	/**
	 * 
	 * 将字节数组（BCD或者其他ASCii）转化为short值
	 * 
	 * @param b
	 */
	public static final int byteArrayToShort(byte[] b) {
		return (b[0] << 8) + (b[1] & 0xFF);
	}

	/**
	 * 
	 * 将INT转化为byte[]
	 */
	public static byte[] intToByteArray(int value) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (b.length - 1 - i) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return b;
	}

	/** */
	/**
	 * MD5加密字符串，返回加密后的16进制字符串
	 * 
	 * @param origin
	 * @return
	 */
	public static String MD5EncodeToHex(String origin) {
		return bytesToHexString(MD5Encode(origin));
	}

	/** */
	/**
	 * MD5加密字符串，返回加密后的字节数组
	 * 
	 * @param origin
	 * @return
	 */
	public static byte[] MD5Encode(String origin) {
		return MD5Encode(origin.getBytes());
	}

	/** */
	/**
	 * MD5加密字节数组，返回加密后的字节数组
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] MD5Encode(byte[] bytes) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			return md.digest(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}

	}

	/**
	 * 过滤html标签
	 * 
	 * @param inputString
	 * @return
	 */
	public static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;
		try {
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签
			htmlStr = htmlStr.replace("&nbsp;", " ");
			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

	/**
	 * 计算百分比
	 * 
	 * @param current
	 * @param total
	 * @return
	 */
	public static String percent(double current, double total) {

		String str;
		double percent = current / total;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(0);
		str = nf.format(percent);

		return str;
	}

	/**
	 * 全局唯一标识符
	 * 
	 * @return UUID
	 */
	public static final String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 返回0.00
	 * 
	 * @param value
	 * @return
	 */
	public static final String formatePriceVal(String value) {
		if (value == null) {
			return "0.00";
		} else {
			return value;
		}
	}

	/*
	 * 由于Java是基于Unicode编码的，因此，一个汉字的长度为1，而不是2。
	 * 但有时需要以字节单位获得字符串的长度。例如，“123abc长城”按字节长度计算是10，而按Unicode计算长度是8。
	 * 为了获得10，需要从头扫描根据字符的Ascii来获得具体的长度
	 * 。如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255。
	 * 因此，可以编写如下的方法来获得以字节为单位的字符串长度。
	 */
	public static int getWordCount(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;

	}

	/*
	 * 基本原理是将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）。这样就可以直接例用length方法获得字符串的字节长度了
	 */
	public static int getWordCountRegex(String s) {
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	}

	/* 按特定的编码格式获取长度 */
	public static int getWordCountCode(String str, String code)
			throws UnsupportedEncodingException {
		return str.getBytes(code).length;
	}

	public static void main(String[] args) {

	}

}
