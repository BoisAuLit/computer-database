package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionManager {

	private static final String USERNAME = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	private static Connection connection;

	private ConnectionManager() throws SQLException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
				+ "computer-database-db?useUnicode=true&useJDBCCompliantTimezoneShift=true"
				+ "&useLegacyDatetimeCode=false&serverTimezone=UTC", USERNAME, PASSWORD);
	}

	private static class LazyHolder {
		static ConnectionManager INSTANCE = null;
		static {
			try {
				INSTANCE = new ConnectionManager();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
					| SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ConnectionManager getInstance() {
		return LazyHolder.INSTANCE;
	}

	public PreparedStatement getPreparedStatement(String query) throws SQLException {
		return connection.prepareStatement(query);
	}

	public void close() throws SQLException {
		connection.close();
	}
}
