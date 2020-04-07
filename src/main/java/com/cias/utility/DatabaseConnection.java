package com.cias.utility;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {

	public static Connection getConnection() {
		Connection conn = null;

		String url = "jdbc:mysql://10.43.1.107:3306/ciasdb";
		String driver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, "ciasdb", "Cedge@123");

		} catch (Exception e) {
	  }
		return conn;
	}

}
