package com.zhuxueup.common.pay;

public class WXConstants {
	//微信公众号appid
	public final static String WX_APP_ID = "wxd1250162d2371946";
	//微信商户id
	public final static String WX_MCH_ID = "1433585402";
	//密钥key
	public final static String WX_KEY = "h89DH4AD89SASH3w4JJDJjJHAdy5bhsd";
	//微信统一下单
	public final static String WX_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//微信公众号支付的签名方式
	public final static String WX_SIGN_TYPE = "MD5";
	
	//企业付款api
	public final static String WX_COMPANY_PAY_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	//公众号发红包api
	public final static String WX_PUB_RED_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
}
