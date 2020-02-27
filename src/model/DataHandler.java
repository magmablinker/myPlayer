package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DataHandler  {

	public void load() {}
	
	public void save() {}
	
	public boolean isAlreadySaved(String row, String table, String item) {
		boolean isAlreadySaved = false;

		try {
			Connection conn = Database.getInstance().getConn();
			String sql = String.format("SELECT %s FROM %s WHERE %s = ?", row, table, row);
			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, item);

			ResultSet res = pst.executeQuery();
			
			if(res.next())
				isAlreadySaved = true;
			
			pst.close();
		} catch (Exception e) {}
				
		return isAlreadySaved;
	}
	
}
