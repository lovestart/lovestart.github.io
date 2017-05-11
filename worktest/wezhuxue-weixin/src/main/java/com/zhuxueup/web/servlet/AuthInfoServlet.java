package com.zhuxueup.web.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zhuxueup.common.jdbc.DbService;
import com.zhuxueup.common.pay.MD5Util;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.CookieUtil;
import com.zhuxueup.common.util.EmojiFilter;
import com.zhuxueup.common.util.JsonUtil;
import com.zhuxueup.common.weixin.AdvancedUtil;
import com.zhuxueup.common.zenum.ResponseCode;
import com.zhuxueup.web.model.PacketSnagRecord;
import com.zhuxueup.web.model.RedPacket;
import com.zhuxueup.web.model.UserInfo;
import com.zhuxueup.web.model.UserToken;
import com.zhuxueup.web.pojo.SNSUserInfo;
import com.zhuxueup.web.pojo.WeixinOauth2Token;

import sun.misc.BASE64Encoder;

/**
 * 网页授权处理
 * 
 * @author chens
 * @version 1.0
 */
public class AuthInfoServlet extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		String to = request.getParameter("to");
		String packetId = request.getParameter("packetId");
		log.info("------授权code---"+code);
		Map<String, Object> map = new HashMap<String, Object>();
		String openId = "";
		String nickName = "";
		// 用户同意授权
		if (CTUtils.isNotEmpty(code) && !"authdeny".equals(code)) {
			log.info("----------开始获取accessToken------");
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(
						ConstantsUtil.getConstantsUtil().getAppId(),
						ConstantsUtil.getConstantsUtil().getAppSecret(),
						code);
			if(CTUtils.isEmpty(weixinOauth2Token)){
				map.put("code", ResponseCode.ERROR.getCode());
				map.put("errorMsg", "获取accessToken失败！");
			}else{
				// 网页授权接口访问凭证
				String accessToken = weixinOauth2Token.getAccessToken();
				log.info("----------accessToken------:"+accessToken);
				// 用户标识
				openId = weixinOauth2Token.getOpenId();
				log.info("------用户标识:"+openId);

				SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
				log.info("------获取的用户信息---"+JsonUtil.toJson(snsUserInfo));
				if (CTUtils.isEmpty(snsUserInfo)) {
					map.put("code", ResponseCode.ERROR.getCode());
					map.put("errorMsg", "获取用户信息失败！");
				} else {
					//map.put("code", ResponseCode.OK.getCode());
					snsUserInfo.setAccessToken(accessToken);
					openId = snsUserInfo.getOpenId();
					nickName = snsUserInfo.getNickname();
					//将授权用户放到cookie中
					log.info("-------将openid放到cookie中---------");
					CookieUtil.addCookie(response, "openId", openId, 60*60*24*30);
					//数据不存在插入用户
					log.info("-------判断数据库是否存在此用户---------");
					if(CTUtils.isEmpty(DbService.getUserById(openId))){
						log.info("-------不存在，插入用户---------");
						String nn = EmojiFilter.filterEmoji(nickName);
						nn = EmojiFilter.removeFourChar(nn);
						//插入数据
						UserInfo ui = new UserInfo();
						ui.setOpen_id(openId);
						ui.setNick_name(nn);
						ui.setAvatar(snsUserInfo.getHeadImgUrl());
						ui.setSex(snsUserInfo.getSex()+"");
						ui.setCity(snsUserInfo.getCity());
						ui.setCountry(snsUserInfo.getCountry());
						ui.setProvince(snsUserInfo.getProvince());
						ui.setUnionid(snsUserInfo.getUnionid());
						if(DbService.insertEntity(ui)){
							log.info("-------新加用户成功---------");
							map.put("code", ResponseCode.OK.getCode());
						}else{
							log.info("-------不存在，插入用户异常---------");
							map.put("code", ResponseCode.ERROR.getCode());
							map.put("errorMsg", "微信授权出现异常");
						}
					}else{
						log.info("-------用户已经存在---------");
						map.put("code", ResponseCode.OK.getCode());
					}
				}
			}
		} else {
			map.put("code", ResponseCode.NOOATHU.getCode());
			map.put("errorMsg", "用户没有同意授权！");
		}
		//0公众号进去直接创建红包，1可以玩，2玩过了
		//链接跳转
		String redirectUrl = "";
		if(!"200".equals(map.get("code").toString())){
			redirectUrl = ConstantsUtil.getConstantsUtil().getError_url()+"?errorMsg="+map.get("errorMsg");
		}else{
			//BASE64Encoder base = new BASE64Encoder();
			if("0".equals(to)){
				log.info("------------公众号进去，直接创建红包-----------");
				redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
						.replace("OPENID", openId)
						.replace("TO","0");
				redirectUrl += "&nickName="+URLEncoder.encode(nickName, "utf-8");
			}else if("1".equals(to)){
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
					PacketSnagRecord psr = DbService.getPacketByIdAndUser(packetId, openId);
					if(CTUtils.isEmpty(psr)){
						/**
						 * 插入token
						 */
						String token = MD5Util.MD5Encode(packetId+openId, "utf-8");
						if(CTUtils.isEmpty(DbService.getUserToken(token))){
							UserToken ut = new UserToken();
							ut.setOpen_id(openId);
							ut.setPacket_id(packetId);
							ut.setToken(token);
							boolean flag = DbService.insertEntity(ut);
							if(flag){
								log.info("--------还没有抢过红包---------");
								redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
										.replace("OPENID", openId)
										.replace("TO","1");
							}else{
								redirectUrl = ConstantsUtil.getConstantsUtil().getError_url()+"?errorMsg=错误的链接";
							}
						}else{
							redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
									.replace("OPENID", openId)
									.replace("TO","1");
						}
					}else{
						log.info("--------已经抢过红包-----------");
						redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
								.replace("OPENID", openId)
								.replace("TO", "2");
					}
				}else{
					log.info("--------红包已经被抢光了-----------");
					redirectUrl = ConstantsUtil.getConstantsUtil().getPacket_url()
							.replace("OPENID", openId)
							.replace("TO", "2");
				}
				redirectUrl += "&packetId="+packetId;
			}else{
				redirectUrl = ConstantsUtil.getConstantsUtil().getError_url()+"?errorMsg=错误的链接";
			}
		}
		log.info("--------跳转url------"+redirectUrl);	
		response.sendRedirect(redirectUrl);
	}
public static void main(String[] args){
	//
	BASE64Encoder b = new BASE64Encoder();
	System.out.println(b.encode("李建华".getBytes()));
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", 200);
	System.out.println("200".equals(map.get("code").toString()));
}
}
