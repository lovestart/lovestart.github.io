package com.zhuxueup.web.model;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2045003944504444171L;
	private Integer id;
	private String open_id;
	private String nick_name;
	private String avatar;
	private String sex;
	private String province;
	private String city;
	private String country;
	private String unionid;
	
	public Integer getId() {
		return id;
	}
	public String getOpen_id() {
		return open_id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSex() {
		return sex;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", open_id=" + open_id + ", nick_name="
				+ nick_name + ", avatar=" + avatar + ", sex=" + sex
				+ ", province=" + province + ", city=" + city + ", country="
				+ country + ", unionid=" + unionid + "]";
	}

}
