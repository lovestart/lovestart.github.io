package com.zhuxueup.common.zenum;
/**
 * 请求返回的信息代码
 * @author chens
 */
public enum ResponseCode {
	ERROR("请求失败",500),
	NOOATHU("未同意授权",501),
	PARAM_ERROR("参数错误",502),
	OK("业务正常",200);
	
	private String name;
	private int code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	private ResponseCode(String name, int code) {
		this.name = name;
		this.code = code;
	}
	
	public static ResponseCode fromCode(int code){
		if(code==OK.code){
			return OK;
		}else if(code==ERROR.code){
			return ERROR;
		}else{
			throw new IllegalArgumentException("Code invalid:" + code);
		}
		
	}
}
