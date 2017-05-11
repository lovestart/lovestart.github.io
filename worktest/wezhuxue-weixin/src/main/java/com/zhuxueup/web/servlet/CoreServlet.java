package com.zhuxueup.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.StringUtil;
import com.zhuxueup.common.weixin.SignUtil;
import com.zhuxueup.web.pojo.MsgResult;
import com.zhuxueup.web.service.CoreService;
 
/**
 * 请求处理的核心类
 * @author chens
 * @version 1.0
 */
public class CoreServlet extends HttpServlet{

	private Logger log =Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = 1L;
	private String echostr;
	
	/** 
     * 确认请求来自微信服务器 
     */ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 connect(request,response);
	}

	/** 
     * 处理微信服务器发来的消息 
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 调用核心业务类接收消息、处理消息
		MsgResult mr = CoreService.processRequest(request);

		// 响应消息
		PrintWriter out = response.getWriter();
		if("myPackets".equals(mr.getEventKey())){
			log.info("------我的红包消息-------");
			/*for(int i=0;i<mr.getList().size();i++){
				log.info("-------------消息-----------"+mr.getList().get(i));
				out.print(mr.getList().get(i));
			}*/
			log.info("-------------消息-----------"+mr.getList().get(0));
			out.print(mr.getList().get(0));
		}else{
			out.print(mr.getStr());
		}
		//out.print(respXml);
		out.flush();
		out.close();
	}

	/**
	 *@return  
	 *@exception 
	 *@param
	 * 
	 * <p>接入连接生效验证</p>
	 */
	private void connect(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		log.info("RemoteAddr: "+ request.getRemoteAddr());
		log.info("QueryString: "+ request.getQueryString());
		 if(!accessing(request, response)){
			 log.info("服务器接入失败.......!");
			 return ;
		 }
		String echostr = getEchostr();
		if(CTUtils.isNotEmpty(echostr)){
			log.info("服务器接入生效..........!");
			response.getWriter().print(echostr);//完成相互认证
		}
	}
	/**
	 * @return boolean
	 * @exception ServletException, IOException
	 * @param
	 *
	 *<p>用来接收微信公众平台的验证</p> 
	 */
	private boolean accessing(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		if(StringUtil.isBlank(signature)){
			return false;
		}
		if(StringUtil.isBlank(timestamp)){
			return false;
		}
		if(StringUtil.isBlank(nonce)){
			return false;
		}
		if(StringUtil.isBlank(echostr)){
			return false;
		}
		if(SignUtil.checkSignature(signature, timestamp, nonce)){
			this.echostr = echostr;
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	public String getEchostr(){
		return echostr;
	}
	
}
