package com.zhuxueup.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.weixin.CommonUtil;

/**
 * 网页授权处理
 * 
 * @author chens
 * @version 1.0
 */
public class SupportHimAuthCodeServlet extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String myId = request.getParameter("myId");
		String redirectUrl = ConstantsUtil.getConstantsUtil().getWtzt_authInfo_url();
		redirectUrl = redirectUrl.replace("MYID", myId);
		String url = ConstantsUtil.getConstantsUtil().getAuthorize_url();
		url = url.replace("APPID", ConstantsUtil.getConstantsUtil().getAppId());
		url = url.replace("REDIRECT_URI", CommonUtil.urlEncodeUTF8(redirectUrl));
		log.info("--------授权获取codeURL:" + url);
		// 直接转到微信用户同意授权页
		response.sendRedirect(url);
	}

}
