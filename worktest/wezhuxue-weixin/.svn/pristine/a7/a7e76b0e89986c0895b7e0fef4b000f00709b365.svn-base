package com.zhuxueup.web.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.zhuxueup.common.jdbc.DbService;
import com.zhuxueup.common.oss.AliyunOSSUtil;
import com.zhuxueup.common.pay.CommonUtil;
import com.zhuxueup.common.pay.MD5Util;
import com.zhuxueup.common.pay.SignUtil;
import com.zhuxueup.common.pay.WXConstants;
import com.zhuxueup.common.pay.WXPayUtil;
import com.zhuxueup.common.qrcode.TwoDimensionCode;
import com.zhuxueup.common.qrcode.TwoImageCompose;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.DateStyle;
import com.zhuxueup.common.util.DateUtil;
import com.zhuxueup.common.util.IdGenerator;
import com.zhuxueup.common.util.ImgConvertUtil;
import com.zhuxueup.common.util.RandomUtil;
import com.zhuxueup.common.util.RandomUtil.TYPE;
import com.zhuxueup.common.util.StringUtil;
import com.zhuxueup.common.zenum.ResponseCode;
import com.zhuxueup.web.model.PacketSnagRecord;
import com.zhuxueup.web.model.PayRecord;
import com.zhuxueup.web.model.Records;
import com.zhuxueup.web.model.RedPacket;
import com.zhuxueup.web.model.UserInfo;
import com.zhuxueup.web.model.UserToken;
import com.zhuxueup.web.vo.PacketVO;

public class PacketService {

