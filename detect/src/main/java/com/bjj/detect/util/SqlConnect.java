package com.bjj.detect.util;

import com.bjj.detect.sqldto.WordItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: sql 数据库配置
 * @author: zhangyan
 * @date: 2024/7/7 11:21
**/
@Configuration
@ConfigurationProperties(prefix = "sqlmeta.connect-info")
@Getter
@Setter
public class SqlConnect {

	private String url;
	private String username;
	private String password;
	private String driver;

	/**
	 * @param :
	 * @return: Connection
	 * @description:
	 * @author: zhangyan
	 * @date: 2024/7/7 11:24
	**/
	public Connection connect(){
		try{
			Class.forName(driver);
			Connection conn= DriverManager.getConnection(
					url,
					username,
					password);
			return conn;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param conn:
	 * @param ps:
	 * @param rs:
	 * @return:
	 * @description: 释放资源
	 * @author: zhangyan
	 * @date: 2024/7/7 11:20
	**/
	public void release(Connection conn, PreparedStatement ps, ResultSet rs){
		try{
			if (conn != null){
				conn.close();
			}
			if (ps != null){
				ps.close();
			}
			if (rs != null){
				rs.close();
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "SqlConnect{" +
				"url='" + url + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", driver='" + driver + '\'' +
				'}';
	}
}
