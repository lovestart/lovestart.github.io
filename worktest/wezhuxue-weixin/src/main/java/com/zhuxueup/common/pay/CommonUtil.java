package com.zhuxueup.common.pay;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class CommonUtil {

	/**
	 * 拼装成xml
	 * @param arr
	 * @return
	 */
	public static String ArrayToXml(Map<String, String> arr) {
		String xml = "<xml>";
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (!"body".equalsIgnoreCase(key)&&!"sign".equalsIgnoreCase(key)) {
				xml += "<" + key + ">" + val + "</" + key + ">";
			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}
		xml += "</xml>";
		return xml;
	}
}
