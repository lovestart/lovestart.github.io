package com.zhuxueup.web.task;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.zhuxueup.common.jdbc.DbService;
import com.zhuxueup.common.pay.CommonUtil;
import com.zhuxueup.common.pay.SignUtil;
import com.zhuxueup.common.pay.WXConstants;
import com.zhuxueup.common.pay.WXPayUtil;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.IdGenerator;
import com.zhuxueup.web.model.RedPacket;

/**
 * 退钱任务
 * 
 */
public class DrawBackTask extends TimerTask {

	private Logger log = Logger.getLogger(DrawBackTask.class);
	private static boolean drawIsRunning = false;
	private ServletContext context = null;

	public DrawBackTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (!drawIsRunning) {
			drawIsRunning = true;
			context.log("开始执行退钱任务");
			// 添加自定义的详细任务
			executeTask();
			// 指定任务执行结束
			drawIsRunning = false;
			context.log("退钱任务执行结束");
		} else {
			context.log("上一次任务执行还未结束");
		}

	}

	/**
	 * 执行任务
	 */
	public void executeTask() {
		List<RedPacket> list = DbService.getRedPackets();
		if(CTUtils.isNotEmpty(list)){
			for(RedPacket rp : list){
				double fee = 0;
				if(Double.parseDouble(rp.getPacket_money())>=100){
					fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee2());
				}else{
					fee = Double.parseDouble(ConstantsUtil.getConstantsUtil().getProcedure_fee1());
				}
				String money = DbService.getSnagPacketMoney(rp.getId());
				double returnMoney = Double.parseDouble(rp.getPacket_money())-Double.parseDouble(money)-fee;
				if((new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(rp.getPacket_money()).setScale(2, BigDecimal.ROUND_HALF_UP))==-1) && returnMoney>=1){
					log.info("---------将剩余钱退回---------");
					SortedMap<String, String> sm = new TreeMap<String, String>();
					String orderNum = IdGenerator.wxCouponOrder();
					if(orderNum.length() > 18){
						orderNum = orderNum.substring(0, 18);
					}
					String nonceStr = UUID.randomUUID().toString().substring(0, 32);
			        sm.put("nonce_str", nonceStr);
			        sm.put("mch_billno", WXConstants.WX_MCH_ID+orderNum);
			        sm.put("mch_id", WXConstants.WX_MCH_ID);
			        sm.put("wxappid", WXConstants.WX_APP_ID);
			        sm.put("send_name", "人人助学");
			        sm.put("re_openid", rp.getOpen_id());
			        sm.put("total_amount", new BigDecimal(returnMoney*100).setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			        sm.put("total_num", "1");
			        sm.put("wishing", "感谢您参加年兽红包活动，祝您鸡年大吉！");
			        sm.put("act_name", "年兽红包剩余退回");
			        sm.put("client_ip", "123.56.235.221");
			        sm.put("remark", "多玩多抢，鸡年大吉！");
			        String paySign = SignUtil.createSign("utf-8", sm);
			        log.info("普通红包*****微信签名***************************="+paySign);
			        sm.put("sign", paySign);
			        String xml = CommonUtil.ArrayToXml(sm);
			        log.info("请求参数*****************************="+xml);
			        Map<String,String> preMap = WXPayUtil.publicRedPacket(xml);
			        log.info("普通红包返回参数*****************************preMap="+preMap);
			        if(!"SUCCESS".equals(preMap.get("return_code"))){
			        	
					}else if(!"SUCCESS".equals(preMap.get("result_code"))){
						
					}else{
						rp.setBack_status("1");
						DbService.updateRedPacket(rp);
					}
				}
			}
		}else{
			log.info("----------没有需要退回的红包--------真实6啊");
		}
	}
}