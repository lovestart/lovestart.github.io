package com.zhuxueup.web.vo;

public class JobInfoVO {

	private Long id;
	private Long companyId;
	private Integer classifyId;
	private String name;
	private String salary;
	private String payType;
	private String cityId;
	private String city;
	private String area;
	private String workPlace;
	private String recruitment;
	private String gender;
	private String workTime;
	private String workDate;
	private String jobDescription;
	private String linkman;
	private String contactWay;
	private String jobStatus;
	private String infoStatus;
	private String publishUser;
	
	public Long getId() {
		return id;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public Integer getClassifyId() {
		return classifyId;
	}
	public String getName() {
		return name;
	}
	public String getSalary() {
		return salary;
	}
	public String getPayType() {
		return payType;
	}
	public String getCityId() {
		return cityId;
	}
	public String getCity() {
		return city;
	}
	public String getArea() {
		return area;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public String getRecruitment() {
		return recruitment;
	}
	public String getGender() {
		return gender;
	}
	public String getWorkTime() {
		return workTime;
	}
	public String getWorkDate() {
		return workDate;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public String getLinkman() {
		return linkman;
	}
	public String getContactWay() {
		return contactWay;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public String getInfoStatus() {
		return infoStatus;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public void setClassifyId(Integer classifyId) {
		this.classifyId = classifyId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public void setRecruitment(String recruitment) {
		this.recruitment = recruitment;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public void setInfoStatus(String infoStatus) {
		this.infoStatus = infoStatus;
	}
	public String getPublishUser() {
		return publishUser;
	}
	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}
	@Override
	public String toString() {
		return "JobInfoVO [id=" + id + ", companyId=" + companyId
				+ ", classifyId=" + classifyId + ", name=" + name + ", salary="
				+ salary + ", payType=" + payType + ", cityId=" + cityId
				+ ", city=" + city + ", area=" + area + ", workPlace="
				+ workPlace + ", recruitment=" + recruitment + ", gender="
				+ gender + ", workTime=" + workTime + ", workDate=" + workDate
				+ ", jobDescription=" + jobDescription + ", linkman=" + linkman
				+ ", contactWay=" + contactWay + ", jobStatus=" + jobStatus
				+ ", infoStatus=" + infoStatus + "]";
	}
}
