package com.zhuxueup.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zhuxueup.common.jdbc.DbService;
import com.zhuxueup.common.pay.WXConstants;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.JsonUtil;
import com.zhuxueup.common.weixin.AdvancedUtil;
import com.zhuxueup.common.weixin.JssdkSignUtil;
import com.zhuxueup.common.zenum.ResponseCode;
import com.zhuxueup.web.model.AccessToken;

public class JsSdkServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7219779388340923966L;
	private static Logger logger = Logger.getLogger(JsSdkServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("----------微信jssdk获取签名-----------");
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String,Object> returnMap = new HashMap<String, Object>();
		String url = request.getParameter("url");
		//String callback = request.getParameter("callback");
		if(CTUtils.isEmpty(url)){
			returnMap.put("code", ResponseCode.PARAM_ERROR.getCode());
			returnMap.put("msg", "分享链接为空");
		}else{
			try {
				SortedMap<String, String> map = new TreeMap<String, String>();
				long timestamp = System.currentTimeMillis() / 1000;
				String nonceStr = UUID.randomUUID().toString().substring(0, 32);
				AccessToken token = DbService.getToken();
				String jsapiTicket= AdvancedUtil.getJsapiTicket(token.getToken());
				map.put("jsapi_ticket", jsapiTicket);
		        map.put("noncestr", nonceStr);
		        map.put("timestamp", timestamp+"");
		        map.put("url", url);
		        logger.info("-----------签名参数-------"+map.toString());
		        //String signature = SignUtil.createSign(map);
		        String signature = JssdkSignUtil.getSignature(jsapiTicket, timestamp+"", nonceStr, url);
		        
		        returnMap.put("appId", ConstantsUtil.getConstantsUtil().getAppId());
		        returnMap.put("timeStamp", timestamp+"");
		        returnMap.put("nonceStr", nonceStr);
		        returnMap.put("signature", signature);
		    	returnMap.put("code", ResponseCode.OK.getCode());
		    	returnMap.put("msg", "成功");
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("code", ResponseCode.ERROR.getCode());
				returnMap.put("msg", "微信JS-SDK签名异常");
			}
		}
		logger.info("-----------JS-SDK签名返回："+JsonUtil.toJson(returnMap));
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(JsonUtil.toJson(returnMap));
		out.flush();
		out.close();
	}
	public static void main(String[] args){
		try {
			System.out.println(JssdkSignUtil.getSignature("kgt8ON7yVITDhtdwci0qeW2OwsxaHp52wv3cGrRo5YpyKISrCK1mCqY5a5mOFwTXNcUkJVbpPQm3B8dUYvYrng",
					"1484903872", "9bf390c7-8211-4d4a-9ec0-7bcfb778", "http://wx.wezhuxue.com/wezhuxue-weixin/views/nianshou/index.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
