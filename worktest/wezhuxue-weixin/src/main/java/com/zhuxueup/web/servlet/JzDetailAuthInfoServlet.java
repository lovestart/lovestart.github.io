package com.zhuxueup.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zhuxueup.common.http.HttpClient;
import com.zhuxueup.common.http.HttpRequestResult;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.JsonUtil;
import com.zhuxueup.common.weixin.AdvancedUtil;
import com.zhuxueup.common.zenum.ResponseCode;
import com.zhuxueup.web.pojo.SNSUserInfo;
import com.zhuxueup.web.pojo.UserInfoVo;
import com.zhuxueup.web.pojo.WeixinOauth2Token;

/**
 * 网页授权处理
 * 
 * @author chens
 * @version 1.0
 */
public class JzDetailAuthInfoServlet extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		String jobId = request.getParameter("jobId");
		log.info("------授权code---"+code);
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = "";
		String openId = "";
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
					UserInfoVo user = new UserInfoVo();
					user.setAccess_token(accessToken);
					user.setAvatar(snsUserInfo.getHeadImgUrl());
					user.setModule("0");
					user.setOpenid(snsUserInfo.getOpenId());
					char[] ch = snsUserInfo.getNickname().toCharArray();
					String str = "";
					for(char c : ch){
						str += ((int)c + 888) + ",";
					}
					user.setUserNickName(str.substring(0, str.length()-1));
					user.setUnionid(snsUserInfo.getUnionid());
					map.put("version", "1.0");
					map.put("system", "weixin_oAuth");
					map.put("token", "");
					map.put("objId", "ShareVo");
					map.put("data", user);
					try {
						//HttpRequestResult result = HttpClient.httpsRequest(ConstantsUtil.getConstantsUtil().getApiLogin_url(), "POST", JsonUtil.toJson(map));
						HttpRequestResult result = HttpClient.doPost(ConstantsUtil.getConstantsUtil().getApiLogin_url(), JsonUtil.toJson(map));
						log.info("----微信登录返回数据---："+result.getResponseBody());
						map = JsonUtil.jsonToMap(result.getResponseBody());
						Map<String, Object> map1 = (Map<String, Object>) map.get("data");
						userId = map1.get("userid").toString();
						map.put("code", ResponseCode.OK.getCode());
					} catch (Exception e) {
						map.put("code", ResponseCode.ERROR.getCode());
						map.put("errorMsg", "用户登录失败！");
						e.printStackTrace();
					}
				}
			}
		} else {
			map.put("code", ResponseCode.NOOATHU.getCode());
			map.put("errorMsg", "用户没有同意授权！");
		}
		log.info("--------map数据：--------"+map);
		//链接跳转
		String redirectUrl = "";
		if(!"200".equals(map.get("code").toString())){
			redirectUrl = ConstantsUtil.getConstantsUtil().getError_url()+"?errorMsg="+map.get("errorMsg");
		}else{
			redirectUrl = ConstantsUtil.getConstantsUtil().getJz_detail_url()+"?jobId="+jobId+"&userId="+userId;
		}
		log.info("--------跳转url------"+redirectUrl);	
		response.sendRedirect(redirectUrl);
	}

}
