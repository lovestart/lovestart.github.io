package com.zhuxueup.web.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zhuxueup.common.jdbc.DbService;
import com.zhuxueup.common.pay.MD5Util;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.CookieUtil;
import com.zhuxueup.common.weixin.CommonUtil;
import com.zhuxueup.web.model.PacketSnagRecord;
import com.zhuxueup.web.model.RedPacket;
import com.zhuxueup.web.model.UserInfo;
import com.zhuxueup.web.model.UserToken;

/**
 * 网页授权处理
 * 
 * @author chens
 * @version 1.0
 */
public class AuthCodeServlet extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String to = request.getParameter("to");
		Cookie cookie = CookieUtil.getCookieByName(request, "openId");
		log.info("---------cookie是否存在------------");
		//判断cookie中有没有，有说明近期已经授权
		if(CTUtils.isEmpty(cookie)){
			log.info("---------cookie不存在，近期没有授权------------");
			String redirectUrl = ConstantsUtil.getConstantsUtil().getAuthInfo_url();
			redirectUrl = redirectUrl.replace("TO", to);
			if("1".equals(to)){
				String packetId = request.getParameter("packetId");
				redirectUrl += "&packetId="+packetId;
			}
			String url = ConstantsUtil.getConstantsUtil().getAuthorize_url();
			url = url.replace("APPID", ConstantsUtil.getConstantsUtil().getAppId());
			url = url.replace("REDIRECT_URI", CommonUtil.urlEncodeUTF8(redirectUrl));
			log.info("--------授权获取codeURL:" + url);
			// 直接转到微信用户同意授权页
			response.sendRedirect(url);
		}else{
			log.info("---------cookie存在，已经授权------------");
			UserInfo ui = DbService.getUserById(cookie.getValue());
			if(CTUtils.isEmpty(ui)){
				log.info("---------数据库不存在，重新授权------------");
				String redirectUrl = ConstantsUtil.getConstantsUtil().getAuthInfo_url();
				redirectUrl = redirectUrl.replace("TO", to);
				if("1".equals(to)){
					String packetId = request.getParameter("packetId");
					redirectUrl += "&packetId="+packetId;
				}
				String url = ConstantsUtil.getConstantsUtil().getAuthorize_url();
				url = url.replace("APPID", ConstantsUtil.getConstantsUtil().getAppId());
				url = url.replace("REDIRECT_URI", CommonUtil.urlEncodeUTF8(redirectUrl));
				log.info("--------授权获取codeURL:" + url);
				// 直接转到微信用户同意授权页
				response.sendRedirect(url);
			}else{
				log.info("---------数据库已存在用户，直接进行业务跳转------------");
				String redirectUrl = "";
				if("0".equals(to)){
					log.info("------------公众号进去，直接创建红包-----------");
					redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
							.replace("OPENID", cookie.getValue())
							.replace("TO","0");
					redirectUrl += "&nickName="+URLEncoder.encode(ui.getNick_name(), "utf-8");
				}else if("1".equals(to)){
					String packetId = request.getParameter("packetId");
					log.info("------------抢红包-----------红包id："+packetId);
					String snagMoney = DbService.getSnagPacketMoney(packetId);
					RedPacket rp = DbService.getRedPacketById(packetId);
					double fee = 0;
					if(Double.parseDouble(rp.getPacket_money())>=100){
						fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee2());
					}else{
						fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee1());
					}
					double leftMoney = Double.parseDouble(rp.getPacket_money())-Double.parseDouble(snagMoney)-fee;
					log.info("-----------剩余金额-----------"+leftMoney);
					if(new BigDecimal(leftMoney).setScale(2,BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ZERO)==1){
						PacketSnagRecord psr = DbService.getPacketByIdAndUser(packetId, cookie.getValue());
						if(CTUtils.isEmpty(psr)){
							/**
							 * 插入token
							 */
							String token = MD5Util.MD5Encode(packetId+cookie.getValue(), "utf-8");
							if(CTUtils.isEmpty(DbService.getUserToken(token))){
								UserToken ut = new UserToken();
								ut.setOpen_id(cookie.getValue());
								ut.setPacket_id(packetId);
								ut.setToken(token);
								boolean flag = DbService.insertEntity(ut);
								if(flag){
									log.info("--------还没有抢过红包---------");
									redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
											.replace("OPENID", cookie.getValue())
											.replace("TO","1");
								}else{
									redirectUrl = ConstantsUtil.getConstantsUtil().getError_url()+"?errorMsg=错误的链接";
								}
							}else{
								redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
										.replace("OPENID", cookie.getValue())
										.replace("TO","1");
							}
						}else{
							log.info("--------已经抢过红包-----------");
							redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
									.replace("OPENID", cookie.getValue())
									.replace("TO", "2");
						}
					}else{
						log.info("--------红包已经被抢光了-----------");
						redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
								.replace("OPENID", cookie.getValue())
								.replace("TO", "2");
					}
					redirectUrl += "&packetId="+packetId;
				}else{
					redirectUrl = ConstantsUtil.getConstantsUtil().getError_url()+"?errorMsg=错误的链接";
				}
				log.info("--------跳转url------"+redirectUrl);	
				response.sendRedirect(redirectUrl);
			}
		}
	}

}
