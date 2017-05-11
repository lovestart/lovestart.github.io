package com.zhuxueup.web.vo;

public class JobCompanyVO {

	private Long id;
	private String name;
	private String logo;
	private String location;
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getLogo() {
		return logo;
	}
	public String getLocation() {
		return location;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
