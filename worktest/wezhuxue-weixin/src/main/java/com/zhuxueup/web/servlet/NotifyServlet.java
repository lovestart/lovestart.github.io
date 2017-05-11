package com.zhuxueup.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;

import com.zhuxueup.common.jdbc.DbService;
import com.zhuxueup.common.pay.SignUtil;
import com.zhuxueup.common.pay.XMLUtil;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.web.model.PayRecord;

public class NotifyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7219779388340923966L;
	private static Logger logger = Logger.getLogger(NotifyServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		 doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		logger.info("------------微信回调开始-------------");
		InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");
		PrintWriter out = response.getWriter();
		//返回结果map
        Map<String, String> params = null;
		try {
			params = XMLUtil.doXMLParse(result);
		} catch (JDOMException e) {
			e.printStackTrace();
			out.print(XMLUtil.setXML("FAIL", ""));
		}
        logger.info("------微信回调返回-------------"+params);
        if (params.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
	        //验证返回数据签名
	        String returnSign = params.remove("sign");
	        SortedMap<String,String> sort = new TreeMap<String,String>(params);
	        String sign = SignUtil.createSign("utf-8", sort);
	        if(sign.equals(returnSign)){
	        	PayRecord pr = DbService.getPayRecord(params.get("out_trade_no"));
	        	if(CTUtils.isEmpty(pr)){
	        		out.print(XMLUtil.setXML("FAIL", ""));
	        	}else{
	        		pr.setStatus("1");
	        		if(DbService.updatePayRecord(pr))
	        			out.print(XMLUtil.setXML("SUCCESS", ""));
	        		else{
	        			out.print(XMLUtil.setXML("FAIL", ""));
	        		}
	        	}
		        out.flush();
		        out.close();
	        }else{
	        	out.print(XMLUtil.setXML("FAIL", "签名失败"));
		        out.flush();
		        out.close();
	        }
        }else{
        	out.print(XMLUtil.setXML("FAIL", ""));
	        out.flush();
	        out.close();
        }
        logger.info("------------微信回调结束-------------");
		/*logger.info("---------微信支付回调----------");
		PrintWriter out = response.getWriter();
		out.print(XMLUtil.setXML("SUCCESS", ""));
        out.flush();
        out.close();*/
	}
}
