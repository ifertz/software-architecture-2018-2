package edu.utfpr.cp.sa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection getConnection(){
		String dbURL = "jdbc:postgresql://localhost:5432/ArqSoft";
		String username = "postgres";
		String password = "2457";
		try {
			return DriverManager.getConnection(dbURL, username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
