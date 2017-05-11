package com.zhuxueup.web.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.zhuxueup.common.jdbc.DbService;
import com.zhuxueup.common.message.resp.Article;
import com.zhuxueup.common.message.resp.NewsMessage;
import com.zhuxueup.common.message.resp.TextMessage;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.DateStyle;
import com.zhuxueup.common.util.DateUtil;
import com.zhuxueup.common.util.IdGenerator;
import com.zhuxueup.common.weixin.AdvancedUtil;
import com.zhuxueup.common.weixin.MessageUtil;
import com.zhuxueup.web.model.AccessToken;
import com.zhuxueup.web.model.RedPacket;
import com.zhuxueup.web.pojo.MsgResult;
import com.zhuxueup.web.vo.JobCompanyVO;
import com.zhuxueup.web.vo.JobInfoVO;

/**
 * 核心服务类
 * 
 * @author chens
 */
public class CoreService {
	private static Logger log = Logger.getLogger(AdvancedUtil.class);
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	public static MsgResult processRequest(HttpServletRequest request) {
		MsgResult mr = new MsgResult();
		// xml格式的消息数据
		String respXml = null;
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			log.info("----------接收到的消息："+requestMap);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息内容
			String msgContent = requestMap.get("Content");
			log.info("--------消息内容："+msgContent);
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			// 事件推送
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				mr.setEventType(eventType);
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					textMessage.setContent("你如期而至，我大喜过望");
					// 将消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
					mr.setStr(respXml);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 暂不做处理
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建菜单时的key值对应
					String eventKey = requestMap.get("EventKey");
					mr.setEventKey(eventKey);
					// 根据key值判断用户点击的按钮
					if (eventKey.equals("myPackets")) {						
						List<RedPacket> packets = DbService.getUserPackets(fromUserName);
						log.info("---------创建的红包："+packets);
						List<String> list = new ArrayList<String>();
						List<Article> articles = new ArrayList<Article>();
						if(CTUtils.isEmpty(packets)){
							log.info("--------还没创建红包-------------");
							Article a = new Article();
							a.setPicUrl("http://123.56.207.57/img/activity/ns_avatar.png");
							a.setDescription("您还没创建年兽红包，点击创建吧~");
							a.setUrl("http://wx.wezhuxue.com/wezhuxue-weixin/authCode?to=0");
							a.setTitle("年兽红包来啦");
							articles.add(a);
							newsMessage.setArticleCount(1);
							newsMessage.setArticles(articles);
							respXml = MessageUtil.messageToXml(newsMessage);
							list.add(respXml);
						}else{
							log.info("----------------创建过红包了-------总共："+packets.size());
							double fee = 0;
							int num_10 = packets.size()/9;
							if((num_10*9) < packets.size()){
								num_10 += 1;
							}
							Article a = null;
							for(int i=0;i<num_10;i++){
								articles = new ArrayList<Article>();
								int j = i * 9;
								int for_num = 0;
								if(((i+1)*9) < packets.size()){
									for_num = (i+1)*9;
								}else{
									for_num = packets.size();
								}
								int n = 0;
								for(;j<for_num;j++){
									n++;
									a = new Article();
									if(Double.parseDouble(packets.get(j).getPacket_money())>=100){
										fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee2());
									}else{
										fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee1());
									}
									String snagMoney = DbService.getSnagPacketMoney(packets.get(j).getId());
									double leftMoney = Double.parseDouble(packets.get(j).getPacket_money())-Double.parseDouble(snagMoney)-fee;
									if(new BigDecimal(leftMoney).setScale(2,BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ZERO)==1){
										a.setPicUrl("http://123.56.207.57/img/activity/ns_hb.png");
										a.setDescription("您在"
												+ DateUtil.DateToString(
																packets.get(j).getCreate_time(),
																DateStyle.MM_DD_HH_MM_SS_CN)
												+ "创建的年兽红包"
												+ packets.get(j).getPacket_money()
												+ "元，已抢"
												+ new BigDecimal(snagMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString() 
												+ "元，还剩"
												+ new BigDecimal(leftMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString() 
												+ "元，点击链接继续分享让小伙伴们抢吧~");
									}else{
										a.setPicUrl("http://123.56.207.57/img/activity/ns_avatar.png");
										a.setDescription("您在"
												+ DateUtil.DateToString(
																packets.get(j).getCreate_time(),
																DateStyle.MM_DD_HH_MM_SS_CN)
												+ "创建的年兽红包"
												+ packets.get(j).getPacket_money()
												+ "元，已被抢光了，小伙伴们收到您慢慢的祝福，点击查看~");
									}
									a.setUrl("http://wx.wezhuxue.com/wezhuxue-weixin/authCode?to=1&packetId="+packets.get(j).getId());
									a.setTitle(packets.get(j).getPacket_money()+"元 年兽红包，时间："+ DateUtil.DateToString(packets.get(j).getCreate_time(),DateStyle.MM_DD_HH_MM_SS_CN));
									articles.add(a);
								}
//								if(for_num > 9){
//									newsMessage.setArticleCount(9);
//								}else{
//									newsMessage.setArticleCount(for_num);
//								}
								newsMessage.setArticleCount(n);
								newsMessage.setArticles(articles);
								respXml = MessageUtil.messageToXml(newsMessage);
								list.add(respXml);
							}
						}
						mr.setList(list);
					}else if("xbbj".equals(eventKey)){
						textMessage.setContent("那些行踪难觅的专业学霸都在这里，为您0距离分享专业课、考研、出国申请等学习经验，让你事半功倍。\n申请成为学霸：xueba@wezhuxue.com");
						// 将消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
						mr.setStr(respXml);
					}else if("xzApp".equals(eventKey)){
						textMessage.setContent("小伙伴们正在努力开发中，预计2月18日上线，敬请期待。");
						// 将消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
						mr.setStr(respXml);
					}else if("lxwm".equals(eventKey)){
						textMessage.setContent("您好，CEO一直在倾听来自你们的声音：ceo@wezhuxue.com\n紧急问题请直接回复公共号反馈，小主将会尽快为您作出答复。");
						// 将消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
						mr.setStr(respXml);
					}else if("cjwt".equals(eventKey)){
						textMessage.setContent("Q：年兽红包没分享出去找不到了怎么办？\n\n小年兽：不要急！和微信红包流程一样～没分享出去的红包会在两天内退回，您不会有任何资金损失。\n\n\n"
								+"Q：年兽红包没被抢完钱怎么办啊？\n\n小年兽：一样的，和微信红包流程一样～没被抢完的年兽红包会在两天内退回，您不会有任何资金损失。\n\n\n"
								+"Q：玩完年兽红包的钱在哪领？\n\n小年兽：如果玩完游戏并显示领到红包，就会收到服务消息，点开消息拆红包就可以了～\n\n\n"
								+"Q：新年了你有什么想说的？\n\n小年兽：用户大大恭喜发财，大吉大利，新春快乐～");
						// 将消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
						mr.setStr(respXml);
					}else if("fbjz".equals(eventKey)){
						textMessage.setContent("发布职位请严格按照如下格式：\n【职位名称】xxx\n【工资】xxx/天\n【结算方式】xxx\n【所在区域】xxx\n【地点】xxxx\n【招聘人数】x人\n【工作时间】xxxx\n【工作日期】xxxxx\n【性别要求】xxxx\n【兼职描述详情】xxxxx\n【公司名称】xxxx\n【兼职类型】xxx\n【联系人】xxx\n【联系方式】xxxxx");
						respXml = MessageUtil.messageToXml(textMessage);
						mr.setStr(respXml);
					}else if("bmxx".equals(eventKey)){
						
					}
				}
			}
			// 当用户发消息时
			else {
				//全部转给客服，没有过滤
//				CustomerMessage customerMessage = new CustomerMessage();
//				customerMessage.setToUserName(fromUserName);
//				customerMessage.setFromUserName(toUserName);
//				customerMessage.setCreateTime(new Date().getTime());
//				customerMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_CUSTOMER);
//				respXml = MessageUtil.messageToXml(customerMessage);
//				if(msgContent.contains("您好")||msgContent.contains("你好")||msgContent.contains("在吗")||msgContent.contains("有人吗")){
//					CustomerMessage customerMessage = new CustomerMessage();
//					customerMessage.setToUserName(fromUserName);
//					customerMessage.setFromUserName(toUserName);
//					customerMessage.setCreateTime(new Date().getTime());
//					customerMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_CUSTOMER);
//					respXml = MessageUtil.messageToXml(customerMessage);
//				}else{
//					textMessage.setContent("稍等，我们客服会及时联系您的！");
//					respXml = MessageUtil.messageToXml(textMessage);
//				}
				log.info("-----------用户发送消息-----------");
				if(msgContent.contains("【职位名称】")||msgContent.contains("发布职位")||msgContent.contains("职位发布")||msgContent.contains("发工作")||msgContent.contains("发兼职")||msgContent.contains("发布兼职")||msgContent.contains("兼职发布")){
					log.info("-------------用户打算发布职位-----------");
					String[] str = msgContent.split("【");
					if(str.length!=15){
						textMessage.setContent("发布职位请严格按照如下格式：\n【职位名称】xxx\n【工资】xxx/天\n【结算方式】xxx\n【所在区域】xxx\n【地点】xxxx\n【招聘人数】x人\n【工作时间】xxxx\n【工作日期】xxxxx\n【性别要求】xxxx\n【兼职描述详情】xxxxx\n【公司名称】xxxx\n【兼职类型】xxx\n【联系人】xxx\n【联系方式】xxxxx");
					}else{
						JobCompanyVO jc = new JobCompanyVO();
						long companyId = Long.parseLong(IdGenerator.nextId());
						jc.setId(companyId);
						jc.setName(str[11].split("】")[1]);
						jc.setLogo("http://jz.wezhuxue.com/admin/images/bluiebox.jpg");
						jc.setLocation("北京");
						if(!DbService.insertEntity(jc)){
							textMessage.setContent("兼职发布错误，请联系客服！");
							respXml = MessageUtil.messageToXml(textMessage);
							mr.setStr(respXml);
							return mr;
						}
						String classifyId = DbService.getClassifyByName(str[12].split("】")[1]);
						if(CTUtils.isEmpty(classifyId)){
							classifyId = "23";
						}
						JobInfoVO job = new JobInfoVO();
						String jobId = IdGenerator.nextId();
						job.setId(Long.parseLong(jobId));
						job.setCompanyId(companyId);
						job.setClassifyId(Integer.parseInt(classifyId));
						job.setCityId("110100");
						job.setCity("北京");
						job.setName(str[1].split("】")[1].replace("\n", ""));
						job.setSalary(str[2].split("】")[1].replace("\n", ""));
						job.setPayType(str[3].split("】")[1].replace("\n", ""));
						job.setArea(str[4].split("】")[1].replace("\n", ""));
						job.setWorkPlace(str[5].split("】")[1].replace("\n", ""));
						job.setRecruitment(str[6].split("】")[1].replace("\n", ""));
						job.setWorkTime(str[7].split("】")[1].replace("\n", ""));
						job.setWorkDate(str[8].split("】")[1].replace("\n", ""));
						job.setGender(str[9].split("】")[1].replace("\n", ""));
						job.setJobDescription(str[10].split("】")[1]);
						job.setLinkman(str[13].split("】")[1].replace("\n", ""));
						job.setContactWay(str[14].split("】")[1].replace("\n", ""));
						job.setPublishUser(toUserName);
						if(!DbService.insertEntity(job)){
							textMessage.setContent("兼职发布错误，请联系客服！");
							respXml = MessageUtil.messageToXml(textMessage);
							mr.setStr(respXml);
							return mr;
						}
						AccessToken token = DbService.getToken();
						//String json = "{\"action\":\"long2short\",\"long_url\":\"http://testweixin.wezhuxue.com/wezhuxue-weixin/views/zxt_stu/jz/jzTemplet.html?jobId="+jobId+"\"}";
						String json = "{\"action\":\"long2short\",\"long_url\":\""+ConstantsUtil.getConstantsUtil().getJz_templet_url().replace("JOBID", jobId)+"\"}";
						String shortUrl = AdvancedUtil.getShortUrl(token.getToken(), json);
						if(CTUtils.isEmpty(shortUrl)){
							textMessage.setContent("兼职发布错误，请联系客服！");
							respXml = MessageUtil.messageToXml(textMessage);
							mr.setStr(respXml);
							return mr;
						}
						textMessage.setContent("兼职预览请点击"+shortUrl+"，确认发布回复Y。");
					}
				}else if(msgContent.equals("Y")||msgContent.equals("y")){
					JobInfoVO job = new JobInfoVO();
					job.setJobStatus("1");
					job.setInfoStatus("1");
					job.setPublishUser(toUserName);
					if(!DbService.updateJobInfo(job)){
						textMessage.setContent("兼职发布错误，请联系客服！");
						respXml = MessageUtil.messageToXml(textMessage);
						mr.setStr(respXml);
						return mr;
					}
					AccessToken token = DbService.getToken();
					String jobId = DbService.getLastJobByUser(toUserName);
					if(CTUtils.isEmpty(jobId)){
						textMessage.setContent("恭喜您，职位发布成功！");
						respXml = MessageUtil.messageToXml(textMessage);
						mr.setStr(respXml);
						return mr;
					}
					//String json = "{\"action\":\"long2short\",\"long_url\":\"http://testweixin.wezhuxue.com//wezhuxue-weixin/jzDetailAuthCode?jobId="+jobId+"\"}";
					String json = "{\"action\":\"long2short\",\"long_url\":\""+ConstantsUtil.getConstantsUtil().getJz_share_url().replace("JOBID", jobId)+"\"}";
					String shortUrl = AdvancedUtil.getShortUrl(token.getToken(), json);
					if(CTUtils.isEmpty(shortUrl)){
						textMessage.setContent("兼职发布错误，请联系客服！");
						respXml = MessageUtil.messageToXml(textMessage);
						mr.setStr(respXml);
						return mr;
					}
					textMessage.setContent("兼职发布成功！快去分享吧~"+shortUrl+"");
				}else{
					textMessage.setContent("如有问题，请加客服微信：Anjoe-W！");
					respXml = MessageUtil.messageToXml(textMessage);
				}
				respXml = MessageUtil.messageToXml(textMessage);
				mr.setStr(respXml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("----------返回的消息："+mr.toString());
		return mr;
	}
}
