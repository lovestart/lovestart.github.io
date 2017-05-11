package com.zhuxueup.common.weixin;

import java.util.Date;

import com.zhuxueup.common.message.resp.CustomerMessage;
import com.zhuxueup.common.message.resp.TextMessage;
import com.zhuxueup.common.message.resp.TransInfo;
import com.zhuxueup.common.util.ConstantsUtil;

public class Test {
	public static void main(String[] args){
		// 获取接口访问凭证
		String accessToken = CommonUtil.getToken(ConstantsUtil.getConstantsUtil().getAppId(), ConstantsUtil.getConstantsUtil().getAppSecret()).getAccessToken();
//		
//		String json = "{\"kf_account\":\"li@zhuxueup\",\"nickname\":\"李建华\",\"password\":\"202cb962ac59075b964b07152d234b70\"}";
//		AdvancedUtil.addCustomer(accessToken, null);
//		
//		/**
//		 * 发送客服消息（文本消息）
//		 */
//		// 组装文本客服消息
//		String jsonTextMsg = AdvancedUtil.makeTextCustomMessage("ojnohwRyrbmC23b0dsEU22BYzM3c", "在不在");
		// 发送客服消息
		//AdvancedUtil.sendCustomMessage(accessToken, jsonTextMsg);
		String str = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token="+accessToken;
		System.out.println(CommonUtil.httpsRequest(str, "GET", null));
	}
}
