package com.zhuxueup.web.model;

import java.io.Serializable;
import java.util.Date;

public class RedPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2045003944504444171L;
	private String id;
	private String open_id;
	private String wx_name;
	private String packet_name;
	private String packet_money;
	private String img_url;
	private String back_status;
	private Date create_time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpen_id() {
		return open_id;
	}
	public String getWx_name() {
		return wx_name;
	}
	public String getPacket_name() {
		return packet_name;
	}
	public String getPacket_money() {
		return packet_money;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public void setWx_name(String wx_name) {
		this.wx_name = wx_name;
	}
	public void setPacket_name(String packet_name) {
		this.packet_name = packet_name;
	}
	public void setPacket_money(String packet_money) {
		this.packet_money = packet_money;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getBack_status() {
		return back_status;
	}
	public void setBack_status(String back_status) {
		this.back_status = back_status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "RedPacket [id=" + id + ", open_id=" + open_id + ", wx_name="
				+ wx_name + ", packet_name=" + packet_name + ", packet_money="
				+ packet_money + ", img_url=" + img_url + ", back_status="
				+ back_status + "]";
	}

}
