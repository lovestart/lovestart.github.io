package com.zhuxueup.web.task;

import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.zhuxueup.common.jdbc.DbService;
import com.zhuxueup.common.util.CTUtils;
import com.zhuxueup.common.util.ConstantsUtil;
import com.zhuxueup.common.util.IdGenerator;
import com.zhuxueup.common.weixin.CommonUtil;
import com.zhuxueup.web.model.AccessToken;
import com.zhuxueup.web.pojo.Token;

/**
 * 刷新token任务
 * 
 */
public class TokenTask extends TimerTask {

	private Logger log = Logger.getLogger(TokenTask.class);
	private static boolean isRunning = false;
	private ServletContext context = null;

	public TokenTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (!isRunning) {
			isRunning = true;
			context.log("开始执行刷新token任务");
			// 添加自定义的详细任务
			executeTask();
			// 指定任务执行结束
			isRunning = false;
			context.log("刷新token任务执行结束");
		} else {
			context.log("上一次任务执行还未结束");
		}

	}

	/**
	 * 执行任务
	 */
	public void executeTask() {
		Token token = CommonUtil.getToken(ConstantsUtil.getConstantsUtil().getAppId(), ConstantsUtil.getConstantsUtil().getAppSecret());
		AccessToken aToken = DbService.getToken();
		if(CTUtils.isEmpty(aToken)){
			log.info("数据库还没有token，插入一条！token："+token.getAccessToken());
			aToken = new AccessToken();
			aToken.setId(IdGenerator.nextId());
			aToken.setToken(token.getAccessToken());
			DbService.insertEntity(aToken);
		}else{
			log.info("数据库已有token，更新数据！token："+token.getAccessToken());
			aToken.setToken(token.getAccessToken());
			DbService.updateToken(aToken);
		}
	}
}