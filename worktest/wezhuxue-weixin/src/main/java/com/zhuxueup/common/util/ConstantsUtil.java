package com.zhuxueup.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/** * 属性工具类 */
public class ConstantsUtil {
	
	private static ConstantsUtil constantsUtil;
	private Properties properties = new Properties();	 
	private String CONFIG = "constants.properties"; 
	private String authorize_url;	 
	private String accessToken_url;	 
	private String appSecret;
	private String appId;
	private String token;
	private String userInfo_url;
	private String refreshToken_url;
	private String error_url;
	
	private String authInfo_url;
	private String apiLogin_url;
	//年兽红包
	private String packet_url;
	private String hit_percent;
	private String game_time;
	private String pay_notify_url;
	private String cert_path;
	private String procedure_fee1;
	private String procedure_fee2;
	private String qrCode_path;
	//助学团
	private String zxt_authInfo_url;
	private String zxt_detail_url;
	//校友站台
	private String wtzt_authInfo_url;
	private String wtzt_detail_url;
	//兼职详情
	private String jzxq_authInfo_url;
	private String jz_detail_url;
	//散标详情
	private String sbxq_authInfo_url;
	private String fundraising_url;
	private String repayment_in_url;
	private String achieve_authInfo_url;
	private String achievement_url;
	
	private String jz_templet_url;
	private String jz_share_url;

