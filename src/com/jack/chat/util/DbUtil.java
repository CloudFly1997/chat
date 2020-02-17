package com.jack.chat.util;

import java.sql.*;

public class DbUtil {
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://101.37.76.215:3306/chat?useSSL=false&useUnicode=true&characterEncoding=utf-8", "root", "281808");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void close(Connection conn, ResultSet rs, PreparedStatement ps) {
		try {
			if(rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
