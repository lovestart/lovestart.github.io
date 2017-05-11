package com.zhuxueup.common.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.zhuxueup.web.model.AccessToken;
import com.zhuxueup.web.model.PacketSnagRecord;
import com.zhuxueup.web.model.PayRecord;
import com.zhuxueup.web.model.RedPacket;
import com.zhuxueup.web.model.UserInfo;
import com.zhuxueup.web.model.UserToken;
import com.zhuxueup.web.vo.JobCompanyVO;
import com.zhuxueup.web.vo.JobInfoVO;

public class DbService {

	private static Logger log = Logger.getLogger(DbService.class);
	private static String insert_packet_sql = "INSERT INTO red_packet (id,open_id,wx_name,packet_name,packet_money,img_url)"
			+ " VALUES (?,?,?,?,?,?) ";
	private static String insert_record_sql = "INSERT INTO packet_snag_record (packet_id,open_id,wx_name,wx_avatar,snag_money,couplet)"
			+ " VALUES (?,?,?,?,?,?) ";
	private static String insert_user_sql = "INSERT INTO user_info (open_id,nick_name,avatar,sex,province,city,country,unionid)"
			+ " VALUES (?,?,?,?,?,?,?,?) ";
	private static String insert_pay_sql = "INSERT INTO pay_record (id,order_num,open_id,amount,status,type)"
			+ " VALUES (?,?,?,?,?,?) ";
	private static String insert_token_sql = "INSERT INTO access_token (id,token)"
			+ " VALUES (?,?) ";
	private static String insert_user_token_sql = "INSERT INTO user_token (token,open_id,packet_id)"
			+ " VALUES (?,?,?) ";
	private static String get_packet_sql = "select * from red_packet where id = ?";
	private static String get_packet_noexpire_sql = "select * from red_packet where id = ? and TIMESTAMPDIFF(HOUR,create_time,DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')) < 36";
	private static String get_packet_snager_sql = "select snag_money,wx_name,wx_avatar from packet_snag_record where packet_id = ? order by CAST(snag_money as decimal(8,2)) desc,create_time asc";
	private static String get_one_snager_sql = "select * from packet_snag_record where packet_id = ? and open_id = ?";
	private static String get_user_sql = "select * from user_info where open_id = ?";
	private static String get_snag_money_sql = "select IFNULL(sum(snag_money),0) from packet_snag_record where packet_id = ?";
	private static String get_token_sql = "select id,token from access_token";
	private static String get_draw_back_packet_sql = "select * from red_packet where back_status='0' and TIMESTAMPDIFF(HOUR,create_time,DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')) >= 36";
	private static String get_pay_sql = "select * from pay_record where order_num = ?";
	private static String get_user_token_sql = "select * from user_token where token = ?";
	private static String get_user_packet_sql = "select * from red_packet where open_id = ? order by create_time desc";
	private static String update_token_sql = "update access_token set token = ?";
	private static String update_pay_sql = "update pay_record set status = ? where order_num = ?";
	private static String update_user_token_sql = "update user_token set status = ? where id = ?";
	private static String update_packet_sql = "update red_packet set back_status = ? where id = ?";
	private static String delete_snag_record_sql = "delete from packet_snag_record where packet_id = ? and open_id = ?";
	
