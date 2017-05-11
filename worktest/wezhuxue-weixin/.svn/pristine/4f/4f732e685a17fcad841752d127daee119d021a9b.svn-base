package com.zhuxueup.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import com.zhuxueup.common.pay.CommonUtil;
import com.zhuxueup.common.pay.SignUtil;
import com.zhuxueup.common.pay.WXConstants;
import com.zhuxueup.common.pay.WXPayUtil;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.IdGenerator;
import com.zhuxueup.common.util.JsonUtil;
import com.zhuxueup.common.zenum.ResponseCode;
import com.zhuxueup.web.model.PayRecord;

public class PayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7219779388340923966L;
	private static Logger logger = Logger.getLogger(PayServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		logger.info("---------支付签名接口-----------");
		Map<String,Object> returnMap = new HashMap<String, Object>();
		String callback = request.getParameter("callback");
		String amount = request.getParameter("amount");
		String subject = request.getParameter("subject");
		String remoteAddr = request.getParameter("remoteAddr");
		String openId = request.getParameter("openId");
		if(CTUtils.isEmpty(amount)){
			returnMap.put("code", ResponseCode.PARAM_ERROR.getCode());
			returnMap.put("code", "金额为空");
		}else if(CTUtils.isEmpty(subject)){
			returnMap.put("code", ResponseCode.PARAM_ERROR.getCode());
			returnMap.put("code", "描述为空");
		}else if(CTUtils.isEmpty(remoteAddr)){
			returnMap.put("code", ResponseCode.PARAM_ERROR.getCode());
			returnMap.put("code", "终端ip为空");
		}else if(Double.parseDouble(amount)<=0){
			returnMap.put("code", ResponseCode.PARAM_ERROR.getCode());
			returnMap.put("code", "金额不合法");
		}else if(CTUtils.isEmpty(openId)){
			returnMap.put("code", ResponseCode.PARAM_ERROR.getCode());
			returnMap.put("code", "openId为空");
		}else{
			try {
				SortedMap<String, String> map = new TreeMap<String, String>();
				String orderNum = IdGenerator.orderId();
				long timestamp = System.currentTimeMillis() / 1000;
				String nonceStr = UUID.randomUUID().toString().substring(0, 32);
		        map.put("appid", WXConstants.WX_APP_ID);
		        map.put("mch_id", WXConstants.WX_MCH_ID);
		        map.put("nonce_str", nonceStr);
		        map.put("body", subject);
		        map.put("out_trade_no", orderNum);
		        //map.put("total_fee", "100");
		        map.put("total_fee", new BigDecimal(amount).multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
		        map.put("spbill_create_ip", remoteAddr);
		        map.put("notify_url", ConstantsUtil.getConstantsUtil().getPay_notify_url());
		        map.put("trade_type", "JSAPI");
		        map.put("openid", openId);
		        map.put("limit_pay", "no_credit");
		       
		        String paySign = SignUtil.createSign("utf-8", map);
		        logger.info("统一下单*****微信签名***************************="+paySign);
		        map.put("sign",paySign);
		        String xml = CommonUtil.ArrayToXml(map);
		        logger.info("请求参数*****************************="+xml);
		        Map<String,String> preMap = WXPayUtil.getPrepayMap(xml);
		        logger.info("统一下单返回参数*****************************preMap="+preMap);
		        if(!"SUCCESS".equals(preMap.get("return_code"))){
					returnMap.put("code",ResponseCode.ERROR.getCode());
					returnMap.put("code","微信统一下单异常");
				}else if(!"SUCCESS".equals(preMap.get("result_code"))){
		        	returnMap.put("code",ResponseCode.ERROR.getCode());
					returnMap.put("code","微信统一下单异常");
				}else{
			        String prepayid = preMap.get("prepay_id");
			        //封装客服端调用参数
			        SortedMap<String, String> signMap = new TreeMap<String, String>();
		        	signMap.put("appId", WXConstants.WX_APP_ID);
			        signMap.put("timeStamp", timestamp+"");
			        signMap.put("package", "prepay_id="+prepayid);
			        signMap.put("nonceStr", nonceStr);
			        signMap.put("signType", WXConstants.WX_SIGN_TYPE);
			        String paySign2 = SignUtil.createSign("utf-8", signMap);
			        logger.info("客户端支付******签名************="+paySign2);
			        returnMap.put("appId", WXConstants.WX_APP_ID);
			        returnMap.put("timeStamp", timestamp+"");
			        returnMap.put("nonceStr", nonceStr);
			        returnMap.put("prepayId", prepayid);
			        returnMap.put("signType", WXConstants.WX_SIGN_TYPE);
			        returnMap.put("paySign", paySign2);
			        
			        PayRecord pay = new PayRecord();
			        pay.setId(IdGenerator.nextId());
			        pay.setOrder_num(orderNum);
			        pay.setOpen_id(openId);
			        pay.setAmount(amount);
			        pay.setStatus("0");
			        pay.setType("1");
			        boolean flag = DbService.insertEntity(pay);
			    	returnMap.put("code", ResponseCode.OK.getCode());
			    	returnMap.put("msg", "成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("code", ResponseCode.ERROR.getCode());
				returnMap.put("msg", "微信统一下单异常");
			}
		}
		logger.info("-----------支付签名返回："+JsonUtil.toJson(returnMap));
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(JsonUtil.toJson(returnMap));
		out.flush();
		out.close();
	}
}
