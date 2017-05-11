package com.zhuxueup.web.pojo;

/**
 * 调微信登录接口传的用户信息
 */
public class UserInfoVo {
	// 用户标识
	private String openid;
	// 用户昵称
	private String userNickName;
	// 模块
	private String module;
	// token
	private String access_token;
	// 用户头像链接
	private String avatar;
	// unionid
	private String unionid;
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String nickName) {
		this.userNickName = nickName;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}
