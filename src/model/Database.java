package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import view.Main;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

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
			// TODO: ON FAIL -> USE CONFIG FILE TO SAVE AND LOAD DATA INSTEAD OF DB???
			Alert alert = new Alert(AlertType.ERROR, "The database connection failed.");
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(Main.class.getResource("css/application.css").toExternalForm());
			Optional<ButtonType> result = alert.showAndWait();
			
			if(result.get().equals(result.get())) {
				System.exit(1);
			}
			
			e.printStackTrace();
		}
		
	}
	
	public static Database getInstance() {
		return INSTANCE;
	}
	
	public Connection getConn() {
		return this.conn;
	}
	

	
}
