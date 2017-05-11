package com.zhuxueup.common.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSON;

public class DbPool {
	private static Logger log = Logger.getLogger(DbPool.class);

	private static DbPool databasePool = null;
	private static DruidDataSource dds = null;
	static {
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("db.properties").getPath();
		if(path.indexOf("%20")!=-1){
			path = path.replace("%20", " "); //引号中有一个半角的空格
		}
		try {
			properties.load(new FileInputStream(path));
			dds = (DruidDataSource) DruidDataSourceFactory
					.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DbPool() {
	}

	public static synchronized DbPool getInstance() {
		if (null == databasePool) {
			databasePool = new DbPool();
		}
		return databasePool;
	}

	public DruidPooledConnection getConnection() throws SQLException {
		return dds.getConnection();
	}

	public static Properties loadPropertyFile(String fullFile) {
		String webRootPath = null;
		if (null == fullFile || fullFile.equals(""))
			throw new IllegalArgumentException(
					"Properties file path can not be null : " + fullFile);
		webRootPath = DbPool.class.getClassLoader().getResource("").getPath();
		webRootPath = new File(webRootPath).getParent();
		InputStream inputStream = null;
		Properties p = null;
		try {
			inputStream = new FileInputStream(new File(webRootPath
					+ File.separator + fullFile));
			p = new Properties();
			p.load(inputStream);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Properties file not found: "
					+ fullFile);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Properties file can not be loading: " + fullFile);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	/**
	 * 将resultSet的结果集转换成list对象
	 * 
	 * @param <T>
	 * @param set
	 * @param t
	 * @return
	 */
	public static <T> List<T> convertToList(ResultSet rs, Class<T> t) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				Map<String, Object> rowData = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);
			}
			List<T> resultList = JSON.parseArray(JSON.toJSONString(list), t);
			return resultList;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将结果集转换成对象
	 * 
	 * @param rs
	 * @param t
	 * @return
	 */
	public static <T> Object convertTObject(ResultSet rs, Class<T> t) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		try {
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				if(!flag){
					flag = true;
				}
				for (int i = 1; i <= columnCount; i++) {
					map.put(md.getColumnName(i), rs.getObject(i));
				}
			}
			if(flag){
				return JSON.parseObject(JSON.toJSONString(map), t);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
}
