package model;

import java.sql.*;

import javafx.application.Platform;

public class Database {
	
	private static Database INSTANCE = new Database();
	
	private Connection conn;
	
	private String database = "jdbc:mysql://localhost/myPlayer";
	private String username = "music";
	private String password = "1337";
	
	private Database() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection(database, username, password);
		} catch(Exception e) {
			e.printStackTrace();
			// STOP WATCHSERVICE
			Platform.exit();
		}
		
	}
	
	public static Database getInstance() {
		return INSTANCE;
	}
	
	public Connection getConn() {
		return this.conn;
	}
	

	
}