	private static String insert_job_sql = "INSERT INTO job_info (id,company_id,classify_id,name,salary,pay_type,city_id,city,area,work_place,recruitment,gender,work_time,work_date,job_description,linkman,contact_way,public_publish_user)"
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	private static String get_classify_sql = "select id from job_classify where name like ?";
	private static String insert_company_sql = "INSERT INTO job_company (id,name,logo,location)"
			+ " VALUES (?,?,?,?) ";
	private static String update_job_sql = "update job_info set job_status = ?,info_status = ? where public_publish_user = ?";
	private static String get_last_job_sql = "select id from job_info where public_publish_user = ? order by create_time desc limit 1";
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public static boolean insertEntity(Object obj) {
		boolean flag = false;
		DbPool dbPool = DbPool.getInstance();
		DruidPooledConnection conn = null;
		PreparedStatement pstate = null;
		try {
			conn = dbPool.getConnection();
			conn.setAutoCommit(false);
			log.info("将数据插入到数据库");
			if (obj instanceof RedPacket) {
				RedPacket rp = (RedPacket) obj;
				pstate = conn.prepareStatement(insert_packet_sql);
				pstate.setString(1, rp.getId());
				pstate.setString(2, rp.getOpen_id());
				pstate.setString(3, rp.getWx_name());
				pstate.setString(4, rp.getPacket_name());
				pstate.setString(5, rp.getPacket_money());
				pstate.setString(6, rp.getImg_url());
			}else if (obj instanceof PacketSnagRecord) {
				PacketSnagRecord psr = (PacketSnagRecord) obj;
				pstate = conn.prepareStatement(insert_record_sql);
				pstate.setString(1, psr.getPacket_id());
				pstate.setString(2, psr.getOpen_id());
				pstate.setString(3, psr.getWx_name());
				pstate.setString(4, psr.getWx_avatar());
				pstate.setString(5, psr.getSnag_money());
				pstate.setString(6, psr.getCouplet());
			}else if (obj instanceof UserInfo) {
				UserInfo user = (UserInfo) obj;
				pstate = conn.prepareStatement(insert_user_sql);
				pstate.setString(1, user.getOpen_id());
				pstate.setString(2, user.getNick_name());
				pstate.setString(3, user.getAvatar());
				pstate.setString(4, user.getSex());
				pstate.setString(5, user.getProvince());
				pstate.setString(6, user.getCity());
				pstate.setString(7, user.getCountry());
				pstate.setString(8, user.getUnionid());
			}else if(obj instanceof PayRecord){
				PayRecord pay = (PayRecord) obj;
				pstate = conn.prepareStatement(insert_pay_sql);
				pstate.setString(1, pay.getId());
				pstate.setString(2, pay.getOrder_num());
				pstate.setString(3, pay.getOpen_id());
				pstate.setString(4, pay.getAmount());
				pstate.setString(5, pay.getStatus());
				pstate.setString(6, pay.getType());
			}else if(obj instanceof AccessToken){
				AccessToken at = (AccessToken) obj;
				pstate = conn.prepareStatement(insert_token_sql);
				pstate.setString(1, at.getId());
				pstate.setString(2, at.getToken());
			}else if(obj instanceof UserToken){
				UserToken ut = (UserToken) obj;
				pstate = conn.prepareStatement(insert_user_token_sql);
				pstate.setString(1, ut.getToken());
				pstate.setString(2, ut.getOpen_id());
				pstate.setString(3, ut.getPacket_id());
			}
			else if(obj instanceof JobInfoVO){
				JobInfoVO ji = (JobInfoVO) obj;
				pstate = conn.prepareStatement(insert_job_sql);
				pstate.setLong(1, ji.getId());
				pstate.setLong(2, ji.getCompanyId());
				pstate.setInt(3, ji.getClassifyId());
				pstate.setString(4, ji.getName());
				pstate.setString(5, ji.getSalary());
				pstate.setString(6, ji.getPayType());
				pstate.setString(7, ji.getCityId());
				pstate.setString(8, ji.getCity());
				pstate.setString(9, ji.getArea());
				pstate.setString(10, ji.getWorkPlace());
				pstate.setString(11, ji.getRecruitment());
				pstate.setString(12, ji.getGender());
				pstate.setString(13, ji.getWorkTime());
				pstate.setString(14, ji.getWorkDate());
				pstate.setString(15, ji.getJobDescription());
				pstate.setString(16, ji.getLinkman());
				pstate.setString(17, ji.getContactWay());
				pstate.setString(18, ji.getPublishUser());
			}
			else if(obj instanceof JobCompanyVO){
				JobCompanyVO jc = (JobCompanyVO) obj;
				pstate = conn.prepareStatement(insert_company_sql);
				pstate.setLong(1, jc.getId());
				pstate.setString(2, jc.getName());
				pstate.setString(3, jc.getLogo());
				pstate.setString(4, jc.getLocation());
			}
			pstate.executeUpdate();
			conn.commit();
			log.info("好了吗");
			flag = true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (null != pstate) {
				try {
					pstate.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	/**
     * 根据id获取单个红包信息
     * @param id
     * @return
     */
    public static RedPacket getRedPacketById(String id){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
    	try {
    		conn = dbp.getConnection();
    		pstate = conn.prepareStatement(get_packet_sql);
    		pstate.setString(1, id);
    		rs = pstate.executeQuery();
    		RedPacket obj = (RedPacket) DbPool.convertTObject(rs, RedPacket.class);
    		return obj;
		} catch (SQLException e) {
			log.error("查询数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return null;
    }
    /**
     * 根据id获取没过期的红包
     * @param id
     * @return
     */
    public static RedPacket getRedPacketByIdNotExpire(String id){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
    	try {
    		conn = dbp.getConnection();
    		pstate = conn.prepareStatement(get_packet_noexpire_sql);
    		pstate.setString(1, id);
    		rs = pstate.executeQuery();
    		RedPacket obj = (RedPacket) DbPool.convertTObject(rs, RedPacket.class);
    		return obj;
		} catch (SQLException e) {
			log.error("查询数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return null;
    }
    /**
     * 根据openId获取用户信息
     * @param id
     * @return
     */
    public static UserInfo getUserById(String openId){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
    	try {
    		conn = dbp.getConnection();
    		pstate = conn.prepareStatement(get_user_sql);
    		pstate.setString(1, openId);
    		rs = pstate.executeQuery();
    		UserInfo obj = (UserInfo) DbPool.convertTObject(rs, UserInfo.class);
    		return obj;
		} catch (SQLException e) {
			log.error("查询数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return null;
    }
    /**
     * 查询是否抢过红包
     * @param id
     * @return
     */
    public static PacketSnagRecord getPacketByIdAndUser(String packetId, String openId){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
    	try {
    		conn = dbp.getConnection();
    		pstate = conn.prepareStatement(get_one_snager_sql);
    		pstate.setString(1, packetId);
    		pstate.setString(2, openId);
    		rs = pstate.executeQuery();
    		PacketSnagRecord obj = (PacketSnagRecord) DbPool.convertTObject(rs, PacketSnagRecord.class);
    		return obj;
		} catch (SQLException e) {
			log.error("查询数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return null;
    }
    /**
     * 查询返回红包抢劫的list集合
     * 多条件可以拼接sql
     * @param sql
     */
    public static List<PacketSnagRecord> getPacketSnagers(String packetId){
    	DbPool dbp=DbPool.getInstance();
    	DruidPooledConnection conn=null;
    	PreparedStatement pstate=null;
    	ResultSet rs=null;
    	try {
    		conn=dbp.getConnection();
    		pstate=conn.prepareStatement(get_packet_snager_sql);
    		pstate.setString(1, packetId);
    		rs=pstate.executeQuery();
    		List<PacketSnagRecord> list = DbPool.convertToList(rs, PacketSnagRecord.class);
    		if(null!=list && list.size()>0){
    			System.out.println("list 抢劫 条数"+list.size());
    			return list;
    		}
		} catch (SQLException e) {
			log.error("数据库操作失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
		return null;
    }
    /**
     * 查询红包已经抢了多少钱了
     * @param sql
     */
    public static String getSnagPacketMoney(String packetId){
    	DbPool dbp=DbPool.getInstance();
    	DruidPooledConnection conn=null;
    	PreparedStatement pstate=null;
    	ResultSet rs=null;
    	String money = "";
    	try {
    		conn=dbp.getConnection();
    		pstate=conn.prepareStatement(get_snag_money_sql);
    		pstate.setString(1, packetId);
    		rs = pstate.executeQuery();
    		while(rs.next()){
    	         money = rs.getString(1);
    	    }
    		return money;
		} catch (SQLException e) {
			log.error("数据库操作失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
		return "0";
    }
    /**
     * 获取token
     * @return
     */
    public static AccessToken getToken(){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
    	try {
    		conn = dbp.getConnection();
    		pstate = conn.prepareStatement(get_token_sql);
    		rs = pstate.executeQuery();
    		AccessToken obj = (AccessToken) DbPool.convertTObject(rs, AccessToken.class);
    		return obj;
		} catch (SQLException e) {
			log.error("查询数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return null;
    }
    /**
     * 更新token
     * @return
     */
    public static void updateToken(AccessToken token){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	try {
    		conn = dbp.getConnection();
    		conn.setAutoCommit(false);
    		pstate = conn.prepareStatement(update_token_sql);
    		pstate.setString(1, token.getToken());
    		pstate.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			log.error("更新数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    }
    /**
     * 查询应该退钱的红包list集合
     * @param sql
     */
    public static List<RedPacket> getRedPackets(){
    	DbPool dbp=DbPool.getInstance();
    	DruidPooledConnection conn=null;
    	PreparedStatement pstate=null;
    	ResultSet rs=null;
    	try {
    		conn=dbp.getConnection();
    		pstate=conn.prepareStatement(get_draw_back_packet_sql);
    		rs=pstate.executeQuery();
    		List<RedPacket> list = DbPool.convertToList(rs, RedPacket.class);
    		if(null!=list && list.size()>0){
    			System.out.println("list 红包 条数"+list.size());
    			return list;
    		}
		} catch (SQLException e) {
			log.error("数据库操作失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
		return null;
    }
    /**
     * 获取token
     * @return
     */
    public static PayRecord getPayRecord(String orderNum){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
    	try {
    		conn = dbp.getConnection();
    		pstate = conn.prepareStatement(get_pay_sql);
    		pstate.setString(1, orderNum);
    		rs = pstate.executeQuery();
    		PayRecord obj = (PayRecord) DbPool.convertTObject(rs, PayRecord.class);
    		return obj;
		} catch (SQLException e) {
			log.error("查询数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return null;
    }
    /**
     * 更新token
     * @return
     */
    public static boolean updatePayRecord(PayRecord pr){
    	boolean flag = false;
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	try {
    		conn = dbp.getConnection();
    		conn.setAutoCommit(false);
    		pstate = conn.prepareStatement(update_pay_sql);
    		pstate.setString(1, pr.getStatus());
    		pstate.setString(2, pr.getOrder_num());
    		pstate.executeUpdate();
			conn.commit();
			flag = true;
		} catch (SQLException e) {
			log.error("更新数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return flag;
    }
    /**
     * 获取用户token
     * @return
     */
    public static UserToken getUserToken(String token){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
    	try {
    		conn = dbp.getConnection();
    		pstate = conn.prepareStatement(get_user_token_sql);
    		pstate.setString(1, token);
    		rs = pstate.executeQuery();
    		UserToken obj = (UserToken) DbPool.convertTObject(rs, UserToken.class);
    		return obj;
		} catch (SQLException e) {
			log.error("查询数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return null;
    }
    /**
     * 更新用户token
     * @return
     */
    public static boolean updateUserToken(UserToken pr){
    	boolean flag = false;
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	try {
    		conn = dbp.getConnection();
    		conn.setAutoCommit(false);
    		pstate = conn.prepareStatement(update_user_token_sql);
    		pstate.setString(1, pr.getStatus());
    		pstate.setInt(2, pr.getId());
    		pstate.executeUpdate();
			conn.commit();
			flag = true;
		} catch (SQLException e) {
			log.error("更新数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return flag;
    }
    /**
     * 更新红包
     * @return
     */
    public static boolean updateRedPacket(RedPacket pr){
    	boolean flag = false;
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	try {
    		conn = dbp.getConnection();
    		conn.setAutoCommit(false);
    		pstate = conn.prepareStatement(update_packet_sql);
    		pstate.setString(1, pr.getBack_status());
    		pstate.setString(2, pr.getId());
    		pstate.executeUpdate();
			conn.commit();
			flag = true;
		} catch (SQLException e) {
			log.error("更新数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return flag;
    }
    /**
     * 删除抢红包记录
     * @return
     */
    public static boolean deleteSnagRecord(PacketSnagRecord psr){
    	boolean flag = false;
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	try {
    		conn = dbp.getConnection();
    		conn.setAutoCommit(false);
    		pstate = conn.prepareStatement(delete_snag_record_sql);
    		pstate.setString(1, psr.getPacket_id());
    		pstate.setString(2, psr.getOpen_id());
    		pstate.executeUpdate();
			conn.commit();
			flag = true;
		} catch (SQLException e) {
			log.error("更新数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return flag;
    }
    /**
     * 查询返回用户创建的红包集合
     * 多条件可以拼接sql
     * @param sql
     */
    public static List<RedPacket> getUserPackets(String openId){
    	DbPool dbp=DbPool.getInstance();
    	DruidPooledConnection conn=null;
    	PreparedStatement pstate=null;
    	ResultSet rs=null;
    	try {
    		conn=dbp.getConnection();
    		pstate=conn.prepareStatement(get_user_packet_sql);
    		pstate.setString(1, openId);
    		rs=pstate.executeQuery();
    		List<RedPacket> list = DbPool.convertToList(rs, RedPacket.class);
    		if(null!=list && list.size()>0){
    			System.out.println("list 抢劫 条数"+list.size());
    			return list;
    		}
		} catch (SQLException e) {
			log.error("数据库操作失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
		return null;
    }
    /**
     * 根据类型获取id
     * @param name
     * @return
     */
    public static String getClassifyByName(String name){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
    	String classifyId = "";
    	try {
    		conn = dbp.getConnection();
    		pstate = conn.prepareStatement(get_classify_sql);
    		pstate.setString(1, "%"+name+"%");
    		rs = pstate.executeQuery();
    		while(rs.next()){
    			classifyId = rs.getString(1);
    	    }
		} catch (SQLException e) {
			log.error("查询数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return classifyId;
    }
    /**
     * 确认发布职位
     * @return
     */
    public static boolean updateJobInfo(JobInfoVO job){
    	boolean flag = false;
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	try {
    		conn = dbp.getConnection();
    		conn.setAutoCommit(false);
    		pstate = conn.prepareStatement(update_job_sql);
    		pstate.setString(1, job.getJobStatus());
    		pstate.setString(2, job.getInfoStatus());
    		pstate.setString(3, job.getPublishUser());
    		pstate.executeUpdate();
			conn.commit();
			flag = true;
		} catch (SQLException e) {
			log.error("更新数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return flag;
    }
    /**
     * 获取微信发布最近的职位
     * @param name
     * @return
     */
    public static String getLastJobByUser(String name){
    	DbPool dbp = DbPool.getInstance();
    	DruidPooledConnection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
    	String jobId = "";
    	try {
    		conn = dbp.getConnection();
    		pstate = conn.prepareStatement(get_last_job_sql);
    		pstate.setString(1, name);
    		rs = pstate.executeQuery();
    		while(rs.next()){
    			jobId = rs.getString(1);
    	    }
		} catch (SQLException e) {
			log.error("查询数据库失败"+e.getMessage(),e);
			e.printStackTrace();
		}finally{
			if(null!=rs){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=pstate){try {pstate.close();} catch (SQLException e) {e.printStackTrace();}}
			if(null!=conn){try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
    	return jobId;
    }
}
