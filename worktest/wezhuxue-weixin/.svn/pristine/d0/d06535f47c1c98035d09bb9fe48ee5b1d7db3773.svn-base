package com.zhuxueup.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zhuxueup.common.util.JsonUtil;
import com.zhuxueup.common.zenum.ResponseCode;
import com.zhuxueup.web.service.PacketService;

public class PacketServlet extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		//String callback = request.getParameter("callback");
		log.info("-----------接口调用，要请求的的method："+method);
		Map<String, Object> map = new HashMap<String, Object>();
		if("createPacket".equals(method)){
			log.info("-----------创建红包-----------");
			try {
				map = PacketService.createPacket(request);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", ResponseCode.ERROR.getCode());
				map.put("msg", "服务器异常");
			}
		}else if("packetBasic".equals(method)){
			//没抢过红包时的接口
			log.info("----------获取红包基本信息----------");
			try {
				map = PacketService.packetBasicInfo(request);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", ResponseCode.ERROR.getCode());
				map.put("msg", "服务器异常");
			}
		}else if("grabPacketInfo".equals(method)){
			//抢过红包时的接口
			log.info("----------抢完红包获取红包抢劫信息---------");
			try {
				map = PacketService.grabPacketInfos(request);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", ResponseCode.ERROR.getCode());
				map.put("msg", "服务器异常");
			}
		}else if("userAndPacketInfo".equals(method)){
			//动画页，红包及用户信息
			log.info("----------游戏动画页，红包信息---------");
			try {
				map = PacketService.userAndPacketInfo(request);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", ResponseCode.ERROR.getCode());
				map.put("msg", "服务器异常");
			}
		}else if("grabThePacket".equals(method)){
			log.info("----------游戏结束后录入抢到的红包信息---------");
			try {
				map = PacketService.grabThePacket(request);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", ResponseCode.ERROR.getCode());
				map.put("msg", "服务器异常");
			}
		}else if("getQRCodeImg".equals(method)){
			log.info("----------生成二维码图片信息---------");
			try {
				map = PacketService.getQRCodeImg(request);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", ResponseCode.ERROR.getCode());
				map.put("msg", "服务器异常");
			}
		}
		else if("getUserPackets".equals(method)){
			log.info("----------生成二维码图片信息---------");
			try {
				map = PacketService.getUserPackets(request);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", ResponseCode.ERROR.getCode());
				map.put("msg", "服务器异常");
			}
		}
		/*else if("imgUpload".equals(method)){
			log.info("---------图片上传--------------");
			map = PacketService.uploadImg(request);
		}*/
		else{
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，method不对");
		}
		log.info("------------红包业务返回数据："+JsonUtil.toJson(map));
		// 响应消息
		PrintWriter out = response.getWriter();
		//out.print(callback+"("+JsonUtil.toJson(map)+")");
		out.print(JsonUtil.toJson(map));
		out.flush();
		out.close();
	}
}