	private ConstantsUtil() {
		String path = Thread.currentThread().getContextClassLoader().getResource(CONFIG).getPath();
		if(path.indexOf("%20")!=-1){
			path = path.replace("%20", " "); //引号中有一个半角的空格
		}
		try {
			properties.load(new FileInputStream(path));
		} catch (IOException e) {			 
			e.printStackTrace();
		}			
		authorize_url = properties.getProperty("authorize.url");
		accessToken_url = properties.getProperty("accessToken.url");
		appSecret = properties.getProperty("appSecret");
		appId = properties.getProperty("appId");
		token = properties.getProperty("token");
		userInfo_url = properties.getProperty("userInfo.url");
		refreshToken_url = properties.getProperty("refreshToken.url");
		error_url = properties.getProperty("error_url");
		authInfo_url = properties.getProperty("authInfo_url");
		apiLogin_url = properties.getProperty("apiLogin_url");
		packet_url = properties.getProperty("packet_url");
		hit_percent = properties.getProperty("hit_percent");
		game_time = properties.getProperty("game_time");
		pay_notify_url = properties.getProperty("pay_notify_url");
		cert_path = properties.getProperty("cert_path");
		procedure_fee1 = properties.getProperty("procedure_fee1");
		procedure_fee2 = properties.getProperty("procedure_fee2");
		qrCode_path = properties.getProperty("qrCode_path");
		zxt_authInfo_url = properties.getProperty("zxt_authInfo_url");
		zxt_detail_url = properties.getProperty("zxt_detail_url");
		wtzt_authInfo_url = properties.getProperty("wtzt_authInfo_url");
		wtzt_detail_url = properties.getProperty("wtzt_detail_url");
		jzxq_authInfo_url = properties.getProperty("jzxq_authInfo_url");
		jz_detail_url = properties.getProperty("jz_detail_url");
		sbxq_authInfo_url = properties.getProperty("sbxq_authInfo_url");
		fundraising_url = properties.getProperty("fundraising_url");
		repayment_in_url = properties.getProperty("repayment_in_url");
		achieve_authInfo_url = properties.getProperty("achieve_authInfo_url");
		achievement_url = properties.getProperty("achievement_url");
		jz_templet_url = properties.getProperty("jz_templet_url");
		jz_share_url = properties.getProperty("jz_share_url");
	}
	public static ConstantsUtil getConstantsUtil(){
		if (constantsUtil==null) {
			synchronized(ConstantsUtil.class){
            	if(constantsUtil==null){
            		constantsUtil=new ConstantsUtil();
            	}
			}
		}
		return constantsUtil;	 
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public String getCONFIG() {
		return CONFIG;
	}
	public void setCONFIG(String cONFIG) {
		CONFIG = cONFIG;
	}
	 
	public String getAuthorize_url() {
		return authorize_url;
	}
	public void setAuthorize_url(String authorize_url) {
		this.authorize_url = authorize_url;
	}
	public String getAccessToken_url() {
		return accessToken_url;
	}
	public void setAccessToken_url(String accessToken_url) {
		this.accessToken_url = accessToken_url;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserInfo_url() {
		return userInfo_url;
	}
	public void setUserInfo_url(String userInfo_url) {
		this.userInfo_url = userInfo_url;
	}
	public String getRefreshToken_url() {
		return refreshToken_url;
	}
	public void setRefreshToken_url(String refreshToken_url) {
		this.refreshToken_url = refreshToken_url;
	}
	public String getError_url() {
		return error_url;
	}
	public void setError_url(String error_url) {
		this.error_url = error_url;
	}
	public String getApiLogin_url() {
		return apiLogin_url;
	}
	public void setApiLogin_url(String apiLogin_url) {
		this.apiLogin_url = apiLogin_url;
	}
	public String getAuthInfo_url() {
		return authInfo_url;
	}
	public void setAuthInfo_url(String authInfo_url) {
		this.authInfo_url = authInfo_url;
	}
	public String getPacket_url() {
		return packet_url;
	}
	public void setPacket_url(String packet_url) {
		this.packet_url = packet_url;
	}
	public String getHit_percent() {
		return hit_percent;
	}
	public String getGame_time() {
		return game_time;
	}
	public void setHit_percent(String hit_percent) {
		this.hit_percent = hit_percent;
	}
	public void setGame_time(String game_time) {
		this.game_time = game_time;
	}
	public String getPay_notify_url() {
		return pay_notify_url;
	}
	public void setPay_notify_url(String pay_notify_url) {
		this.pay_notify_url = pay_notify_url;
	}
	public String getCert_path() {
		return cert_path;
	}
	public void setCert_path(String cert_path) {
		this.cert_path = cert_path;
	}
	public String getProcedure_fee1() {
		return procedure_fee1;
	}
	public String getProcedure_fee2() {
		return procedure_fee2;
	}
	public void setProcedure_fee1(String procedure_fee1) {
		this.procedure_fee1 = procedure_fee1;
	}
	public void setProcedure_fee2(String procedure_fee2) {
		this.procedure_fee2 = procedure_fee2;
	}
	public String getQrCode_path() {
		return qrCode_path;
	}
	public void setQrCode_path(String qrCode_path) {
		this.qrCode_path = qrCode_path;
	}
	public String getZxt_authInfo_url() {
		return zxt_authInfo_url;
	}
	public String getZxt_detail_url() {
		return zxt_detail_url;
	}
	public void setZxt_authInfo_url(String zxt_authInfo_url) {
		this.zxt_authInfo_url = zxt_authInfo_url;
	}
	public void setZxt_detail_url(String zxt_detail_url) {
		this.zxt_detail_url = zxt_detail_url;
	}
	public String getWtzt_authInfo_url() {
		return wtzt_authInfo_url;
	}
	public String getWtzt_detail_url() {
		return wtzt_detail_url;
	}
	public void setWtzt_authInfo_url(String wtzt_authInfo_url) {
		this.wtzt_authInfo_url = wtzt_authInfo_url;
	}
	public void setWtzt_detail_url(String wtzt_detail_url) {
		this.wtzt_detail_url = wtzt_detail_url;
	}
	public String getJzxq_authInfo_url() {
		return jzxq_authInfo_url;
	}
	public String getJz_detail_url() {
		return jz_detail_url;
	}
	public void setJzxq_authInfo_url(String jzxq_authInfo_url) {
		this.jzxq_authInfo_url = jzxq_authInfo_url;
	}
	public void setJz_detail_url(String jz_detail_url) {
		this.jz_detail_url = jz_detail_url;
	}
	public String getSbxq_authInfo_url() {
		return sbxq_authInfo_url;
	}
	public void setSbxq_authInfo_url(String sbxq_authInfo_url) {
		this.sbxq_authInfo_url = sbxq_authInfo_url;
	}
	public String getFundraising_url() {
		return fundraising_url;
	}
	public String getRepayment_in_url() {
		return repayment_in_url;
	}
	public void setFundraising_url(String fundraising_url) {
		this.fundraising_url = fundraising_url;
	}
	public void setRepayment_in_url(String repayment_in_url) {
		this.repayment_in_url = repayment_in_url;
	}
	public String getAchieve_authInfo_url() {
		return achieve_authInfo_url;
	}
	public String getAchievement_url() {
		return achievement_url;
	}
	public void setAchieve_authInfo_url(String achieve_authInfo_url) {
		this.achieve_authInfo_url = achieve_authInfo_url;
	}
	public void setAchievement_url(String achievement_url) {
		this.achievement_url = achievement_url;
	}
	public String getJz_templet_url() {
		return jz_templet_url;
	}
	public String getJz_share_url() {
		return jz_share_url;
	}
	public void setJz_templet_url(String jz_templet_url) {
		this.jz_templet_url = jz_templet_url;
	}
	public void setJz_share_url(String jz_share_url) {
		this.jz_share_url = jz_share_url;
	}

}