	private static Logger log = Logger.getLogger(PacketService.class);
	/**
	 * 创建红包
	 * @param request
	 * @return
	 */
	public static Map<String, Object> createPacket(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String openId = request.getParameter("openId");
		String wxName = request.getParameter("wxName");
		log.info("--------微信昵称------"+wxName);
		String packetMoney = request.getParameter("packetMoney");
		String packetName = request.getParameter("packetName");
		log.info("--------红包名称------"+packetName);
		String packetImg = request.getParameter("packetImg");
		if(CTUtils.isEmpty(openId)){
			log.info("----------请求参数openId为空");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，openId为空");
			return map;
		}
		if(CTUtils.isEmpty(wxName)){
			log.info("----------请求参数微信昵称为空");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，微信昵称为空");
			return map;
		}
		/*BASE64Decoder decoder = new BASE64Decoder();
		try {
			wxName = new String(decoder.decodeBuffer(wxName));
		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，微信昵称错误");
		}*/
		if(CTUtils.isEmpty(packetMoney)){
			log.info("----------请求参数红包金额为空");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，红包金额为空");
			return map;
		}
		if(CTUtils.isNotEmpty(packetImg)){
			log.info("------图片base64编码："+packetImg);
			String dirFile = request.getSession().getServletContext().getRealPath("")+"/upload/";
			String fileName = "u"+System.currentTimeMillis()+""+RandomUtil.getRandom(6, TYPE.LETTER);
			try {
				boolean b = ImgConvertUtil.createImgByBASE64(packetImg, new File(dirFile), dirFile+fileName+".png");
				if(b){
					String videoKey = "packet/"+fileName;
					//AliyunOSSUtil.uploadFile(null, videoKey, new File(dirFile+fileName));
	        		if(!AliyunOSSUtil.uploadFile(null, videoKey, new File(dirFile+fileName+".png"))){
	        			map.put("code", ResponseCode.ERROR.getCode());
	                	map.put("msg", "图片上传失败");
	                    return map;
	        		}else{
	        			packetImg = videoKey;
	        		}
				}else{
					map.put("code", ResponseCode.ERROR.getCode());
                	map.put("msg", "图片上传失败");
                    return map;
				}
			} catch (IOException e) {
				e.printStackTrace();
				map.put("code", ResponseCode.ERROR.getCode());
            	map.put("msg", "图片上传失败");
                return map;
			}
		}
		RedPacket rp = new RedPacket();
		String id = IdGenerator.nextId();
		rp.setId(id);
		rp.setOpen_id(openId);
		rp.setWx_name(wxName);
		rp.setPacket_name(packetName);
		rp.setImg_url(packetImg);
		rp.setPacket_money(packetMoney);
		log.info("----------插入对象，红包："+rp.toString());
		boolean flag = DbService.insertEntity(rp);
		if(flag){
			log.info("---------创建红包成功----------");
			/*年兽叼走手续费
			PacketSnagRecord psr = new PacketSnagRecord();
			psr.setCouplet("");
			psr.setOpen_id("nianshou");
			psr.setPacket_id(id);
			psr.setWx_name(packetName);
			psr.setWx_avatar("http://123.56.207.57/img/activity/ns_avatar.jpg");
			psr.setSnag_money("0.5");
			DbService.insertEntity(psr);*/
			map.put("code", ResponseCode.OK.getCode());
			map.put("msg", "创建红包成功");
			map.put("packetId", id);
		}else{
			log.info("---------创建红包失败----------");
			map.put("code", ResponseCode.ERROR.getCode());
			map.put("msg", "创建红包失败");
		}
		return map;
	}
	/**
	 * 消息页红包基本信息
	 * @param request
	 * @return
	 */
	public static Map<String, Object> packetBasicInfo(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String packetId = request.getParameter("packetId");
		String openId = request.getParameter("openId");
		if(CTUtils.isEmpty(packetId)){
			log.info("----------请求参数packetId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，红包Id为空");
			return map;
		}
		if(CTUtils.isEmpty(openId)){
			log.info("----------请求参数openId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，用户openId为空");
			return map;
		}
		RedPacket rp = DbService.getRedPacketById(packetId);
		if(CTUtils.isEmpty(rp)){
			log.info("----------没有获取到红包，id："+packetId);
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "没有获取到红包");
			return map;
		}
		PacketSnagRecord psr = DbService.getPacketByIdAndUser(packetId, openId);
		if(CTUtils.isEmpty(psr)){
			log.info("----------没有抢过红包："+packetId+"，openId："+openId);
			map.put("grabStatus", "1");
		}else{
			log.info("----------已经抢过红包了："+packetId+"，openId："+openId);
			map.put("grabStatus", "0");
		}
		if("1".equals(rp.getBack_status())){
			log.info("---------红包过期了----------");
			map.put("grabStatus", "0");
		}
		map.put("packetName", rp.getPacket_name());
		map.put("wxName", rp.getWx_name());
		map.put("packetMoney", rp.getPacket_money());
		if(CTUtils.isNotEmpty(rp.getImg_url())){
			map.put("packetImg", AliyunOSSUtil.getFile(rp.getImg_url()));
		}
		map.put("code", ResponseCode.OK.getCode());
		map.put("msg", "成功");
		return map;
	}
	/**
	 * 抢的红包记录
	 * @param request
	 * @return
	 */
	public static Map<String, Object> grabPacketInfos(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String packetId = request.getParameter("packetId");
		String openId = request.getParameter("openId");
		if(CTUtils.isEmpty(packetId)){
			log.info("----------请求参数packetId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，红包Id为空");
			return map;
		}
		if(CTUtils.isEmpty(openId)){
			log.info("----------请求参数openId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，openId为空");
			return map;
		}
		UserInfo ui = DbService.getUserById(openId);
		if(CTUtils.isEmpty(ui)){
			log.info("----------请求参数openId错误----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，用户不存在");
			return map;
		}
		RedPacket rp = DbService.getRedPacketById(packetId);
		if(CTUtils.isEmpty(rp)){
			log.info("----------请求参数packetId错误----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，不存在此红包");
			return map;
		}else{
			double fee = 0;
			if(Double.parseDouble(rp.getPacket_money())>=100){
				fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee2());
			}else{
				fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee1());
			}
			String snagMoney = DbService.getSnagPacketMoney(packetId);
			log.info("----------已经抢走的金额："+snagMoney);
			String totalMoney = rp.getPacket_money()+"";
			map.put("snagMoneys", new BigDecimal(Double.parseDouble(snagMoney)).setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("totalMoney", totalMoney);
			if(CTUtils.isEmpty(DbService.getRedPacketByIdNotExpire(packetId))||"1".equals(rp.getBack_status())){
				log.info("---------红包过期了----------");
				map.put("grabStatus", "0");
				map.put("couplet", "1");
				map.put("totalMoney", new BigDecimal(Double.parseDouble(snagMoney)+fee).setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				PacketSnagRecord psr = DbService.getPacketByIdAndUser(packetId, openId);
				if(CTUtils.isEmpty(psr)){
					log.info("----------红包已经抢完，没有抢到红包，红包id："+packetId+"，openId："+openId);
					map.put("grabStatus", "0");
					map.put("couplet", "1");
				}else{
					log.info("----------抢到红包了，红包id："+packetId+"，openId："+openId);
					if("0".equals(psr.getSnag_money())){
						map.put("grabStatus", "2");
						map.put("couplet", psr.getCouplet());
						map.put("snagMoney", psr.getSnag_money());
					}else{
						map.put("grabStatus", "1");
						map.put("couplet", psr.getCouplet());
						map.put("snagMoney", psr.getSnag_money());
					}
				}
			}
			map.put("currUser", ui.getNick_name());
			//抢红包的记录
			List<PacketSnagRecord> list = DbService.getPacketSnagers(packetId);
			Records r = null;
			List<Records> records = new ArrayList<Records>();
			//获取用户抢红包的列表
			if(CTUtils.isNotEmpty(list)){
				double per = 0;
				for(PacketSnagRecord p : list){
					per = Double.parseDouble(p.getSnag_money()) / Double.parseDouble(rp.getPacket_money()) * 100;
					r = new Records();
					r.setAvatar(p.getWx_avatar());
					r.setMoney(p.getSnag_money());
					r.setName(p.getWx_name());
					r.setPercent(new BigDecimal(per).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
					records.add(r);
				}
			}
			double leftMoney = Double.parseDouble(rp.getPacket_money())-Double.parseDouble(snagMoney)-fee;
			if(new BigDecimal(leftMoney).setScale(2,BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ZERO)!=1){
				//设置年兽抢的部分，放到列表最下面
				r = new Records();
				r.setAvatar("http://123.56.207.57/img/activity/ns_avatar.png");
				r.setMoney(fee+"");
				r.setPercent("0");
				r.setName("小年兽");
				records.add(r);
				map.put("snagMoneys", rp.getPacket_money());
			}
			log.info("----------抢红包排序的记录-------"+records.toString());
			map.put("records", records);
			map.put("code", ResponseCode.OK.getCode());
			map.put("msg", "成功");
			return map;
		}
	}
	/**
	 * 抢红包动画页
	 * @param request
	 * @return
	 */
	public static Map<String, Object> userAndPacketInfo(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String packetId = request.getParameter("packetId");
		String openId = request.getParameter("openId");
		if(CTUtils.isEmpty(packetId)){
			log.info("----------请求参数packetId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，红包Id为空");
			return map;
		}
		if(CTUtils.isEmpty(openId)){
			log.info("----------请求参数openId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，openId为空");
			return map;
		}
		RedPacket rp = DbService.getRedPacketById(packetId);
		if(CTUtils.isEmpty(rp)){
			log.info("----------没有获取到红包，id："+packetId);
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "没有获取到红包");
			return map;
		}
		UserInfo ui = DbService.getUserById(openId);
		if(CTUtils.isEmpty(ui)){
			log.info("----------没有获取到用户，openId："+openId);
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "没有获取到用户");
			return map;
		}
		/*UserToken ut = new UserToken();
		ut.setOpen_id(openId);
		ut.setPacket_id(packetId);
		ut.setToken(MD5Util.MD5Encode(packetId+openId, "utf-8"));
		boolean flag = DbService.insertEntity(ut);
		if(flag){*/
		map.put("hitPercent", ConstantsUtil.getConstantsUtil().getHit_percent());
		map.put("gameTime", ConstantsUtil.getConstantsUtil().getGame_time());
		if(CTUtils.isNotEmpty(rp.getImg_url())){
			map.put("packetImg", AliyunOSSUtil.getFile(rp.getImg_url()));
		}
		map.put("userNickName", ui.getNick_name());
		map.put("userAvatar", ui.getAvatar());
		map.put("code", ResponseCode.OK.getCode());
		map.put("msg", "成功");
		/*}else{
			map.put("code", ResponseCode.ERROR.getCode());
			map.put("msg", "服务器异常");
		}*/
		return map;
	}
	/**
	 * 抢红包接口
	 * @param request
	 * @return
	 */
	public static Map<String, Object> grabThePacket(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String packetId = request.getParameter("packetId");
		String openId = request.getParameter("openId");
		String hitPercent = request.getParameter("hitPercent");
		//String couplet = request.getParameter("couplet");
		if(CTUtils.isEmpty(packetId)){
			log.info("----------请求参数packetId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，红包Id为空");
			return map;
		}
		if(CTUtils.isEmpty(openId)){
			log.info("----------请求参数openId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，openId为空");
			return map;
		}
		if(CTUtils.isEmpty(hitPercent)){
			log.info("----------请求参数打击多少为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，打击量为空");
			return map;
		}
		RedPacket rp = DbService.getRedPacketById(packetId);
		if(CTUtils.isEmpty(rp)){
			log.info("----------没有获取到红包，id："+packetId);
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "没有获取到红包");
			return map;
		}
		UserInfo ui = DbService.getUserById(openId);
		if(CTUtils.isEmpty(ui)){
			log.info("----------没有获取到用户，openId："+openId);
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "没有获取到用户");
			return map;
		}
		PacketSnagRecord record = DbService.getPacketByIdAndUser(packetId, openId);
		if(CTUtils.isNotEmpty(record)){
			log.info("----------已经抢过红包了-----------");
			map.put("code", ResponseCode.OK.getCode());
			map.put("msg", "已经抢过红包了");
			return map;
		}
		double fee = 0;
		if(Double.parseDouble(rp.getPacket_money())>=100){
			fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee2());
		}else{
			fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee1());
		}
		String money = DbService.getSnagPacketMoney(packetId);
		if(new BigDecimal(Double.parseDouble(money)+fee).setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(rp.getPacket_money()).setScale(2, BigDecimal.ROUND_HALF_UP))!=-1){
			log.info("----------红包已经被抢完了--------------");
			map.put("code", ResponseCode.OK.getCode());
			map.put("msg", "红包已经被抢光了");
			return map;
		}
		String token = MD5Util.MD5Encode(packetId+openId, "utf-8");
		UserToken ut = DbService.getUserToken(token);
		if(CTUtils.isEmpty(ut)){
			log.info("----------没有玩游戏直接抢红包，被攻击，刷接口--------------");
			map.put("code", ResponseCode.ERROR.getCode());
			map.put("msg", "请放过我们，不要刷链接");
			return map;
		}
		if("1".equals(ut.getStatus())){
			log.info("----------已经抢过了，不断刷新--------------");
			map.put("code", ResponseCode.ERROR.getCode());
			map.put("msg", "请不要重复刷新哦");
			return map;
		}
		double leftMoney = Double.parseDouble(rp.getPacket_money())-Double.parseDouble(money)-fee;
		PacketSnagRecord psr = new PacketSnagRecord();
		//psr.setCouplet(couplet);
		psr.setOpen_id(openId);
		psr.setPacket_id(packetId);
		psr.setWx_name(ui.getNick_name());
		psr.setWx_avatar(ui.getAvatar());
		log.info("----------打击量--------------"+hitPercent);
		double snagMoney = 0;
		if("0".equals(hitPercent)){
			psr.setSnag_money("0");
		}else{
			/*原推广用的，去掉
			 * snagMoney = (Double.parseDouble(rp.getPacket_money())-fee) * (Double.parseDouble(hitPercent)/3) * Double.parseDouble("0.8");
			//打击不足一块，按一块算
			if(snagMoney < 1){
				snagMoney = 1;
			}
			if(leftMoney - snagMoney < 1){
				snagMoney = leftMoney;
			}*/
			
			if(Double.parseDouble(rp.getPacket_money())>=20){
				snagMoney = (Double.parseDouble(rp.getPacket_money())-fee) * (Double.parseDouble(hitPercent)/3) * Double.parseDouble("0.8");
				//打击不足一块，按一块算
				if(snagMoney < 1){
					snagMoney = 1;
				}
				if(leftMoney - snagMoney < 1){
					snagMoney = leftMoney;
				}
			}else if(Double.parseDouble(rp.getPacket_money())>10){
				if((Double.parseDouble(hitPercent)/3)>0.18){
					snagMoney = 1 + (Double.parseDouble(hitPercent)/3) * 19;
				}else if((Double.parseDouble(hitPercent)/3)>0.11){
					snagMoney = 1 + (Double.parseDouble(hitPercent)/3) * 11;
				}else if((Double.parseDouble(hitPercent)/3)>0.05){
					snagMoney = 1 + (Double.parseDouble(hitPercent)/3) * 5;
				}else{
					snagMoney = 1 + (Double.parseDouble(hitPercent)/3);
				}
				//snagMoney = 1 + Double.parseDouble(hitPercent) * 10;
				if(leftMoney - snagMoney < 1){
					snagMoney = leftMoney;
				}
			}else{
				if((Double.parseDouble(hitPercent)/3)>0.19){
					snagMoney = 1 + (Double.parseDouble(hitPercent)/3) * 13;
				}else if((Double.parseDouble(hitPercent)/3)>0.12){
					snagMoney = 1 + (Double.parseDouble(hitPercent)/3) * 8;
				}else if((Double.parseDouble(hitPercent)/3)>0.06){
					snagMoney = 1 + (Double.parseDouble(hitPercent)/3) * 4;
				}else{
					snagMoney = 1 + (Double.parseDouble(hitPercent)/3);
				}
				//snagMoney = 1 + Double.parseDouble(hitPercent) * 2;
				if(leftMoney - snagMoney < 1){
					snagMoney = leftMoney;
				}
			}
			psr.setSnag_money(new BigDecimal(snagMoney).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
		log.info("----------打击获得的钱--------------"+snagMoney);
		boolean flag = DbService.insertEntity(psr);
		if(flag){
			ut.setStatus("1");
			flag = DbService.updateUserToken(ut);
		}
		if(flag && snagMoney >= 1){
			log.info("---------抢到红包了---------");
			//微信企业付款
			SortedMap<String, String> sm = new TreeMap<String, String>();
			String orderNum = IdGenerator.wxCouponOrder();
			if(orderNum.length() > 18){
				orderNum = orderNum.substring(0, 18);
			}else if(orderNum.length() < 18){
				for(int i=0;i<(18-orderNum.length());i++){
					orderNum += "0";
				}
			}
			//long timestamp = System.currentTimeMillis() / 1000;
			String nonceStr = UUID.randomUUID().toString().substring(0, 32);
	        
	        sm.put("nonce_str", nonceStr);
	        sm.put("mch_billno", WXConstants.WX_MCH_ID+orderNum);
	        sm.put("mch_id", WXConstants.WX_MCH_ID);
	        sm.put("wxappid", WXConstants.WX_APP_ID);
	        sm.put("send_name", StringUtil.getWordCountCode(rp.getWx_name(),"UTF-8")>30?"小年兽":rp.getWx_name());
	        sm.put("re_openid", openId);
	        sm.put("total_amount", new BigDecimal(snagMoney).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
	        //sm.put("total_amount", "100");
	        sm.put("total_num", "1");
	        sm.put("wishing", "感谢您参加年兽红包活动，祝您鸡年大吉！");
	        sm.put("act_name", "打年兽抢红包活动");
	        sm.put("client_ip", request.getLocalAddr());
	        sm.put("remark", "多玩多抢，鸡年大吉！");
	       
	        String paySign = SignUtil.createSign("utf-8", sm);
	        log.info("普通红包*****微信签名***************************="+paySign);
	        sm.put("sign", paySign);
	        String xml = CommonUtil.ArrayToXml(sm);
	        log.info("请求参数*****************************="+xml);
	        Map<String,String> preMap = WXPayUtil.publicRedPacket(xml);
	        log.info("普通红包返回参数*****************************preMap="+preMap);
	        
	        if(!"SUCCESS".equals(preMap.get("return_code"))){
	        	map.put("code",ResponseCode.ERROR.getCode());
	        	map.put("code","发红包出现异常");
	        	DbService.deleteSnagRecord(psr);
	        	ut.setStatus("0");
	        	DbService.updateUserToken(ut);
			}else if(!"SUCCESS".equals(preMap.get("result_code"))){
				map.put("code",ResponseCode.ERROR.getCode());
				map.put("code","发红包出现异常");
				DbService.deleteSnagRecord(psr);
	        	ut.setStatus("0");
	        	DbService.updateUserToken(ut);
			}else{
				PayRecord pay = new PayRecord();
		        pay.setId(IdGenerator.nextId());
		        pay.setOrder_num(orderNum);
		        pay.setOpen_id(openId);
		        pay.setAmount(new BigDecimal(snagMoney).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		        pay.setStatus("1");
		        pay.setType("2");
		        DbService.insertEntity(pay);
				map.put("code", ResponseCode.OK.getCode());
				map.put("msg", "抢到红包了，666");
			}
		}else{
			if("0".equals(hitPercent)){
				log.info("---------打击量为0----------");
				map.put("code", ResponseCode.OK.getCode());
				map.put("msg", "抢到红包了，不过有点遗憾");
			}else{
				log.info("---------抢红包出现问题----------");
				map.put("code", ResponseCode.ERROR.getCode());
				map.put("msg", "抢红包失败");
			}
		}
		return map;
	}
	/**
	 * 抢红包接口
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getQRCodeImg(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String packetId = request.getParameter("packetId");
		if(CTUtils.isEmpty(packetId)){
			log.info("----------请求参数packetId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，红包Id为空");
			return map;
		}
		RedPacket rp = DbService.getRedPacketById(packetId);
		if(CTUtils.isEmpty(rp)){
			log.info("----------没有获取到红包，id："+packetId);
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "没有获取到红包");
			return map;
		}
		String imgPath = ConstantsUtil.getConstantsUtil().getQrCode_path()+"QRCode/"+packetId+"_QRCode.png";
		String encoderContent = "http://wx.wezhuxue.com/wezhuxue-weixin/authCode?to=1&packetId="+packetId;
		TwoDimensionCode handler = new TwoDimensionCode();
		handler.encoderQRCode(encoderContent, imgPath, "png", 11);
		TwoImageCompose.createPicTwo2(ConstantsUtil.getConstantsUtil().getQrCode_path()+"packet_qrcode.jpg",
				imgPath, ConstantsUtil.getConstantsUtil().getQrCode_path()+"Packet/"+packetId+"_packet.png",
				264, 1044);
		String key = "qr_packet/"+packetId;
		if(AliyunOSSUtil.uploadFile(null, key, new File(ConstantsUtil.getConstantsUtil().getQrCode_path()+"Packet/"+packetId+"_packet.png"))){
			map.put("code", ResponseCode.OK.getCode());
			map.put("msg", "成功");
			map.put("qrcodeImg", AliyunOSSUtil.getFile(key));
		}else{
			map.put("code", ResponseCode.ERROR.getCode());
			map.put("msg", "二维码生成失败");
		}
		return map;
	}
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	public static Map<String, Object> uploadImg(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		// 实例化一个硬盘文件工厂，用来配置文件上传组件ServletFileUpload
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		//上传路径
		String uploadPath = request.getSession().getServletContext().getRealPath("")+"/upload/";
		File directory = new File(uploadPath);  
        if (!directory.exists()) {  
            try {
				FileUtils.forceMkdir(directory);
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }
        factory.setRepository(directory);
		// 利用硬盘文件工厂配置文件上传组件
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 存放FileItem对象
		List<FileItem> items = null; 
		try { 
			// 获取文件的FileItem对象，即表单域,分为普通表单域和文件域
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			if (e instanceof SizeLimitExceededException) {  
                 
            }
            e.printStackTrace();
		}
		// 没有文件上传  
        if (items == null || items.size() == 0) {  
        	map.put("code", ResponseCode.PARAM_ERROR);
        	map.put("msg", "没有文件上传");
            return map;
        }  
        // 得到所有上传的文件  
        Iterator<FileItem> fileItr = items.iterator();  
        // 循环处理所有文件  
        while (fileItr.hasNext()) {  
            FileItem fileItem = null;  
            String path = null;  
            long size = 0;  
            // 得到当前文件  
            fileItem = (FileItem) fileItr.next();  
            // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
            if (fileItem == null || fileItem.isFormField()) {  
                continue;  
            }  
            // 得到文件的完整路径  
            path = fileItem.getName();  
            // 得到文件的大小  
            size = fileItem.getSize();  
            if ("".equals(path) || size == 0) {  
            	map.put("code", ResponseCode.PARAM_ERROR);
            	map.put("msg", "请选择上传文件");
                return map;  
            }  
            //文件名  
            String fileName = "u"+System.currentTimeMillis()+""+RandomUtil.getRandom(6, TYPE.CAPITAL);
            uploadPath = uploadPath+"/"+fileName+path.substring(path.indexOf("."), path.length());  
            try {
                // 保存文件  
            	File file = new File(uploadPath);
                fileItem.write(file);
                String videoKey = "packet/"+fileName;
        		if(!AliyunOSSUtil.uploadFile(null, videoKey, file)){
        			map.put("code", ResponseCode.ERROR);
                	map.put("msg", "文件上传失败");
                    return map;
        		}else{
        			map.put("code", ResponseCode.OK);
                	map.put("msg", "文件上传成功");
                	map.put("packetImg", videoKey);
                    return map;
        		}
            } catch (Exception e) {  
                e.printStackTrace(); 
                map.put("code", ResponseCode.ERROR);
            	map.put("msg", "文件上传失败");
                return map;
            }
        }
		return map;
	}
	/**
	 * 获取用户所有红包
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getUserPackets(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String openId = request.getParameter("openId");
		if(CTUtils.isEmpty(openId)){
			log.info("----------请求参数openId为空----------");
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "请求参数错误，openId为空");
			return map;
		}
		UserInfo ui = DbService.getUserById(openId);
		if(CTUtils.isEmpty(ui)){
			log.info("----------没有获取到用户，openId："+openId);
			map.put("code", ResponseCode.PARAM_ERROR.getCode());
			map.put("msg", "没有获取到用户");
			return map;
		}
		List<RedPacket> packets = DbService.getUserPackets(openId);
		log.info("---------创建的红包："+packets);
		if(CTUtils.isEmpty(packets)){
			log.info("--------还没创建红包-------------");
			map.put("code", ResponseCode.OK.getCode());
			map.put("msg", "成功");
			map.put("status", "0");
		}else{
			log.info("----------------创建过红包了-------总共："+packets.size());
			map.put("code", ResponseCode.OK.getCode());
			map.put("msg", "成功");
			map.put("status", "1");
			double fee = 0;
			List<PacketVO> list = new ArrayList<PacketVO>();
			PacketVO pv = null;
			for(RedPacket rp:packets){
				pv = new PacketVO();
				if(Double.parseDouble(rp.getPacket_money())>=100){
					fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee2());
				}else{
					fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee1());
				}
				String snagMoney = DbService.getSnagPacketMoney(rp.getId());
				double leftMoney = Double.parseDouble(rp.getPacket_money())-Double.parseDouble(snagMoney)-fee;
				if(new BigDecimal(leftMoney).setScale(2,BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ZERO)==1){
					pv.setStatus("0");
					pv.setSnagMoney(new BigDecimal(snagMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
					pv.setLeftMoney(new BigDecimal(leftMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				}else{
					pv.setStatus("1");
				}
				pv.setId(rp.getId());
				pv.setPacketMoney(rp.getPacket_money());
				pv.setCreateTime(DateUtil.DateToString(rp.getCreate_time(),DateStyle.MM_DD_HH_MM_SS_CN));
				list.add(pv);
			}
			map.put("records", list);
		}
		return map;
	}
	public static void main(String[] args) throws UnsupportedEncodingException{
		String packetId = "672842298052";
		String imgPath = "d:/test/QRCode/QRCode.png";
		String encoderContent = "http://wx.wezhuxue.com/wezhuxue-weixin/authCode?to=1&packetId="+packetId;
		TwoDimensionCode handler = new TwoDimensionCode();
		handler.encoderQRCode(encoderContent, imgPath, "png", 14);
		TwoImageCompose.createPicTwo2("d:/test/packet_qrcode.jpg",
				imgPath, "d:/test/Packet/"+packetId+"_packet.png",
				264, 1044);
	}
}
