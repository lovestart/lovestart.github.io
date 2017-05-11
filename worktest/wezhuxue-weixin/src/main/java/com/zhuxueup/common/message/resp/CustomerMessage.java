package com.zhuxueup.common.message.resp;

/**
 * 客服消息
 * 
 * @author chens
 */
public class CustomerMessage extends BaseMessage {
	//指定客服
	private TransInfo TransInfo;

	public TransInfo getTransInfo() {
		return TransInfo;
	}

	public void setTransInfo(TransInfo transInfo) {
		TransInfo = transInfo;
	}
}
