package com.zhuxueup.web.pojo;

import java.util.List;

public class MsgResult {

	private String msgType;
	private String eventType;
	private String eventKey;
	private List<String> list;
	private String str;
	
	public List<String> getList() {
		return list;
	}
	public String getStr() {
		return str;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public String getMsgType() {
		return msgType;
	}
	public String getEventType() {
		return eventType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	@Override
	public String toString() {
		return "MsgResult [msgType=" + msgType + ", eventType=" + eventType
				+ ", eventKey=" + eventKey + ", list=" + list + ", str=" + str
				+ "]";
	}

}
