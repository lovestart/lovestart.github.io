package com.zhuxueup.web.model;

import java.io.Serializable;

public class PacketSnagRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4400459235780096802L;
	private Integer id;
	private String packet_id;
	private String open_id;
	private String wx_name;
	private String wx_avatar;
	private String snag_money;
	private String couplet;
	
	public Integer getId() {
		return id;
	}
	public String getPacket_id() {
		return packet_id;
	}
	public String getOpen_id() {
		return open_id;
	}
	public String getWx_name() {
		return wx_name;
	}
	public String getWx_avatar() {
		return wx_avatar;
	}
	public String getSnag_money() {
		return snag_money;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setPacket_id(String packet_id) {
		this.packet_id = packet_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public void setWx_name(String wx_name) {
		this.wx_name = wx_name;
	}
	public void setWx_avatar(String wx_avatar) {
		this.wx_avatar = wx_avatar;
	}
	public void setSnag_money(String snag_money) {
		this.snag_money = snag_money;
	}
	public String getCouplet() {
		return couplet;
	}
	public void setCouplet(String couplet) {
		this.couplet = couplet;
	}
	@Override
	public String toString() {
		return "PacketSnagRecord [id=" + id + ", packet_id=" + packet_id
				+ ", open_id=" + open_id + ", wx_name=" + wx_name
				+ ", wx_avatar=" + wx_avatar + ", snag_money=" + snag_money
				+ ", couplet=" + couplet + "]";
	}
	
}
