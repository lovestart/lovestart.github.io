package com.zhuxueup.common.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

import com.zhuxueup.common.weixin.MyX509TrustManager;

/**
 * http请求
 */
public class HttpClient {
	private static Logger log = Logger.getLogger(HttpClient.class);
	public static HttpRequestResult doGet(String url,
			HashMap<String, String> params) throws Exception {

		String strParams = "";
		if (params != null && !params.isEmpty()) {
			strParams = buildHttpRequest(params);
		}

		URL getUrl = new URL(url + "?" + strParams);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		connection.connect();
		HttpRequestResult result = new HttpRequestResult();
		int rcode = connection.getResponseCode();
		result.setResponseCode(rcode);
		if (rcode != 200) {
		}

		BufferedInputStream reader = new BufferedInputStream(
				connection.getInputStream());
		byte[] buf = new byte[1024];
		int bytesReaded = 0;
		StringBuffer strBuf = new StringBuffer();
		while ((bytesReaded = reader.read(buf)) != -1) {
			strBuf.append(new String(buf, 0, bytesReaded, "utf-8"));
		}

		result.setResponseBody(strBuf.toString());
		return result;
	}

	public static HttpRequestResult doPost(String url,
			HashMap<String, String> params) throws Exception {

		String strParams = "";
		if (params != null && !params.isEmpty()) {
			strParams = buildHttpRequest(params);
		}

		URL getUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();

		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.getOutputStream().write(strParams.getBytes("utf-8"));
		connection.connect();

		HttpRequestResult result = new HttpRequestResult();
		int rcode = connection.getResponseCode();
		result.setResponseCode(rcode);
		if (rcode != 200) {
		}

		BufferedInputStream reader = new BufferedInputStream(
				connection.getInputStream());
		byte[] buf = new byte[1024];
		int bytesReaded = 0;
		StringBuffer strBuf = new StringBuffer();
		while ((bytesReaded = reader.read(buf)) != -1) {
			strBuf.append(new String(buf, 0, bytesReaded, "utf-8"));
		}

		result.setResponseBody(strBuf.toString());
		return result;
	}

	public static HttpRequestResult doPost(String url, String json)
			throws Exception {

		URL getUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();

		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.getOutputStream().write(json.getBytes("utf-8"));
		connection.connect();

		HttpRequestResult result = new HttpRequestResult();
		int rcode = connection.getResponseCode();
		result.setResponseCode(rcode);
		if (rcode != 200) {
		}

		BufferedInputStream reader = new BufferedInputStream(
				connection.getInputStream());
		byte[] buf = new byte[1024];
		int bytesReaded = 0;
		StringBuffer strBuf = new StringBuffer();
		while ((bytesReaded = reader.read(buf)) != -1) {
			strBuf.append(new String(buf, 0, bytesReaded, "utf-8"));
		}

		result.setResponseBody(strBuf.toString());
		return result;
	}

	public static String buildHttpRequest(HashMap<String, String> params)
			throws Exception {
		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		StringBuffer strBuf = new StringBuffer();

		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = URLEncoder.encode(entry.getValue(), "utf-8");
			strBuf.append(key + "=" + val + "&");
		}
		return strBuf.substring(0, strBuf.length() - 1);
	}
	
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static HttpRequestResult httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		HttpRequestResult result = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			log.info("------返回数据----" + buffer.toString());

			result = new HttpRequestResult();
			int rcode = conn.getResponseCode();
			result.setResponseCode(rcode);
			result.setResponseBody(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
			log.error("连接超时：{}", ce);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("https请求异常：{}", e);
		}
		return result;
	}

}
