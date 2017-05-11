package com.zhuxueup.web.listener;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zhuxueup.common.util.DateUtil;
import com.zhuxueup.web.task.DrawBackTask;
import com.zhuxueup.web.task.TokenTask;

/**
 * 定时任务，ContextListener
 * 
 */
public class TaskContextListener implements ServletContextListener {

	private java.util.Timer timer = null;
	//时间间隔(一天)  
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

	/**
	 * 这个方法在Web应用服务做好接受请求的时候被调用。
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		timer = new java.util.Timer(true);
		event.getServletContext().log("定时器已启动");
		//刷新token任务
		//每隔115分钟
		timer.schedule(new TokenTask(event.getServletContext()), 0, 115 * 60 * 1000);
		/*//退钱任务
		Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.HOUR_OF_DAY, 9); //凌晨9点  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        Date date = calendar.getTime(); //第一次执行定时任务的时间  
        //如果第一次执行定时任务的时间 小于当前的时间  
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。  
        if (date.before(new Date())) {  
            date = DateUtil.addDay(date, 1);  
        }
        //每天早上9点执行退钱任务
		timer.schedule(new DrawBackTask(event.getServletContext()), date, PERIOD_DAY);*/
		event.getServletContext().log("已经添加任务调度表");
	}

	/**
	 * 这个方法在Web应用服务被移除，没有能力再接受请求的时候被调用。
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		event.getServletContext().log("定时器销毁");
	}

}