package com.zhuxueup.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;

public class MyFilter implements Filter {
	
	private Logger log = Logger.getLogger(MyFilter.class);

	public MyFilter() {
	}

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest sr = (HttpServletRequest) request;
		String requestUri = sr.getRequestURI();
		log.info("-----拦截请求地址----"+sr.getRequestURL());
		log.info("-------拦截的URI----"+requestUri);
		if(requestUri.indexOf("index.html")!=-1){
			String code = request.getParameter("code");
			log.info("------获取的code："+code);
			if(CTUtils.isEmpty(code)){
				HttpServletResponse sp = (HttpServletResponse) response;
				sp.sendRedirect(ConstantsUtil.getConstantsUtil().getError_url());
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
