package com.jdbc.jeevika.utility;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {

	public static Connection getDbConnection(String url,String userName,String password) throws SQLException {
		return DriverManager.getConnection(url,userName,password);
	}
	
	public static void c(ResultSet resultSet, Statement statement,Connection connection) {
		if(resultSet!=null) {
			try {
				connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(statement!=null) {
			try {
				connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection!=null) {
			try {
				connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
