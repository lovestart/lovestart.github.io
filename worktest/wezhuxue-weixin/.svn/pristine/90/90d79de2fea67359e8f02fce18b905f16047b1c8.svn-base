package com.zhuxueup.common.pay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.security.KeyStore;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.IdGenerator;

@SuppressWarnings("deprecation")
public class WXPayUtil {

	private static Logger logger = Logger.getLogger(WXPayUtil.class);
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String wxPayRequest(String requestUrl, String requestMethod, String outputStr){
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
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
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
            return buffer.toString();
		} catch (ConnectException ce) {
			logger.error("连接超时：{}", ce);
		} catch (Exception e) {
			logger.error("https请求异常：{}", e);
		}
		return null;
    }
	/**
	 * 统一下单
	 * @param xml
	 * @return
	 */
    public static Map<String,String> getPrepayMap(String xml){
    	Map<String, String> map = null;
        try {
        	String result = wxPayRequest(WXConstants.WX_ORDER_URL, "POST", xml);
        	map = XMLUtil.doXMLParse(result);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 企业付款
     */
    public static Map<String,String> companyPay(String xml){
    	Map<String, String> map = null;
        try {
        	String result = wxPayRequestWithCert(WXConstants.WX_COMPANY_PAY_URL, xml);
        	map = XMLUtil.doXMLParse(result);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 发放普通红包
     */
    public static Map<String,String> publicRedPacket(String xml){
    	Map<String, String> map = null;
        try {
        	String result = wxPayRequestWithCert(WXConstants.WX_PUB_RED_URL, xml);
        	map = XMLUtil.doXMLParse(result);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 微信调用带证书
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String wxPayRequestWithCert(String url, String param) throws Exception{
    	KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(ConstantsUtil.getConstantsUtil().getCert_path()));
        //FileInputStream instream = new FileInputStream(new File("D:/cert/apiclient_cert.p12"));
        try {
            keyStore.load(instream, WXConstants.WX_MCH_ID.toCharArray());
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, WXConstants.WX_MCH_ID.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpGet httpget = new HttpGet(url);
            HttpPost httpPost = new HttpPost(url);
            StringEntity se = new StringEntity(param,"utf-8");
            httpPost.setEntity(se);
            System.out.println("executing request" + httpget.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    StringBuffer buffer = new StringBuffer();
                    while ((text = bufferedReader.readLine()) != null) {
                    	buffer.append(text);
                    }
                    return buffer.toString();
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return null;
    }
    /*微信企业付款备份
     * public static void companyPayBak(){
    	//微信企业付款
		SortedMap<String, String> sm = new TreeMap<String, String>();
		String orderNum = IdGenerator.orderId();
		//long timestamp = System.currentTimeMillis() / 1000;
		String nonceStr = UUID.randomUUID().toString().substring(0, 32);
        sm.put("mch_appid", WXConstants.WX_APP_ID);
        sm.put("mchid", WXConstants.WX_MCH_ID);
        sm.put("nonce_str", nonceStr);
        sm.put("desc", "用户抢到红包");
        sm.put("partner_trade_no", orderNum);
        sm.put("amount", "1");
        //map.put("amount", new BigDecimal(payVo.getAmount()).multiply(new BigDecimal(100)).setScale(0).toString());
        sm.put("spbill_create_ip", request.getLocalAddr());
        sm.put("notify_url", ConstantsUtil.getConstantsUtil().getPay_notify_url());
        sm.put("openid", openId);
        sm.put("check_name", "NO_CHECK");
       
        String paySign = SignUtil.createSign("utf-8", sm);
        log.info("企业付款*****微信签名***************************="+paySign);
        sm.put("sign",paySign);
        String xml = CommonUtil.ArrayToXml(sm);
        log.info("请求参数*****************************="+xml);
        Map<String,String> preMap = WXPayUtil.companyPay(xml);
        log.info("企业付款返回参数*****************************preMap="+preMap);
    }*/
    public static void main(String[] args){
    	//微信企业付款
		SortedMap<String, String> sm = new TreeMap<String, String>();
		String orderNum = IdGenerator.wxCouponOrder();
		if(orderNum.length() > 18){
			orderNum = orderNum.substring(0, 18);
		}
		//long timestamp = System.currentTimeMillis() / 1000;
		String nonceStr = UUID.randomUUID().toString().substring(0, 32);
        sm.put("nonce_str", nonceStr);
        sm.put("mch_billno", WXConstants.WX_MCH_ID+orderNum);
        sm.put("mch_id", WXConstants.WX_MCH_ID);
        sm.put("wxappid", WXConstants.WX_APP_ID);
        sm.put("send_name", "人人助学");
        sm.put("re_openid", "o5_6DwuwJUWbHE2FgBSTyWRofAAU");
        sm.put("total_amount", "101");
        sm.put("total_num", "1");
        sm.put("wishing", "感谢您参加年兽红包活动，祝您鸡年大吉！");
        sm.put("act_name", "打年兽抢红包活动");
        //map.put("amount", new BigDecimal(payVo.getAmount()).multiply(new BigDecimal(100)).setScale(0).toString());
        sm.put("client_ip", "192.168.0.110");
        sm.put("remark", "多玩多抢，鸡年大吉！");
       
        String paySign = SignUtil.createSign("utf-8", sm);
        System.out.println("普通红包*****微信签名***************************="+paySign);
        sm.put("sign", paySign);
        String xml = CommonUtil.ArrayToXml(sm);
        System.out.println("请求参数*****************************="+xml);
        Map<String,String> preMap = WXPayUtil.publicRedPacket(xml);
        System.out.println("普通红包返回参数*****************************preMap="+preMap);
    }
}
